import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EventType } from './type.model';
import { EventTypeService } from './type.service';

@Component({
    selector: 'jhi-event-type-detail',
    templateUrl: './type-detail.component.html'
})
export class EventTypeDetailComponent implements OnInit, OnDestroy {

    eventType: EventType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private eventTypeService: EventTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEventTypes();
    }

    load(id) {
        this.eventTypeService.find(id).subscribe((eventType) => {
            this.eventType = eventType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEventTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'eventTypeListModification',
            (response) => this.load(this.eventType.id)
        );
    }
}
