import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Belong } from './belong.model';
import { BelongPopupService } from './belong-popup.service';
import { BelongService } from './belong.service';

@Component({
    selector: 'jhi-belong-delete-dialog',
    templateUrl: './belong-delete-dialog.component.html'
})
export class BelongDeleteDialogComponent {

    belong: Belong;

    constructor(
        private belongService: BelongService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.belongService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'belongListModification',
                content: 'Deleted an belong'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-belong-delete-popup',
    template: ''
})
export class BelongDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private belongPopupService: BelongPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.belongPopupService
                .open(BelongDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
