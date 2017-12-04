import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EventMedia } from './media.model';
import { EventMediaPopupService } from './media-popup.service';
import { EventMediaService } from './media.service';

@Component({
    selector: 'jhi-event-media-delete-dialog',
    templateUrl: './media-delete-dialog.component.html'
})
export class EventMediaDeleteDialogComponent {

    eventMedia: EventMedia;

    constructor(
        private eventMediaService: EventMediaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eventMediaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'eventMediaListModification',
                content: 'Deleted an eventMedia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-event-media-delete-popup',
    template: ''
})
export class EventMediaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private eventMediaPopupService: EventMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.eventMediaPopupService
                .open(EventMediaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
