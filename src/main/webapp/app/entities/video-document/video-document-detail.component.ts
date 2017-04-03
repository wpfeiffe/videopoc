import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { VideoDocument } from './video-document.model';
import { VideoDocumentService } from './video-document.service';

@Component({
    selector: 'jhi-video-document-detail',
    templateUrl: './video-document-detail.component.html'
})
export class VideoDocumentDetailComponent implements OnInit, OnDestroy {

    videoDocument: VideoDocument;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private videoDocumentService: VideoDocumentService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['videoDocument']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.videoDocumentService.find(id).subscribe(videoDocument => {
            this.videoDocument = videoDocument;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
