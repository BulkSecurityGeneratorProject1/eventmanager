/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EventmanagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';


import { EventTypeDetailComponent } from '../../../../../../main/webapp/app/event/type/type-detail.component';
import { EventTypeService } from '../../../../../../main/webapp/app/event/type/type.service';
import { EventType } from '../../../../../../main/webapp/app/event/type/type.model';

describe('Component Tests', () => {

    describe('EventType Management Detail Component', () => {
        let comp: EventTypeDetailComponent;
        let fixture: ComponentFixture<EventTypeDetailComponent>;
        let service: EventTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EventmanagerTestModule],
                declarations: [EventTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EventTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(EventTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EventTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EventTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EventType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.eventType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
