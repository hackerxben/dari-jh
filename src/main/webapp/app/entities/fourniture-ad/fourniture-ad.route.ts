import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFournitureAd, FournitureAd } from 'app/shared/model/fourniture-ad.model';
import { FournitureAdService } from './fourniture-ad.service';
import { FournitureAdComponent } from './fourniture-ad.component';
import { FournitureAdDetailComponent } from './fourniture-ad-detail.component';
import { FournitureAdUpdateComponent } from './fourniture-ad-update.component';

@Injectable({ providedIn: 'root' })
export class FournitureAdResolve implements Resolve<IFournitureAd> {
  constructor(private service: FournitureAdService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFournitureAd> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fournitureAd: HttpResponse<FournitureAd>) => {
          if (fournitureAd.body) {
            return of(fournitureAd.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FournitureAd());
  }
}

export const fournitureAdRoute: Routes = [
  {
    path: '',
    component: FournitureAdComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'FournitureAds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FournitureAdDetailComponent,
    resolve: {
      fournitureAd: FournitureAdResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FournitureAds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FournitureAdUpdateComponent,
    resolve: {
      fournitureAd: FournitureAdResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FournitureAds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FournitureAdUpdateComponent,
    resolve: {
      fournitureAd: FournitureAdResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FournitureAds',
    },
    canActivate: [UserRouteAccessService],
  },
];
