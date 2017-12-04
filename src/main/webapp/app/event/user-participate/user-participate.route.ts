import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserParticipateComponent } from './user-participate.component';
import { UserParticipateDetailComponent } from './user-participate-detail.component';
import { UserParticipatePopupComponent } from './user-participate-dialog.component';
import { UserParticipateDeletePopupComponent } from './user-participate-delete-dialog.component';

@Injectable()
export class UserParticipateResolvePagingParams implements Resolve<any> {

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

export const userParticipateRoute: Routes = [
    {
        path: 'user-participate',
        component: UserParticipateComponent,
        resolve: {
            'pagingParams': UserParticipateResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'User participates'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-participate/:id',
        component: UserParticipateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'User participates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userParticipatePopupRoute: Routes = [
    {
        path: 'user-participate-new',
        component: UserParticipatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'User participates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-participate/:id/edit',
        component: UserParticipatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'User participates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-participate/:id/delete',
        component: UserParticipateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'User participates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
