import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import { SharedModule } from '../../../shared';
import {GrowlModule} from 'primeng/primeng';
import {StepsModule} from 'primeng/components/steps/steps';
import {ButtonModule} from 'primeng/components/button/button';
import {WizardModule} from 'primeng-extensions-wizard/components/wizard.module';

import {
    StepsDemoComponent,
    stepsDemoRoute
} from './';

const primeng_STATES = [
    stepsDemoRoute
];

@NgModule({
    imports: [
        SharedModule,
        CommonModule,
        BrowserAnimationsModule,
        StepsModule,
        GrowlModule,
        ButtonModule,
        WizardModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        StepsDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventmanagerStepsDemoModule {}
