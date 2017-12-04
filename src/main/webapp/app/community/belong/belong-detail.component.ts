import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Belong } from './belong.model';
import { BelongService } from './belong.service';

@Component({
    selector: 'jhi-belong-detail',
    templateUrl: './belong-detail.component.html'
})
export class BelongDetailComponent implements OnInit, OnDestroy {

    belong: Belong;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private belongService: BelongService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBelongs();
    }

    load(id) {
        this.belongService.find(id).subscribe((belong) => {
            this.belong = belong;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBelongs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'belongListModification',
            (response) => this.load(this.belong.id)
        );
    }
}
