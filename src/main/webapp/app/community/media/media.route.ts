import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CommunityMediaComponent } from './media.component';
import { CommunityMediaDetailComponent } from './media-detail.component';
import { CommunityMediaPopupComponent } from './media-dialog.component';
import { CommunityMediaDeletePopupComponent } from './media-delete-dialog.component';

export const communityMediaRoute: Routes = [
    {
        path: 'community-media',
        component: CommunityMediaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Community medias'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'community-media/:id',
        component: CommunityMediaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Community medias'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const communityMediaPopupRoute: Routes = [
    {
        path: 'community-media-new',
        component: CommunityMediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Community medias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'community-media/:id/edit',
        component: CommunityMediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Community medias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'community-media/:id/delete',
        component: CommunityMediaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Community medias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
