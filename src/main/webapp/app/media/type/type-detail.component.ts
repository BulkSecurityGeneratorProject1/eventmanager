import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MediaType } from './type.model';
import { MediaTypeService } from './type.service';

@Component({
    selector: 'jhi-media-type-detail',
    templateUrl: './type-detail.component.html'
})
export class MediaTypeDetailComponent implements OnInit, OnDestroy {

    mediaType: MediaType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mediaTypeService: MediaTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMediaTypes();
    }

    load(id) {
        this.mediaTypeService.find(id).subscribe((mediaType) => {
            this.mediaType = mediaType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMediaTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mediaTypeListModification',
            (response) => this.load(this.mediaType.id)
        );
    }
}
