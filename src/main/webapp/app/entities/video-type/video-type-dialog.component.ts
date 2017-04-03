import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { VideoType } from './video-type.model';
import { VideoTypePopupService } from './video-type-popup.service';
import { VideoTypeService } from './video-type.service';

@Component({
    selector: 'jhi-video-type-dialog',
    templateUrl: './video-type-dialog.component.html'
})
export class VideoTypeDialogComponent implements OnInit {

    videoType: VideoType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private videoTypeService: VideoTypeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['videoType']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.videoType.id !== undefined) {
            this.videoTypeService.update(this.videoType)
                .subscribe((res: VideoType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.videoTypeService.create(this.videoType)
                .subscribe((res: VideoType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: VideoType) {
        this.eventManager.broadcast({ name: 'videoTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-video-type-popup',
    template: ''
})
export class VideoTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private videoTypePopupService: VideoTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.videoTypePopupService
                    .open(VideoTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.videoTypePopupService
                    .open(VideoTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
