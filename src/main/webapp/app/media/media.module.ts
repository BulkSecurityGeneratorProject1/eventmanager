import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MediaModule } from './media/media.module';
import { MediaTypeModule } from './type/type.module';

@NgModule({
    imports: [
        MediaModule,
        MediaTypeModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MediaMediaModule {}
