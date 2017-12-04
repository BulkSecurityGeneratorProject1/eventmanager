import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommunityMedia } from './media.model';
import { CommunityMediaPopupService } from './media-popup.service';
import { CommunityMediaService } from './media.service';

@Component({
    selector: 'jhi-community-media-delete-dialog',
    templateUrl: './media-delete-dialog.component.html'
})
export class CommunityMediaDeleteDialogComponent {

    communityMedia: CommunityMedia;

    constructor(
        private communityMediaService: CommunityMediaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.communityMediaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'communityMediaListModification',
                content: 'Deleted an communityMedia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-community-media-delete-popup',
    template: ''
})
export class CommunityMediaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private communityMediaPopupService: CommunityMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.communityMediaPopupService
                .open(CommunityMediaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
