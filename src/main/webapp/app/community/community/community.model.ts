import { BaseEntity } from './../../shared';

export class Community implements BaseEntity {

    constructor(
        public id?: number,
        public community?: string,
        public description?: string,
        public imageUrl?: string,
        public privateGroup?: boolean,
        public created?: any,
        public communityMedias?: BaseEntity[],
        public owners?: BaseEntity[],
        public belongs?: BaseEntity[],
    ) {
        this.privateGroup = false;
    }
}
