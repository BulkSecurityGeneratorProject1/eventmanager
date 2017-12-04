import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EventMediaComponent } from './media.component';
import { EventMediaDetailComponent } from './media-detail.component';
import { EventMediaPopupComponent } from './media-dialog.component';
import { EventMediaDeletePopupComponent } from './media-delete-dialog.component';

export const eventMediaRoute: Routes = [
    {
        path: 'event-media',
        component: EventMediaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event medias'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'event-media/:id',
        component: EventMediaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event medias'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eventMediaPopupRoute: Routes = [
    {
        path: 'event-media-new',
        component: EventMediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event medias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'event-media/:id/edit',
        component: EventMediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event medias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'event-media/:id/delete',
        component: EventMediaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Event medias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
