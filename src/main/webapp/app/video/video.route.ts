import { Routes } from '@angular/router';
import { VideoUploadComponent } from './video-upload/video-upload.component';
import { VideoViewComponent } from './video-view/video-view.component';

export const videoRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'video-upload',
        component: VideoUploadComponent
      },
      {
        path: 'video-view/:id',
        component: VideoViewComponent
      }
    ]
  }
];
