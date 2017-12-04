import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BelongComponent } from './belong.component';
import { BelongDetailComponent } from './belong-detail.component';
import { BelongPopupComponent } from './belong-dialog.component';
import { BelongDeletePopupComponent } from './belong-delete-dialog.component';

export const belongRoute: Routes = [
    {
        path: 'belong',
        component: BelongComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Belongs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'belong/:id',
        component: BelongDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Belongs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const belongPopupRoute: Routes = [
    {
        path: 'belong-new',
        component: BelongPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Belongs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'belong/:id/edit',
        component: BelongPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Belongs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'belong/:id/delete',
        component: BelongDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Belongs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
