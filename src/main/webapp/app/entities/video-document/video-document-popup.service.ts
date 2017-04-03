import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VideoDocument } from './video-document.model';
import { VideoDocumentService } from './video-document.service';
@Injectable()
export class VideoDocumentPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private videoDocumentService: VideoDocumentService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.videoDocumentService.find(id).subscribe(videoDocument => {
                this.videoDocumentModalRef(component, videoDocument);
            });
        } else {
            return this.videoDocumentModalRef(component, new VideoDocument());
        }
    }

    videoDocumentModalRef(component: Component, videoDocument: VideoDocument): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.videoDocument = videoDocument;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
