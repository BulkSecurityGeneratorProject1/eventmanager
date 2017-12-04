import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserParticipate } from './user-participate.model';
import { UserParticipatePopupService } from './user-participate-popup.service';
import { UserParticipateService } from './user-participate.service';

@Component({
    selector: 'jhi-user-participate-delete-dialog',
    templateUrl: './user-participate-delete-dialog.component.html'
})
export class UserParticipateDeleteDialogComponent {

    userParticipate: UserParticipate;

    constructor(
        private userParticipateService: UserParticipateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userParticipateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userParticipateListModification',
                content: 'Deleted an userParticipate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-participate-delete-popup',
    template: ''
})
export class UserParticipateDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userParticipatePopupService: UserParticipatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userParticipatePopupService
                .open(UserParticipateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
