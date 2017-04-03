import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VideopocSharedModule } from '../../shared';

import {
    VideoDocumentService,
    VideoDocumentPopupService,
    VideoDocumentComponent,
    VideoDocumentDetailComponent,
    VideoDocumentDialogComponent,
    VideoDocumentPopupComponent,
    VideoDocumentDeletePopupComponent,
    VideoDocumentDeleteDialogComponent,
    videoDocumentRoute,
    videoDocumentPopupRoute,
    VideoDocumentResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...videoDocumentRoute,
    ...videoDocumentPopupRoute,
];

@NgModule({
    imports: [
        VideopocSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VideoDocumentComponent,
        VideoDocumentDetailComponent,
        VideoDocumentDialogComponent,
        VideoDocumentDeleteDialogComponent,
        VideoDocumentPopupComponent,
        VideoDocumentDeletePopupComponent,
    ],
    entryComponents: [
        VideoDocumentComponent,
        VideoDocumentDialogComponent,
        VideoDocumentPopupComponent,
        VideoDocumentDeleteDialogComponent,
        VideoDocumentDeletePopupComponent,
    ],
    providers: [
        VideoDocumentService,
        VideoDocumentPopupService,
        VideoDocumentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VideopocVideoDocumentModule {}
