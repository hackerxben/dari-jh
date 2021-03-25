import { Moment } from 'moment';

export interface IFournitureAd {
  id?: number;
  nameFa?: string;
  price?: number;
  description?: string;
  address?: string;
  created?: Moment;
}

export class FournitureAd implements IFournitureAd {
  constructor(
    public id?: number,
    public nameFa?: string,
    public price?: number,
    public description?: string,
    public address?: string,
    public created?: Moment
  ) {}
}
