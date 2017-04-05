import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { Router } from '@angular/router';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload/ng2-file-upload';
import { CSRFService } from '../../shared/auth/csrf.service';
import {VideoDocumentService} from '../../entities/video-document/video-document.service';
import {VideoDocument} from '../../entities/video-document/video-document.model';
import {AlertService} from 'ng-jhipster';


@Component({
  selector: 'jhi-video-upload',
  templateUrl: './video-upload.component.html',
  styleUrls: ['./video-upload.component.css']
})
export class VideoUploadComponent implements OnInit {

  myUrl = '/upload/singlefile';
  csrfValue = this.csrfService.getCSRF();
  videoDocuments: VideoDocument[];

  uploader: FileUploader = new FileUploader(
    {
      url: this.myUrl,
      headers: [
        {
          name: 'x-xsrf-token', value: this.csrfValue
        }
      ]
    }
  );
  hasBaseDropZoneOver  = false;
  hasAnotherDropZoneOver = false;


  constructor(private csrfService: CSRFService,
              private alertService: AlertService,
              private router: Router,
              private videoDocumentService: VideoDocumentService) { }

  ngOnInit() {
    this.videoDocumentService.query({}).subscribe(
      (res: Response) => this.onSuccess(res.json(), res.headers),
      (res: Response) => this.onError(res.json())
    );

    this.uploader.onCompleteItem = (item:any, response:any, status:any, headers:any) => {
      this.videoDocumentService.query({}).subscribe(
        (res: Response) => this.onSuccess(res.json(), res.headers),
        (res: Response) => this.onError(res.json())
      );
    }
  }

  public fileOverBase(e: any): void {
    this.hasBaseDropZoneOver = e;
  }

  public fileOverAnother(e: any): void {
    this.hasAnotherDropZoneOver = e;
  }

  public onSuccess(data, headers) {
    this.videoDocuments = data;
  }

  public onError(error) {
    this.alertService.error(error.message, null, null);
  }


  public onView(value) {
    console.log('The View value is ' + value);
    this.router.navigate(['/video-view', value]);

  }
  public onConvert(value) {
    console.log('The Convert value is ' + value);

  }

}
