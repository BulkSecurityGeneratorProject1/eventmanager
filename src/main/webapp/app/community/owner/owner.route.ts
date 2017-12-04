import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OwnerComponent } from './owner.component';
import { OwnerDetailComponent } from './owner-detail.component';
import { OwnerPopupComponent } from './owner-dialog.component';
import { OwnerDeletePopupComponent } from './owner-delete-dialog.component';

export const ownerRoute: Routes = [
    {
        path: 'owner',
        component: OwnerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Owners'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'owner/:id',
        component: OwnerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Owners'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ownerPopupRoute: Routes = [
    {
        path: 'owner-new',
        component: OwnerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Owners'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'owner/:id/edit',
        component: OwnerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Owners'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'owner/:id/delete',
        component: OwnerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Owners'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
