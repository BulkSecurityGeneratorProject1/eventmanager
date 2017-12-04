import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EventTypeComponent } from './type.component';
import { EventTypeDetailComponent } from './type-detail.component';
import { EventTypePopupComponent } from './type-dialog.component';
import { EventTypeDeletePopupComponent } from './type-delete-dialog.component';

@Injectable()
export class EventTypeResolvePagingParams implements Resolve<any> {

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

export const eventTypeRoute: Routes = [
    {
        path: 'event-type',
        component: EventTypeComponent,
        resolve: {
            'pagingParams': EventTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event types'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'event-type/:id',
        component: EventTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event types'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eventTypePopupRoute: Routes = [
    {
        path: 'event-type-new',
        component: EventTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event types'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'event-type/:id/edit',
        component: EventTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event types'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'event-type/:id/delete',
        component: EventTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event types'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
