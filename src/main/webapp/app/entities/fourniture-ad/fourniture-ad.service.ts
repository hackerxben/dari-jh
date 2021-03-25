import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFournitureAd } from 'app/shared/model/fourniture-ad.model';

type EntityResponseType = HttpResponse<IFournitureAd>;
type EntityArrayResponseType = HttpResponse<IFournitureAd[]>;

@Injectable({ providedIn: 'root' })
export class FournitureAdService {
  public resourceUrl = SERVER_API_URL + 'api/fourniture-ads';

  constructor(protected http: HttpClient) {}

  create(fournitureAd: IFournitureAd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fournitureAd);
    return this.http
      .post<IFournitureAd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fournitureAd: IFournitureAd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fournitureAd);
    return this.http
      .put<IFournitureAd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFournitureAd>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFournitureAd[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fournitureAd: IFournitureAd): IFournitureAd {
    const copy: IFournitureAd = Object.assign({}, fournitureAd, {
      created: fournitureAd.created && fournitureAd.created.isValid() ? fournitureAd.created.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fournitureAd: IFournitureAd) => {
        fournitureAd.created = fournitureAd.created ? moment(fournitureAd.created) : undefined;
      });
    }
    return res;
  }
}
