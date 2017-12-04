import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CommunityMedia } from './media.model';
import { CommunityMediaService } from './media.service';

@Injectable()
export class CommunityMediaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private communityMediaService: CommunityMediaService

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
                this.communityMediaService.find(id).subscribe((communityMedia) => {
                    communityMedia.created = this.datePipe
                        .transform(communityMedia.created, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.communityMediaModalRef(component, communityMedia);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.communityMediaModalRef(component, new CommunityMedia());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    communityMediaModalRef(component: Component, communityMedia: CommunityMedia): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.communityMedia = communityMedia;
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
