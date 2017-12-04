import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CommentModule } from './comment/comment.module';
import { EventModule } from './event/event.module';
import { EventMediaModule } from './media/media.module';
import { EventTypeModule } from './type/type.module';
import { UserParticipateModule } from './user-participate/user-participate.module';



@NgModule({
    imports: [
        CommentModule,
        EventModule,
        EventMediaModule,
        EventTypeModule,
        UserParticipateModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventEventModule {}
