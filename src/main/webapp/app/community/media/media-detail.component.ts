import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CommunityMedia } from './media.model';
import { CommunityMediaService } from './media.service';

@Component({
    selector: 'jhi-community-media-detail',
    templateUrl: './media-detail.component.html'
})
export class CommunityMediaDetailComponent implements OnInit, OnDestroy {

    communityMedia: CommunityMedia;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private communityMediaService: CommunityMediaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommunityMedias();
    }

    load(id) {
        this.communityMediaService.find(id).subscribe((communityMedia) => {
            this.communityMedia = communityMedia;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommunityMedias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'communityMediaListModification',
            (response) => this.load(this.communityMedia.id)
        );
    }
}
