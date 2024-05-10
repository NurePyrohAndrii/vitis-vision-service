import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {VineyardComponent} from "./pages/vineyard/vineyard.component";
import {BlockComponent} from "./pages/block/block.component";
import {GroupComponent} from "./pages/group/group.component";
import {VineComponent} from "./pages/vine/vine.component";

const routes: Routes = [
  {
    path: '',
    component: VineyardComponent
  },
  {
    path: ':vineyardId/block/:blockId',
    component: BlockComponent
  },
  {
    path: ':vineyardId/group/:groupId',
    component: GroupComponent
  },
  {
    path: ':vineyardId/block/:blockId/vine/:vineId',
    component: VineComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VineyardRoutingModule { }
