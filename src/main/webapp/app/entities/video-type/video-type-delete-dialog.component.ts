import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { VideoType } from './video-type.model';
import { VideoTypePopupService } from './video-type-popup.service';
import { VideoTypeService } from './video-type.service';

@Component({
    selector: 'jhi-video-type-delete-dialog',
    templateUrl: './video-type-delete-dialog.component.html'
})
export class VideoTypeDeleteDialogComponent {

    videoType: VideoType;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private videoTypeService: VideoTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['videoType']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.videoTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'videoTypeListModification',
                content: 'Deleted an videoType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-video-type-delete-popup',
    template: ''
})
export class VideoTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private videoTypePopupService: VideoTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.videoTypePopupService
                .open(VideoTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
