import { Routes } from '@angular/router';
import { VideoUploadComponent } from './video-upload/video-upload.component';
import { VideoListComponent } from './video-list/video-list.component';

export const videoRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'video-upload',
        component: VideoUploadComponent
      },
      {
        path: 'video-list',
        component: VideoListComponent
      }
    ]
  }
];
