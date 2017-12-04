import { BaseEntity } from './../../shared';

export class MediaType implements BaseEntity {
    constructor(
        public id?: number,
        public mediaType?: string,
        public imageUrl?: string,
        public media?: BaseEntity[],
    ) {
    }
}
