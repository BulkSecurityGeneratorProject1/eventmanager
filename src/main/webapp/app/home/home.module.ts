import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../shared';


import { EventService } from './event.service';


import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent
    ],
    entryComponents: [
    ],
    providers: [
        EventService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HomeModule {}
