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
import { VideoTypeDetailComponent } from '../../../../../../main/webapp/app/entities/video-type/video-type-detail.component';
import { VideoTypeService } from '../../../../../../main/webapp/app/entities/video-type/video-type.service';
import { VideoType } from '../../../../../../main/webapp/app/entities/video-type/video-type.model';

describe('Component Tests', () => {

    describe('VideoType Management Detail Component', () => {
        let comp: VideoTypeDetailComponent;
        let fixture: ComponentFixture<VideoTypeDetailComponent>;
        let service: VideoTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [VideoTypeDetailComponent],
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
                    VideoTypeService
                ]
            }).overrideComponent(VideoTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VideoTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VideoTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VideoType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.videoType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
