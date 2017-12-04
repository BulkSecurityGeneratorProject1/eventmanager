import { BaseEntity } from './../../shared';

export class EventType implements BaseEntity {
    constructor(
        public id?: number,
        public eventType?: string,
        public imageUrl?: string,
        public events?: BaseEntity[],
    ) {
    }
}
