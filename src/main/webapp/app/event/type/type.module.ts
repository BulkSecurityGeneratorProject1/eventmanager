import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';
import {
    EventTypeService,
    EventTypePopupService,
    EventTypeComponent,
    EventTypeDetailComponent,
    EventTypeDialogComponent,
    EventTypePopupComponent,
    EventTypeDeletePopupComponent,
    EventTypeDeleteDialogComponent,
    eventTypeRoute,
    eventTypePopupRoute,
    EventTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...eventTypeRoute,
    ...eventTypePopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EventTypeComponent,
        EventTypeDetailComponent,
        EventTypeDialogComponent,
        EventTypeDeleteDialogComponent,
        EventTypePopupComponent,
        EventTypeDeletePopupComponent,
    ],
    entryComponents: [
        EventTypeComponent,
        EventTypeDialogComponent,
        EventTypePopupComponent,
        EventTypeDeleteDialogComponent,
        EventTypeDeletePopupComponent,
    ],
    providers: [
        EventTypeService,
        EventTypePopupService,
        EventTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventTypeModule {}
