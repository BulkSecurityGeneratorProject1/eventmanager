import { BaseEntity } from './../../shared';

export class Belong implements BaseEntity {

    constructor(
        public id?: number,
        public userId?: number,
        public created?: any,
        public community?: BaseEntity,
    ) {
    }
}
