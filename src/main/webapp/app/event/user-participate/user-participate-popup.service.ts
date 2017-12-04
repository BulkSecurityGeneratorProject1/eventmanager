import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { UserParticipate } from './user-participate.model';
import { UserParticipateService } from './user-participate.service';

@Injectable()
export class UserParticipatePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private userParticipateService: UserParticipateService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.userParticipateService.find(id).subscribe((userParticipate) => {
                    userParticipate.created = this.datePipe
                        .transform(userParticipate.created, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.userParticipateModalRef(component, userParticipate);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.userParticipateModalRef(component, new UserParticipate());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    userParticipateModalRef(component: Component, userParticipate: UserParticipate): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userParticipate = userParticipate;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
