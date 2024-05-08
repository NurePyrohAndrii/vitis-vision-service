import { NgModule } from '@angular/core';

import { CommonRoutingModule } from './common-routing.module';
import { HomeComponent } from './pages/home/home.component';


@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonRoutingModule
  ]
})
export class CommonModule { }
