import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VideoDocumentDetailComponent } from '../../../../../../main/webapp/app/entities/video-document/video-document-detail.component';
import { VideoDocumentService } from '../../../../../../main/webapp/app/entities/video-document/video-document.service';
import { VideoDocument } from '../../../../../../main/webapp/app/entities/video-document/video-document.model';

describe('Component Tests', () => {

    describe('VideoDocument Management Detail Component', () => {
        let comp: VideoDocumentDetailComponent;
        let fixture: ComponentFixture<VideoDocumentDetailComponent>;
        let service: VideoDocumentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [VideoDocumentDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    VideoDocumentService
                ]
            }).overrideComponent(VideoDocumentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VideoDocumentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VideoDocumentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VideoDocument(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.videoDocument).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
