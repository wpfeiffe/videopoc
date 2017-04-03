import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { VideoTypeComponent } from './video-type.component';
import { VideoTypeDetailComponent } from './video-type-detail.component';
import { VideoTypePopupComponent } from './video-type-dialog.component';
import { VideoTypeDeletePopupComponent } from './video-type-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class VideoTypeResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const videoTypeRoute: Routes = [
  {
    path: 'video-type',
    component: VideoTypeComponent,
    resolve: {
      'pagingParams': VideoTypeResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoType.home.title'
    }
  }, {
    path: 'video-type/:id',
    component: VideoTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoType.home.title'
    }
  }
];

export const videoTypePopupRoute: Routes = [
  {
    path: 'video-type-new',
    component: VideoTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'video-type/:id/edit',
    component: VideoTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'video-type/:id/delete',
    component: VideoTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoType.home.title'
    },
    outlet: 'popup'
  }
];
