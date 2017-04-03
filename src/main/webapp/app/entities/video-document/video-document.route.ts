import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { VideoDocumentComponent } from './video-document.component';
import { VideoDocumentDetailComponent } from './video-document-detail.component';
import { VideoDocumentPopupComponent } from './video-document-dialog.component';
import { VideoDocumentDeletePopupComponent } from './video-document-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class VideoDocumentResolvePagingParams implements Resolve<any> {

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

export const videoDocumentRoute: Routes = [
  {
    path: 'video-document',
    component: VideoDocumentComponent,
    resolve: {
      'pagingParams': VideoDocumentResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoDocument.home.title'
    }
  }, {
    path: 'video-document/:id',
    component: VideoDocumentDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoDocument.home.title'
    }
  }
];

export const videoDocumentPopupRoute: Routes = [
  {
    path: 'video-document-new',
    component: VideoDocumentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoDocument.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'video-document/:id/edit',
    component: VideoDocumentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoDocument.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'video-document/:id/delete',
    component: VideoDocumentDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'videopocApp.videoDocument.home.title'
    },
    outlet: 'popup'
  }
];
