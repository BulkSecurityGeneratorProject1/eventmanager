import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';
import {
    UserParticipateService,
    UserParticipatePopupService,
    UserParticipateComponent,
    UserParticipateDetailComponent,
    UserParticipateDialogComponent,
    UserParticipatePopupComponent,
    UserParticipateDeletePopupComponent,
    UserParticipateDeleteDialogComponent,
    userParticipateRoute,
    userParticipatePopupRoute,
    UserParticipateResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...userParticipateRoute,
    ...userParticipatePopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserParticipateComponent,
        UserParticipateDetailComponent,
        UserParticipateDialogComponent,
        UserParticipateDeleteDialogComponent,
        UserParticipatePopupComponent,
        UserParticipateDeletePopupComponent,
    ],
    entryComponents: [
        UserParticipateComponent,
        UserParticipateDialogComponent,
        UserParticipatePopupComponent,
        UserParticipateDeleteDialogComponent,
        UserParticipateDeletePopupComponent,
    ],
    providers: [
        UserParticipateService,
        UserParticipatePopupService,
        UserParticipateResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserParticipateModule {}
