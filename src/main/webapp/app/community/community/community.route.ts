import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CommunityComponent } from './community.component';
import { CommunityDetailComponent } from './community-detail.component';
import { CommunityPopupComponent } from './community-dialog.component';
import { CommunityDeletePopupComponent } from './community-delete-dialog.component';

@Injectable()
export class CommunityResolvePagingParams implements Resolve<any> {

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

export const communityRoute: Routes = [
    {
        path: 'community',
        component: CommunityComponent,
        resolve: {
            'pagingParams': CommunityResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Communities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'community/:id',
        component: CommunityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Communities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const communityPopupRoute: Routes = [
    {
        path: 'community-new',
        component: CommunityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Communities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'community/:id/edit',
        component: CommunityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Communities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'community/:id/delete',
        component: CommunityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Communities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
