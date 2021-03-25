import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DariTestModule } from '../../../test.module';
import { FournitureAdComponent } from 'app/entities/fourniture-ad/fourniture-ad.component';
import { FournitureAdService } from 'app/entities/fourniture-ad/fourniture-ad.service';
import { FournitureAd } from 'app/shared/model/fourniture-ad.model';

describe('Component Tests', () => {
  describe('FournitureAd Management Component', () => {
    let comp: FournitureAdComponent;
    let fixture: ComponentFixture<FournitureAdComponent>;
    let service: FournitureAdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DariTestModule],
        declarations: [FournitureAdComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(FournitureAdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FournitureAdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FournitureAdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FournitureAd(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fournitureAds && comp.fournitureAds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FournitureAd(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fournitureAds && comp.fournitureAds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
