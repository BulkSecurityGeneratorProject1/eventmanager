import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Invitation } from './invitation.model';
import { InvitationPopupService } from './invitation-popup.service';
import { InvitationService } from './invitation.service';

@Component({
    selector: 'jhi-invitation-delete-dialog',
    templateUrl: './invitation-delete-dialog.component.html'
})
export class InvitationDeleteDialogComponent {

    invitation: Invitation;

    constructor(
        private invitationService: InvitationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.invitationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'invitationListModification',
                content: 'Deleted an invitation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invitation-delete-popup',
    template: ''
})
export class InvitationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invitationPopupService: InvitationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.invitationPopupService
                .open(InvitationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
