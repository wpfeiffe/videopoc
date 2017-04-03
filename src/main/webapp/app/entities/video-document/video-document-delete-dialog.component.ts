import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { VideoDocument } from './video-document.model';
import { VideoDocumentPopupService } from './video-document-popup.service';
import { VideoDocumentService } from './video-document.service';

@Component({
    selector: 'jhi-video-document-delete-dialog',
    templateUrl: './video-document-delete-dialog.component.html'
})
export class VideoDocumentDeleteDialogComponent {

    videoDocument: VideoDocument;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private videoDocumentService: VideoDocumentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['videoDocument']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.videoDocumentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'videoDocumentListModification',
                content: 'Deleted an videoDocument'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-video-document-delete-popup',
    template: ''
})
export class VideoDocumentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private videoDocumentPopupService: VideoDocumentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.videoDocumentPopupService
                .open(VideoDocumentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
