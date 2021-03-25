import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DariTestModule } from '../../../test.module';
import { FournitureAdUpdateComponent } from 'app/entities/fourniture-ad/fourniture-ad-update.component';
import { FournitureAdService } from 'app/entities/fourniture-ad/fourniture-ad.service';
import { FournitureAd } from 'app/shared/model/fourniture-ad.model';

describe('Component Tests', () => {
  describe('FournitureAd Management Update Component', () => {
    let comp: FournitureAdUpdateComponent;
    let fixture: ComponentFixture<FournitureAdUpdateComponent>;
    let service: FournitureAdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DariTestModule],
        declarations: [FournitureAdUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FournitureAdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FournitureAdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FournitureAdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FournitureAd(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FournitureAd();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
