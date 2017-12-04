import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserParticipate } from './user-participate.model';
import { UserParticipatePopupService } from './user-participate-popup.service';
import { UserParticipateService } from './user-participate.service';
import { Event, EventService } from '../event';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-participate-dialog',
    templateUrl: './user-participate-dialog.component.html'
})
export class UserParticipateDialogComponent implements OnInit {

    userParticipate: UserParticipate;
    isSaving: boolean;

    events: Event[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userParticipateService: UserParticipateService,
        private eventService: EventService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.eventService.query()
            .subscribe((res: ResponseWrapper) => { this.events = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userParticipate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userParticipateService.update(this.userParticipate));
        } else {
            this.subscribeToSaveResponse(
                this.userParticipateService.create(this.userParticipate));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserParticipate>) {
        result.subscribe((res: UserParticipate) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserParticipate) {
        this.eventManager.broadcast({ name: 'userParticipateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEventById(index: number, item: Event) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-participate-popup',
    template: ''
})
export class UserParticipatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userParticipatePopupService: UserParticipatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userParticipatePopupService
                    .open(UserParticipateDialogComponent as Component, params['id']);
            } else {
                this.userParticipatePopupService
                    .open(UserParticipateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
