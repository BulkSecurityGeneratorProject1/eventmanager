import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Community } from './community.model';
import { CommunityPopupService } from './community-popup.service';
import { CommunityService } from './community.service';

@Component({
    selector: 'jhi-community-dialog',
    templateUrl: './community-dialog.component.html'
})
export class CommunityDialogComponent implements OnInit {

    community: Community;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private communityService: CommunityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.community.id !== undefined) {
            this.subscribeToSaveResponse(
                this.communityService.update(this.community));
        } else {
            this.subscribeToSaveResponse(
                this.communityService.create(this.community));
        }
    }

    private subscribeToSaveResponse(result: Observable<Community>) {
        result.subscribe((res: Community) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Community) {
        this.eventManager.broadcast({ name: 'communityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-community-popup',
    template: ''
})
export class CommunityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private communityPopupService: CommunityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.communityPopupService
                    .open(CommunityDialogComponent as Component, params['id']);
            } else {
                this.communityPopupService
                    .open(CommunityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
