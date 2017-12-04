/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EventmanagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BelongDetailComponent } from '../../../../../../main/webapp/app/community/belong/belong-detail.component';
import { BelongService } from '../../../../../../main/webapp/app/community/belong/belong.service';
import { Belong } from '../../../../../../main/webapp/app/community/belong/belong.model';

describe('Component Tests', () => {

    describe('Belong Management Detail Component', () => {
        let comp: BelongDetailComponent;
        let fixture: ComponentFixture<BelongDetailComponent>;
        let service: BelongService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EventmanagerTestModule],
                declarations: [BelongDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BelongService,
                    JhiEventManager
                ]
            }).overrideTemplate(BelongDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BelongDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BelongService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Belong(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.belong).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
