import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFournitureAd } from 'app/shared/model/fourniture-ad.model';

@Component({
  selector: 'jhi-fourniture-ad-detail',
  templateUrl: './fourniture-ad-detail.component.html',
})
export class FournitureAdDetailComponent implements OnInit {
  fournitureAd: IFournitureAd | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fournitureAd }) => (this.fournitureAd = fournitureAd));
  }

  previousState(): void {
    window.history.back();
  }
}
