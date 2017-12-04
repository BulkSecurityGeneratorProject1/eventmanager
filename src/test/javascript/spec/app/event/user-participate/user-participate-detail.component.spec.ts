/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EventmanagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';

import { UserParticipateDetailComponent } from '../../../../../../main/webapp/app/event/user-participate/user-participate-detail.component';
import { UserParticipateService } from '../../../../../../main/webapp/app/event/user-participate/user-participate.service';
import { UserParticipate } from '../../../../../../main/webapp/app/event/user-participate/user-participate.model';

describe('Component Tests', () => {

    describe('UserParticipate Management Detail Component', () => {
        let comp: UserParticipateDetailComponent;
        let fixture: ComponentFixture<UserParticipateDetailComponent>;
        let service: UserParticipateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EventmanagerTestModule],
                declarations: [UserParticipateDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserParticipateService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserParticipateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserParticipateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserParticipateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserParticipate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userParticipate).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
