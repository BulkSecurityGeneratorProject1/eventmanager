import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';
import {
    EventMediaService,
    EventMediaPopupService,
    EventMediaComponent,
    EventMediaDetailComponent,
    EventMediaDialogComponent,
    EventMediaPopupComponent,
    EventMediaDeletePopupComponent,
    EventMediaDeleteDialogComponent,
    eventMediaRoute,
    eventMediaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...eventMediaRoute,
    ...eventMediaPopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EventMediaComponent,
        EventMediaDetailComponent,
        EventMediaDialogComponent,
        EventMediaDeleteDialogComponent,
        EventMediaPopupComponent,
        EventMediaDeletePopupComponent,
    ],
    entryComponents: [
        EventMediaComponent,
        EventMediaDialogComponent,
        EventMediaPopupComponent,
        EventMediaDeleteDialogComponent,
        EventMediaDeletePopupComponent,
    ],
    providers: [
        EventMediaService,
        EventMediaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventMediaModule {}
