/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EventmanagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CommunityDetailComponent } from '../../../../../../main/webapp/app/community/community/community-detail.component';
import { CommunityService } from '../../../../../../main/webapp/app/community/community/community.service';
import { Community } from '../../../../../../main/webapp/app/community/community/community.model';

describe('Component Tests', () => {

    describe('Community Management Detail Component', () => {
        let comp: CommunityDetailComponent;
        let fixture: ComponentFixture<CommunityDetailComponent>;
        let service: CommunityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EventmanagerTestModule],
                declarations: [CommunityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CommunityService,
                    JhiEventManager
                ]
            }).overrideTemplate(CommunityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommunityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommunityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Community(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.community).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
