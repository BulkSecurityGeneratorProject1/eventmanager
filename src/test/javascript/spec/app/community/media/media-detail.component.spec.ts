/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EventmanagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CommunityMediaDetailComponent } from '../../../../../../main/webapp/app/community/media/media-detail.component';
import { CommunityMediaService } from '../../../../../../main/webapp/app/community/media/media.service';
import { CommunityMedia } from '../../../../../../main/webapp/app/community/media/media.model';

describe('Component Tests', () => {

    describe('CommunityMedia Management Detail Component', () => {
        let comp: CommunityMediaDetailComponent;
        let fixture: ComponentFixture<CommunityMediaDetailComponent>;
        let service: CommunityMediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EventmanagerTestModule],
                declarations: [CommunityMediaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CommunityMediaService,
                    JhiEventManager
                ]
            }).overrideTemplate(CommunityMediaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommunityMediaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommunityMediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CommunityMedia(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.communityMedia).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
