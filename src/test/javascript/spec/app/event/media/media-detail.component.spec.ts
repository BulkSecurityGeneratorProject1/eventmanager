/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EventmanagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EventMediaDetailComponent } from '../../../../../../main/webapp/app/event/media/media-detail.component';
import { EventMediaService } from '../../../../../../main/webapp/app/event/media/media.service';
import { EventMedia } from '../../../../../../main/webapp/app/event/media/media.model';

describe('Component Tests', () => {

    describe('EventMedia Management Detail Component', () => {
        let comp: EventMediaDetailComponent;
        let fixture: ComponentFixture<EventMediaDetailComponent>;
        let service: EventMediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EventmanagerTestModule],
                declarations: [EventMediaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EventMediaService,
                    JhiEventManager
                ]
            }).overrideTemplate(EventMediaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EventMediaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EventMediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EventMedia(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.eventMedia).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
