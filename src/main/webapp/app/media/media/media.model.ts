import { BaseEntity } from './../../shared';

export class Media implements BaseEntity {
    constructor(
        public id?: number,
        public media?: string,
        public mediaUrl?: string,
        public mediaType?: BaseEntity,
        public eventMedias?: BaseEntity[],
        public communityMedias?: BaseEntity[],
        public created?: any
    ) {

    }
}
