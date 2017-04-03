import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { VideoDocument } from './video-document.model';
import { VideoDocumentPopupService } from './video-document-popup.service';
import { VideoDocumentService } from './video-document.service';
import { VideoType, VideoTypeService } from '../video-type';

@Component({
    selector: 'jhi-video-document-dialog',
    templateUrl: './video-document-dialog.component.html'
})
export class VideoDocumentDialogComponent implements OnInit {

    videoDocument: VideoDocument;
    authorities: any[];
    isSaving: boolean;

    videotypes: VideoType[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private videoDocumentService: VideoDocumentService,
        private videoTypeService: VideoTypeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['videoDocument']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.videoTypeService.query().subscribe(
            (res: Response) => { this.videotypes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.videoDocument.id !== undefined) {
            this.videoDocumentService.update(this.videoDocument)
                .subscribe((res: VideoDocument) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.videoDocumentService.create(this.videoDocument)
                .subscribe((res: VideoDocument) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: VideoDocument) {
        this.eventManager.broadcast({ name: 'videoDocumentListModification', content: 'OK'});
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

    trackVideoTypeById(index: number, item: VideoType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-video-document-popup',
    template: ''
})
export class VideoDocumentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private videoDocumentPopupService: VideoDocumentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.videoDocumentPopupService
                    .open(VideoDocumentDialogComponent, params['id']);
            } else {
                this.modalRef = this.videoDocumentPopupService
                    .open(VideoDocumentDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
