import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EventMedia } from './media.model';
import { EventMediaPopupService } from './media-popup.service';
import { EventMediaService } from './media.service';
import { Media, MediaService } from '../../media/media';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-event-media-dialog',
    templateUrl: './media-dialog.component.html'
})
export class EventMediaDialogComponent implements OnInit {

    eventMedia: EventMedia;
    isSaving: boolean;

    media: Media[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private eventMediaService: EventMediaService,
        private mediaService: MediaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mediaService.query()
            .subscribe((res: ResponseWrapper) => { this.media = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.eventMedia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.eventMediaService.update(this.eventMedia));
        } else {
            this.subscribeToSaveResponse(
                this.eventMediaService.create(this.eventMedia));
        }
    }

    private subscribeToSaveResponse(result: Observable<EventMedia>) {
        result.subscribe((res: EventMedia) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EventMedia) {
        this.eventManager.broadcast({ name: 'eventMediaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMediaById(index: number, item: Media) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-event-media-popup',
    template: ''
})
export class EventMediaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private eventMediaPopupService: EventMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.eventMediaPopupService
                    .open(EventMediaDialogComponent as Component, params['id']);
            } else {
                this.eventMediaPopupService
                    .open(EventMediaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
