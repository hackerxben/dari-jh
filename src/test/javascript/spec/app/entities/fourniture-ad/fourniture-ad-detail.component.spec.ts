import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DariTestModule } from '../../../test.module';
import { FournitureAdDetailComponent } from 'app/entities/fourniture-ad/fourniture-ad-detail.component';
import { FournitureAd } from 'app/shared/model/fourniture-ad.model';

describe('Component Tests', () => {
  describe('FournitureAd Management Detail Component', () => {
    let comp: FournitureAdDetailComponent;
    let fixture: ComponentFixture<FournitureAdDetailComponent>;
    const route = ({ data: of({ fournitureAd: new FournitureAd(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DariTestModule],
        declarations: [FournitureAdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FournitureAdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FournitureAdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fournitureAd on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fournitureAd).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
