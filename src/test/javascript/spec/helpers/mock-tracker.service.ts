import { SpyObject } from './spyobject';
import { TrackerService } from '../../../../main/webapp/app/shared/tracker/tracker.service';

export class MockTrackerService extends SpyObject {

    constructor() {
        super(TrackerService);
    }

    connect() {}
}
