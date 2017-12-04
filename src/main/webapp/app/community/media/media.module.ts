import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';
import {
    CommunityMediaService,
    CommunityMediaPopupService,
    CommunityMediaComponent,
    CommunityMediaDetailComponent,
    CommunityMediaDialogComponent,
    CommunityMediaPopupComponent,
    CommunityMediaDeletePopupComponent,
    CommunityMediaDeleteDialogComponent,
    communityMediaRoute,
    communityMediaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...communityMediaRoute,
    ...communityMediaPopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CommunityMediaComponent,
        CommunityMediaDetailComponent,
        CommunityMediaDialogComponent,
        CommunityMediaDeleteDialogComponent,
        CommunityMediaPopupComponent,
        CommunityMediaDeletePopupComponent,
    ],
    entryComponents: [
        CommunityMediaComponent,
        CommunityMediaDialogComponent,
        CommunityMediaPopupComponent,
        CommunityMediaDeleteDialogComponent,
        CommunityMediaDeletePopupComponent,
    ],
    providers: [
        CommunityMediaService,
        CommunityMediaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommunityMediaModule {}
