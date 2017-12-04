import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MediaType } from './type.model';
import { MediaTypePopupService } from './type-popup.service';
import { MediaTypeService } from './type.service';

@Component({
    selector: 'jhi-media-type-delete-dialog',
    templateUrl: './type-delete-dialog.component.html'
})
export class MediaTypeDeleteDialogComponent {

    mediaType: MediaType;

    constructor(
        private mediaTypeService: MediaTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mediaTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mediaTypeListModification',
                content: 'Deleted an mediaType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-media-type-delete-popup',
    template: ''
})
export class MediaTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mediaTypePopupService: MediaTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mediaTypePopupService
                .open(MediaTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
