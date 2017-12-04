import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MediaType } from './type.model';
import { MediaTypePopupService } from './type-popup.service';
import { MediaTypeService } from './type.service';

@Component({
    selector: 'jhi-media-type-dialog',
    templateUrl: './type-dialog.component.html'
})
export class MediaTypeDialogComponent implements OnInit {

    mediaType: MediaType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mediaTypeService: MediaTypeService,
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
        if (this.mediaType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mediaTypeService.update(this.mediaType));
        } else {
            this.subscribeToSaveResponse(
                this.mediaTypeService.create(this.mediaType));
        }
    }

    private subscribeToSaveResponse(result: Observable<MediaType>) {
        result.subscribe((res: MediaType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MediaType) {
        this.eventManager.broadcast({ name: 'mediaTypeListModification', content: 'OK'});
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
    selector: 'jhi-media-type-popup',
    template: ''
})
export class MediaTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mediaTypePopupService: MediaTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mediaTypePopupService
                    .open(MediaTypeDialogComponent as Component, params['id']);
            } else {
                this.mediaTypePopupService
                    .open(MediaTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
