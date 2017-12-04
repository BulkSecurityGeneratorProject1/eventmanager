import { BaseEntity } from './../../shared';

export class Invitation implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public corporate?: string,
        public fullName?: string,
        public email?: string,
        public phone?: string,
        public mobilePhone?: string,
        public message?: string,
        public voiceMessage?: string,
        public sendToEmail?: boolean,
        public sendToCallPhone?: boolean,
        public sendToVoiceMobilePhone?: boolean,
        public sendToSMSMobilePhone?: boolean,
        public periodToSend?: any,
        public statusInvitation?: boolean,
        public created?: any,
        public event?: BaseEntity,
    ) {
        this.sendToEmail = false;
        this.sendToCallPhone = false;
        this.sendToVoiceMobilePhone = false;
        this.sendToSMSMobilePhone = false;
        this.statusInvitation = false;
    }
}
