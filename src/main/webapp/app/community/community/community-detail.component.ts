import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Community } from './community.model';
import { CommunityService } from './community.service';

@Component({
    selector: 'jhi-community-detail',
    templateUrl: './community-detail.component.html'
})
export class CommunityDetailComponent implements OnInit, OnDestroy {

    community: Community;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private communityService: CommunityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommunities();
    }

    load(id) {
        this.communityService.find(id).subscribe((community) => {
            this.community = community;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommunities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'communityListModification',
            (response) => this.load(this.community.id)
        );
    }
}
