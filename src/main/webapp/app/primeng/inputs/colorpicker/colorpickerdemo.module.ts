import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../../shared';
import {FormsModule} from '@angular/forms';
import {ColorPickerModule} from 'primeng/components/colorpicker/colorpicker';
import {GrowlModule} from 'primeng/components/growl/growl';
import {WizardModule} from 'primeng-extensions-wizard/components/wizard.module';

import {
    ColorpickerDemoComponent,
    colorpickerDemoRoute
} from './';

const primeng_STATES = [
    colorpickerDemoRoute
];

@NgModule({
    imports: [
        SharedModule,
        FormsModule,
        ColorPickerModule,
        GrowlModule,
        WizardModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        ColorpickerDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventmanagerColorPickerDemoModule {}
