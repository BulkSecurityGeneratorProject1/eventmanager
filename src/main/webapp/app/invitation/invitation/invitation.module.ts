import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';
import {
    InvitationService,
    InvitationPopupService,
    InvitationComponent,
    InvitationDetailComponent,
    InvitationDialogComponent,
    InvitationPopupComponent,
    InvitationDeletePopupComponent,
    InvitationDeleteDialogComponent,
    invitationRoute,
    invitationPopupRoute,
    InvitationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...invitationRoute,
    ...invitationPopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InvitationComponent,
        InvitationDetailComponent,
        InvitationDialogComponent,
        InvitationDeleteDialogComponent,
        InvitationPopupComponent,
        InvitationDeletePopupComponent,
    ],
    entryComponents: [
        InvitationComponent,
        InvitationDialogComponent,
        InvitationPopupComponent,
        InvitationDeleteDialogComponent,
        InvitationDeletePopupComponent,
    ],
    providers: [
        InvitationService,
        InvitationPopupService,
        InvitationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InvitationModule {}
