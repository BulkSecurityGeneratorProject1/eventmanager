import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EventType } from './type.model';
import { EventTypePopupService } from './type-popup.service';
import { EventTypeService } from './type.service';

@Component({
    selector: 'jhi-event-type-dialog',
    templateUrl: './type-dialog.component.html'
})
export class EventTypeDialogComponent implements OnInit {

    eventType: EventType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private eventTypeService: EventTypeService,
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
        if (this.eventType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.eventTypeService.update(this.eventType));
        } else {
            this.subscribeToSaveResponse(
                this.eventTypeService.create(this.eventType));
        }
    }

    private subscribeToSaveResponse(result: Observable<EventType>) {
        result.subscribe((res: EventType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EventType) {
        this.eventManager.broadcast({ name: 'eventTypeListModification', content: 'OK'});
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
    selector: 'jhi-event-type-popup',
    template: ''
})
export class EventTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private eventTypePopupService: EventTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.eventTypePopupService
                    .open(EventTypeDialogComponent as Component, params['id']);
            } else {
                this.eventTypePopupService
                    .open(EventTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
