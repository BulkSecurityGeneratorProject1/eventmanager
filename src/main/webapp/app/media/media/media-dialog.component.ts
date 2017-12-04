import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Media } from './media.model';
import { MediaPopupService } from './media-popup.service';
import { MediaService } from './media.service';
import { MediaType, MediaTypeService } from '../type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-media-dialog',
    templateUrl: './media-dialog.component.html'
})
export class MediaDialogComponent implements OnInit {

    media: Media;
    isSaving: boolean;

    mediatypes: MediaType[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mediaService: MediaService,
        private mediaTypeService: MediaTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mediaTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.mediatypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.media.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mediaService.update(this.media));
        } else {
            this.subscribeToSaveResponse(
                this.mediaService.create(this.media));
        }
    }

    private subscribeToSaveResponse(result: Observable<Media>) {
        result.subscribe((res: Media) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Media) {
        this.eventManager.broadcast({ name: 'mediaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMediaTypeById(index: number, item: MediaType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-media-popup',
    template: ''
})
export class MediaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mediaPopupService: MediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mediaPopupService
                    .open(MediaDialogComponent as Component, params['id']);
            } else {
                this.mediaPopupService
                    .open(MediaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
