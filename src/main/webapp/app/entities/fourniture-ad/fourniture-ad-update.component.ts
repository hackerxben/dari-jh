import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFournitureAd, FournitureAd } from 'app/shared/model/fourniture-ad.model';
import { FournitureAdService } from './fourniture-ad.service';

@Component({
  selector: 'jhi-fourniture-ad-update',
  templateUrl: './fourniture-ad-update.component.html',
})
export class FournitureAdUpdateComponent implements OnInit {
  isSaving = false;
  createdDp: any;

  editForm = this.fb.group({
    id: [],
    nameFa: [],
    price: [],
    description: [],
    address: [],
    created: [],
  });

  constructor(protected fournitureAdService: FournitureAdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fournitureAd }) => {
      this.updateForm(fournitureAd);
    });
  }

  updateForm(fournitureAd: IFournitureAd): void {
    this.editForm.patchValue({
      id: fournitureAd.id,
      nameFa: fournitureAd.nameFa,
      price: fournitureAd.price,
      description: fournitureAd.description,
      address: fournitureAd.address,
      created: fournitureAd.created,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fournitureAd = this.createFromForm();
    if (fournitureAd.id !== undefined) {
      this.subscribeToSaveResponse(this.fournitureAdService.update(fournitureAd));
    } else {
      this.subscribeToSaveResponse(this.fournitureAdService.create(fournitureAd));
    }
  }

  private createFromForm(): IFournitureAd {
    return {
      ...new FournitureAd(),
      id: this.editForm.get(['id'])!.value,
      nameFa: this.editForm.get(['nameFa'])!.value,
      price: this.editForm.get(['price'])!.value,
      description: this.editForm.get(['description'])!.value,
      address: this.editForm.get(['address'])!.value,
      created: this.editForm.get(['created'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFournitureAd>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
