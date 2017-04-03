import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VideopocSharedModule } from '../../shared';

import {
    VideoTypeService,
    VideoTypePopupService,
    VideoTypeComponent,
    VideoTypeDetailComponent,
    VideoTypeDialogComponent,
    VideoTypePopupComponent,
    VideoTypeDeletePopupComponent,
    VideoTypeDeleteDialogComponent,
    videoTypeRoute,
    videoTypePopupRoute,
    VideoTypeResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...videoTypeRoute,
    ...videoTypePopupRoute,
];

@NgModule({
    imports: [
        VideopocSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VideoTypeComponent,
        VideoTypeDetailComponent,
        VideoTypeDialogComponent,
        VideoTypeDeleteDialogComponent,
        VideoTypePopupComponent,
        VideoTypeDeletePopupComponent,
    ],
    entryComponents: [
        VideoTypeComponent,
        VideoTypeDialogComponent,
        VideoTypePopupComponent,
        VideoTypeDeleteDialogComponent,
        VideoTypeDeletePopupComponent,
    ],
    providers: [
        VideoTypeService,
        VideoTypePopupService,
        VideoTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VideopocVideoTypeModule {}
