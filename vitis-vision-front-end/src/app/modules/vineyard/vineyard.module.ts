import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VineyardRoutingModule } from './vineyard-routing.module';
import { VineyardComponent } from './pages/vineyard/vineyard.component';
import {FormsModule} from "@angular/forms";
import { GroupComponent } from './pages/group/group.component';
import { BlockComponent } from './pages/block/block.component';
import { VineComponent } from './pages/vine/vine.component';
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    VineyardComponent,
    GroupComponent,
    BlockComponent,
    VineComponent
  ],
    imports: [
        CommonModule,
        VineyardRoutingModule,
        FormsModule,
        TranslateModule
    ]
})
export class VineyardModule { }
