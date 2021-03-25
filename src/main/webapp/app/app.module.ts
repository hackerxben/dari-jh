import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DariSharedModule } from 'app/shared/shared.module';
import { DariCoreModule } from 'app/core/core.module';
import { DariAppRoutingModule } from './app-routing.module';
import { DariHomeModule } from './home/home.module';
import { DariEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    DariSharedModule,
    DariCoreModule,
    DariHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DariEntityModule,
    DariAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class DariAppModule {}
