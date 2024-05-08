/* tslint:disable */
/* eslint-disable */
import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfiguration, ApiConfigurationParams } from './api-configuration';

import { VineyardService } from './services/vineyard.service';
import { StaffService } from './services/staff.service';
import { GroupService } from './services/group.service';
import { BlockService } from './services/block.service';
import { DeviceService } from './services/device.service';
import { UserService } from './services/user.service';
import { VineService } from './services/vine.service';
import { DbBackupControllerService } from './services/db-backup-controller.service';
import { AuthenticationService } from './services/authentication.service';
import { DemoControllerService } from './services/demo-controller.service';

/**
 * Module that provides all services and configuration.
 */
@NgModule({
  imports: [],
  exports: [],
  declarations: [],
  providers: [
    VineyardService,
    StaffService,
    GroupService,
    BlockService,
    DeviceService,
    UserService,
    VineService,
    DbBackupControllerService,
    AuthenticationService,
    DemoControllerService,
    ApiConfiguration
  ],
})
export class ApiModule {
  static forRoot(params: ApiConfigurationParams): ModuleWithProviders<ApiModule> {
    return {
      ngModule: ApiModule,
      providers: [
        {
          provide: ApiConfiguration,
          useValue: params
        }
      ]
    }
  }

  constructor( 
    @Optional() @SkipSelf() parentModule: ApiModule,
    @Optional() http: HttpClient
  ) {
    if (parentModule) {
      throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
    }
    if (!http) {
      throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
      'See also https://github.com/angular/angular/issues/20575');
    }
  }
}
