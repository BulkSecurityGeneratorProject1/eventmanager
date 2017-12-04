import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';
import {
    MediaTypeService,
    MediaTypePopupService,
    MediaTypeComponent,
    MediaTypeDetailComponent,
    MediaTypeDialogComponent,
    MediaTypePopupComponent,
    MediaTypeDeletePopupComponent,
    MediaTypeDeleteDialogComponent,
    mediaTypeRoute,
    mediaTypePopupRoute,
    MediaTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mediaTypeRoute,
    ...mediaTypePopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MediaTypeComponent,
        MediaTypeDetailComponent,
        MediaTypeDialogComponent,
        MediaTypeDeleteDialogComponent,
        MediaTypePopupComponent,
        MediaTypeDeletePopupComponent,
    ],
    entryComponents: [
        MediaTypeComponent,
        MediaTypeDialogComponent,
        MediaTypePopupComponent,
        MediaTypeDeleteDialogComponent,
        MediaTypeDeletePopupComponent,
    ],
    providers: [
        MediaTypeService,
        MediaTypePopupService,
        MediaTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MediaTypeModule {}
