import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { InvitationModule } from './invitation/invitation.module';

@NgModule({
    imports: [
        InvitationModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InvitationInvitationModule {}
