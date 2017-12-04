import { BaseEntity } from '../shared';

export class Event implements BaseEntity {
    constructor(
        public id?: number,
        public event?: string,
        public description?: string,
        public numberOfPlaces?: number,
        public numberOfPlacesRemaining?: number,
        public latitude?: number,
        public longitude?: number,
        public startEvent?: any,
        public endEvent?: any,
        public imageUrl?: string,
        public others?: string,
        public privateEvent?: boolean,
        public statusEvent?: boolean,
        public created?: any,
        public eventType?: BaseEntity,
        public comments?: BaseEntity[],
        public invitations?: BaseEntity[],
        public userParticipates?: BaseEntity[],
        public eventMedia?: BaseEntity,
    ) {
        this.privateEvent = false;
        this.statusEvent = false;
    }
}
