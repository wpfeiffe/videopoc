import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {VgCoreModule} from 'videogular2/core';
import {VgControlsModule} from 'videogular2/controls';
import {VgOverlayPlayModule} from 'videogular2/overlay-play';
import {VgBufferingModule} from 'videogular2/buffering';

import { VideopocSharedModule } from '../shared';

import { videoRoutes } from './video.route';
import { VideoViewComponent } from './video-view/video-view.component';
import { VideoUploadComponent } from './video-upload/video-upload.component';
import { FileUploadModule } from 'ng2-file-upload/ng2-file-upload';


@NgModule({
  imports: [
    VideopocSharedModule,
    FileUploadModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    RouterModule.forRoot(videoRoutes, { useHash: true })
  ],
  declarations: [
    VideoUploadComponent,
    VideoViewComponent
  ],
  entryComponents: [
  ],
  providers: [
  ],
  schemas: []
})
export class VideoModule {}
