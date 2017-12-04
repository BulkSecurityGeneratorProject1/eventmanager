import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';
import {
    OwnerService,
    OwnerPopupService,
    OwnerComponent,
    OwnerDetailComponent,
    OwnerDialogComponent,
    OwnerPopupComponent,
    OwnerDeletePopupComponent,
    OwnerDeleteDialogComponent,
    ownerRoute,
    ownerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ownerRoute,
    ...ownerPopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OwnerComponent,
        OwnerDetailComponent,
        OwnerDialogComponent,
        OwnerDeleteDialogComponent,
        OwnerPopupComponent,
        OwnerDeletePopupComponent,
    ],
    entryComponents: [
        OwnerComponent,
        OwnerDialogComponent,
        OwnerPopupComponent,
        OwnerDeleteDialogComponent,
        OwnerDeletePopupComponent,
    ],
    providers: [
        OwnerService,
        OwnerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OwnerModule {}
