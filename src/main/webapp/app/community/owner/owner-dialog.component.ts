import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Owner } from './owner.model';
import { OwnerPopupService } from './owner-popup.service';
import { OwnerService } from './owner.service';
import { Community, CommunityService } from '../community';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-owner-dialog',
    templateUrl: './owner-dialog.component.html'
})
export class OwnerDialogComponent implements OnInit {

    owner: Owner;
    isSaving: boolean;

    communities: Community[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ownerService: OwnerService,
        private communityService: CommunityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.communityService.query()
            .subscribe((res: ResponseWrapper) => { this.communities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.owner.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ownerService.update(this.owner));
        } else {
            this.subscribeToSaveResponse(
                this.ownerService.create(this.owner));
        }
    }

    private subscribeToSaveResponse(result: Observable<Owner>) {
        result.subscribe((res: Owner) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Owner) {
        this.eventManager.broadcast({ name: 'ownerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCommunityById(index: number, item: Community) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-owner-popup',
    template: ''
})
export class OwnerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ownerPopupService: OwnerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ownerPopupService
                    .open(OwnerDialogComponent as Component, params['id']);
            } else {
                this.ownerPopupService
                    .open(OwnerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
