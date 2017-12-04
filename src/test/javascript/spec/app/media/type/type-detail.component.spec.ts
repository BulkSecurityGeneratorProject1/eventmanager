/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EventmanagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';




import { MediaTypeDetailComponent } from '../../../../../../main/webapp/app/media/type/type-detail.component';
import { MediaTypeService } from '../../../../../../main/webapp/app/media/type/type.service';
import { MediaType } from '../../../../../../main/webapp/app/media/type/type.model';

describe('Component Tests', () => {

    describe('MediaType Management Detail Component', () => {
        let comp: MediaTypeDetailComponent;
        let fixture: ComponentFixture<MediaTypeDetailComponent>;
        let service: MediaTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EventmanagerTestModule],
                declarations: [MediaTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MediaTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(MediaTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MediaType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mediaType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
