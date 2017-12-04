import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { WindowRef } from './tracker/window.service';
import {
    SharedLibsModule,
    AlertComponent,
    AlertErrorComponent
} from './';

@NgModule({
    imports: [
        SharedLibsModule
    ],
    declarations: [
        AlertComponent,
        AlertErrorComponent
    ],
    providers: [
        WindowRef,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        },
    ],
    exports: [
        SharedLibsModule,
        AlertComponent,
        AlertErrorComponent
    ]
})
export class SharedCommonModule {}
