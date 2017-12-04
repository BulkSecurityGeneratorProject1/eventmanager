import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';


import {
    BelongService,
    BelongPopupService,
    BelongComponent,
    BelongDetailComponent,
    BelongDialogComponent,
    BelongPopupComponent,
    BelongDeletePopupComponent,
    BelongDeleteDialogComponent,
    belongRoute,
    belongPopupRoute,
} from './';

const ENTITY_STATES = [
    ...belongRoute,
    ...belongPopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BelongComponent,
        BelongDetailComponent,
        BelongDialogComponent,
        BelongDeleteDialogComponent,
        BelongPopupComponent,
        BelongDeletePopupComponent,
    ],
    entryComponents: [
        BelongComponent,
        BelongDialogComponent,
        BelongPopupComponent,
        BelongDeleteDialogComponent,
        BelongDeletePopupComponent,
    ],
    providers: [
        BelongService,
        BelongPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BelongModule {}
