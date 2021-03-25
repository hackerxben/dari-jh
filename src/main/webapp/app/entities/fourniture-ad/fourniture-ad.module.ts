import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DariSharedModule } from 'app/shared/shared.module';
import { FournitureAdComponent } from './fourniture-ad.component';
import { FournitureAdDetailComponent } from './fourniture-ad-detail.component';
import { FournitureAdUpdateComponent } from './fourniture-ad-update.component';
import { FournitureAdDeleteDialogComponent } from './fourniture-ad-delete-dialog.component';
import { fournitureAdRoute } from './fourniture-ad.route';

@NgModule({
  imports: [DariSharedModule, RouterModule.forChild(fournitureAdRoute)],
  declarations: [FournitureAdComponent, FournitureAdDetailComponent, FournitureAdUpdateComponent, FournitureAdDeleteDialogComponent],
  entryComponents: [FournitureAdDeleteDialogComponent],
})
export class DariFournitureAdModule {}
