import { BaseEntity } from './../../shared';

export class EventMedia implements BaseEntity {
    constructor(
        public id?: number,
        public created?: any,
        public media?: BaseEntity,
        public events?: BaseEntity[],
    ) {
    }
}
