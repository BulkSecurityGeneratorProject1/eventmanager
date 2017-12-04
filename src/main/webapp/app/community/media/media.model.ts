import { BaseEntity } from './../../shared';

export class CommunityMedia implements BaseEntity {
    constructor(
        public id?: number,
        public created?: any,
        public media?: BaseEntity,
        public community?: BaseEntity,
    ) {
    }
}
