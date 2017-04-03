import { Component, OnInit } from '@angular/core';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload/ng2-file-upload';
import { CSRFService } from '../../shared/auth/csrf.service';


@Component({
  selector: 'jhi-video-upload',
  templateUrl: './video-upload.component.html',
  styleUrls: ['./video-upload.component.css']
})
export class VideoUploadComponent implements OnInit {

  myUrl = '/upload/singlefile';
  csrfValue = this.csrfService.getCSRF();

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


  constructor(private csrfService: CSRFService) { }

  ngOnInit() {
  }

  public fileOverBase(e: any): void {
    this.hasBaseDropZoneOver = e;
  }

  public fileOverAnother(e: any): void {
    this.hasAnotherDropZoneOver = e;
  }

}
