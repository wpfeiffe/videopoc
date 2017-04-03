import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VideopocSharedModule } from '../shared';

import { videoRoutes } from './video.route';
import { VideoListComponent } from './video-list/video-list.component';
import { VideoUploadComponent } from './video-upload/video-upload.component';
import { FileUploadModule } from 'ng2-file-upload/ng2-file-upload';


@NgModule({
  imports: [
    VideopocSharedModule,
    FileUploadModule,
    RouterModule.forRoot(videoRoutes, { useHash: true })
  ],
  declarations: [
    VideoUploadComponent,
    VideoListComponent
  ],
  entryComponents: [
  ],
  providers: [
  ],
  schemas: []
})
export class VideoModule {}
