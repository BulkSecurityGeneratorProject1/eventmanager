import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { UserParticipate } from './user-participate.model';
import { UserParticipateService } from './user-participate.service';

@Component({
    selector: 'jhi-user-participate-detail',
    templateUrl: './user-participate-detail.component.html'
})
export class UserParticipateDetailComponent implements OnInit, OnDestroy {

    userParticipate: UserParticipate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userParticipateService: UserParticipateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserParticipates();
    }

    load(id) {
        this.userParticipateService.find(id).subscribe((userParticipate) => {
            this.userParticipate = userParticipate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserParticipates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userParticipateListModification',
            (response) => this.load(this.userParticipate.id)
        );
    }
}
