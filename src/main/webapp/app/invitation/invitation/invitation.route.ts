import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InvitationComponent } from './invitation.component';
import { InvitationDetailComponent } from './invitation-detail.component';
import { InvitationPopupComponent } from './invitation-dialog.component';
import { InvitationDeletePopupComponent } from './invitation-delete-dialog.component';

@Injectable()
export class InvitationResolvePagingParams implements Resolve<any> {

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

export const invitationRoute: Routes = [
    {
        path: 'invitation',
        component: InvitationComponent,
        resolve: {
            'pagingParams': InvitationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Invitations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'invitation/:id',
        component: InvitationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Invitations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invitationPopupRoute: Routes = [
    {
        path: 'invitation-new',
        component: InvitationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Invitations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invitation/:id/edit',
        component: InvitationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Invitations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invitation/:id/delete',
        component: InvitationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Invitations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
