import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { VideoType } from './video-type.model';
import { VideoTypeService } from './video-type.service';

@Component({
    selector: 'jhi-video-type-detail',
    templateUrl: './video-type-detail.component.html'
})
export class VideoTypeDetailComponent implements OnInit, OnDestroy {

    videoType: VideoType;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private videoTypeService: VideoTypeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['videoType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.videoTypeService.find(id).subscribe(videoType => {
            this.videoType = videoType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
