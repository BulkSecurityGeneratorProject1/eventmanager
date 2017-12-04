import { BaseEntity } from './../../shared';

export class Comment implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public comment?: string,
        public publish?: boolean,
        public created?: any,
        public event?: BaseEntity,
    ) {
        this.publish = false;
    }
}
