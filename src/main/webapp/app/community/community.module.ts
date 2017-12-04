import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BelongModule } from './belong/belong.module';
import { CommunityModule } from './community/community.module';
import { CommunityMediaModule } from './media/media.module';
import { OwnerModule } from './owner/owner.module';

@NgModule({
    imports: [
        BelongModule,
        CommunityModule,
        CommunityMediaModule,
        OwnerModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommunityCommunityModule {}
