import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EventMedia } from './media.model';
import { EventMediaService } from './media.service';

@Component({
    selector: 'jhi-event-media-detail',
    templateUrl: './media-detail.component.html'
})
export class EventMediaDetailComponent implements OnInit, OnDestroy {

    eventMedia: EventMedia;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private eventMediaService: EventMediaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEventMedias();
    }

    load(id) {
        this.eventMediaService.find(id).subscribe((eventMedia) => {
            this.eventMedia = eventMedia;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEventMedias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'eventMediaListModification',
            (response) => this.load(this.eventMedia.id)
        );
    }
}
