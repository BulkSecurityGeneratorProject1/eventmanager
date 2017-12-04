import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Belong } from './belong.model';
import { BelongPopupService } from './belong-popup.service';
import { BelongService } from './belong.service';
import { Community, CommunityService } from '../community';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-belong-dialog',
    templateUrl: './belong-dialog.component.html'
})
export class BelongDialogComponent implements OnInit {

    belong: Belong;
    isSaving: boolean;

    communities: Community[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private belongService: BelongService,
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
        if (this.belong.id !== undefined) {
            this.subscribeToSaveResponse(
                this.belongService.update(this.belong));
        } else {
            this.subscribeToSaveResponse(
                this.belongService.create(this.belong));
        }
    }

    private subscribeToSaveResponse(result: Observable<Belong>) {
        result.subscribe((res: Belong) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Belong) {
        this.eventManager.broadcast({ name: 'belongListModification', content: 'OK'});
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
    selector: 'jhi-belong-popup',
    template: ''
})
export class BelongPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private belongPopupService: BelongPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.belongPopupService
                    .open(BelongDialogComponent as Component, params['id']);
            } else {
                this.belongPopupService
                    .open(BelongDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
