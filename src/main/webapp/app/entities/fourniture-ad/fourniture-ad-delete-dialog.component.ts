import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFournitureAd } from 'app/shared/model/fourniture-ad.model';
import { FournitureAdService } from './fourniture-ad.service';

@Component({
  templateUrl: './fourniture-ad-delete-dialog.component.html',
})
export class FournitureAdDeleteDialogComponent {
  fournitureAd?: IFournitureAd;

  constructor(
    protected fournitureAdService: FournitureAdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fournitureAdService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fournitureAdListModification');
      this.activeModal.close();
    });
  }
}
