import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Community } from './community.model';
import { CommunityPopupService } from './community-popup.service';
import { CommunityService } from './community.service';

@Component({
    selector: 'jhi-community-delete-dialog',
    templateUrl: './community-delete-dialog.component.html'
})
export class CommunityDeleteDialogComponent {

    community: Community;

    constructor(
        private communityService: CommunityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.communityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'communityListModification',
                content: 'Deleted an community'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-community-delete-popup',
    template: ''
})
export class CommunityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private communityPopupService: CommunityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.communityPopupService
                .open(CommunityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
