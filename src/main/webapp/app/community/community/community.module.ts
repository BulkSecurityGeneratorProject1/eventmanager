import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';
import {
    CommunityService,
    CommunityPopupService,
    CommunityComponent,
    CommunityDetailComponent,
    CommunityDialogComponent,
    CommunityPopupComponent,
    CommunityDeletePopupComponent,
    CommunityDeleteDialogComponent,
    communityRoute,
    communityPopupRoute,
    CommunityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...communityRoute,
    ...communityPopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CommunityComponent,
        CommunityDetailComponent,
        CommunityDialogComponent,
        CommunityDeleteDialogComponent,
        CommunityPopupComponent,
        CommunityDeletePopupComponent,
    ],
    entryComponents: [
        CommunityComponent,
        CommunityDialogComponent,
        CommunityPopupComponent,
        CommunityDeleteDialogComponent,
        CommunityDeletePopupComponent,
    ],
    providers: [
        CommunityService,
        CommunityPopupService,
        CommunityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommunityModule {}
