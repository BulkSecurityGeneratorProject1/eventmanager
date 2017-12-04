import { BaseEntity } from './../../shared';

export class UserParticipate implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public created?: any,
        public event?: BaseEntity,
    ) {
    }
}
