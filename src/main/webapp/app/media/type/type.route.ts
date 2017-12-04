import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MediaTypeComponent } from './type.component';
import { MediaTypeDetailComponent } from './type-detail.component';
import { MediaTypePopupComponent } from './type-dialog.component';
import { MediaTypeDeletePopupComponent } from './type-delete-dialog.component';

@Injectable()
export class MediaTypeResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const mediaTypeRoute: Routes = [
    {
        path: 'media-type',
        component: MediaTypeComponent,
        resolve: {
            'pagingParams': MediaTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Media types'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'media-type/:id',
        component: MediaTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Media types'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mediaTypePopupRoute: Routes = [
    {
        path: 'media-type-new',
        component: MediaTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Media types'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'media-type/:id/edit',
        component: MediaTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Media types'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'media-type/:id/delete',
        component: MediaTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Media types'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
