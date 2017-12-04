import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CommunityMedia } from './media.model';
import { CommunityMediaPopupService } from './media-popup.service';
import { CommunityMediaService } from './media.service';
import { Media, MediaService } from '../../media/media/';
import { Community, CommunityService } from '../community';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-community-media-dialog',
    templateUrl: './media-dialog.component.html'
})
export class CommunityMediaDialogComponent implements OnInit {

    communityMedia: CommunityMedia;
    isSaving: boolean;

    media: Media[];

    communities: Community[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private communityMediaService: CommunityMediaService,
        private mediaService: MediaService,
        private communityService: CommunityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mediaService.query()
            .subscribe((res: ResponseWrapper) => { this.media = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.communityService.query()
            .subscribe((res: ResponseWrapper) => { this.communities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.communityMedia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.communityMediaService.update(this.communityMedia));
        } else {
            this.subscribeToSaveResponse(
                this.communityMediaService.create(this.communityMedia));
        }
    }

    private subscribeToSaveResponse(result: Observable<CommunityMedia>) {
        result.subscribe((res: CommunityMedia) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CommunityMedia) {
        this.eventManager.broadcast({ name: 'communityMediaListModification', content: 'OK'});
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

    trackCommunityById(index: number, item: Community) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-community-media-popup',
    template: ''
})
export class CommunityMediaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private communityMediaPopupService: CommunityMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.communityMediaPopupService
                    .open(CommunityMediaDialogComponent as Component, params['id']);
            } else {
                this.communityMediaPopupService
                    .open(CommunityMediaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
