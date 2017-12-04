import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    SharedLibsModule,
    SharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    LoginModalComponent,
    Principal,
    TrackerService,
    HasAnyAuthorityDirective,
} from './';

@NgModule({
    imports: [
        SharedLibsModule,
        SharedCommonModule
    ],
    declarations: [
        LoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        TrackerService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    entryComponents: [LoginModalComponent],
    exports: [
        SharedCommonModule,
        LoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class SharedModule {}
