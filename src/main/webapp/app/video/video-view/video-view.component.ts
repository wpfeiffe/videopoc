import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'jhi-video-view',
  templateUrl: './video-view.component.html',
  styles: []
})
export class VideoViewComponent implements OnInit {

  videoToView = 0;
  videoFound = false;
  sources: Array<Object>;

  constructor(
    private route: ActivatedRoute,
    private router: Router) {


  }

  ngOnInit() {
    this.route.params.subscribe(value => {
      console.log('The value is ' + value);
      this.videoToView = value.id;

      this.sources = [
        {
          src: `/document/${this.videoToView}`,
          type: 'video/mp4'
        },
        {
          src: `/document/${this.videoToView}`,
          type: 'video/ogg'
        },
        {
          src: `/document/${this.videoToView}`,
          type: 'video/webm'
        }
      ];

      this.videoFound = true;

    });
  }
}
