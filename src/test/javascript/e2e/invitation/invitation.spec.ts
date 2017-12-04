import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Invitation e2e test', () => {

    let navBarPage: NavBarPage;
    let invitationDialogPage: InvitationDialogPage;
    let invitationComponentsPage: InvitationComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Invitations', () => {
        navBarPage.goToEntity('invitation-my-suffix');
        invitationComponentsPage = new InvitationComponentsPage();
        expect(invitationComponentsPage.getTitle()).toMatch(/Invitations/);

    });

    it('should load create Invitation dialog', () => {
        invitationComponentsPage.clickOnCreateButton();
        invitationDialogPage = new InvitationDialogPage();
        expect(invitationDialogPage.getModalTitle()).toMatch(/Create or edit a Invitation/);
        invitationDialogPage.close();
    });

    it('should create and save Invitations', () => {
        invitationComponentsPage.clickOnCreateButton();
        invitationDialogPage.setUserIdInput('5');
        expect(invitationDialogPage.getUserIdInput()).toMatch('5');
        invitationDialogPage.setCorporateInput('corporate');
        expect(invitationDialogPage.getCorporateInput()).toMatch('corporate');
        invitationDialogPage.setFullNameInput('fullName');
        expect(invitationDialogPage.getFullNameInput()).toMatch('fullName');
        invitationDialogPage.setEmailInput('email');
        expect(invitationDialogPage.getEmailInput()).toMatch('email');
        invitationDialogPage.setPhoneInput('phone');
        expect(invitationDialogPage.getPhoneInput()).toMatch('phone');
        invitationDialogPage.setMobilePhoneInput('mobilePhone');
        expect(invitationDialogPage.getMobilePhoneInput()).toMatch('mobilePhone');
        invitationDialogPage.setMessageInput('message');
        expect(invitationDialogPage.getMessageInput()).toMatch('message');
        invitationDialogPage.setVoiceMessageInput('voiceMessage');
        expect(invitationDialogPage.getVoiceMessageInput()).toMatch('voiceMessage');
        invitationDialogPage.getSendToEmailInput().isSelected().then(function (selected) {
            if (selected) {
                invitationDialogPage.getSendToEmailInput().click();
                expect(invitationDialogPage.getSendToEmailInput().isSelected()).toBeFalsy();
            } else {
                invitationDialogPage.getSendToEmailInput().click();
                expect(invitationDialogPage.getSendToEmailInput().isSelected()).toBeTruthy();
            }
        });
        invitationDialogPage.getSendToCallPhoneInput().isSelected().then(function (selected) {
            if (selected) {
                invitationDialogPage.getSendToCallPhoneInput().click();
                expect(invitationDialogPage.getSendToCallPhoneInput().isSelected()).toBeFalsy();
            } else {
                invitationDialogPage.getSendToCallPhoneInput().click();
                expect(invitationDialogPage.getSendToCallPhoneInput().isSelected()).toBeTruthy();
            }
        });
        invitationDialogPage.getSendToVoiceMobilePhoneInput().isSelected().then(function (selected) {
            if (selected) {
                invitationDialogPage.getSendToVoiceMobilePhoneInput().click();
                expect(invitationDialogPage.getSendToVoiceMobilePhoneInput().isSelected()).toBeFalsy();
            } else {
                invitationDialogPage.getSendToVoiceMobilePhoneInput().click();
                expect(invitationDialogPage.getSendToVoiceMobilePhoneInput().isSelected()).toBeTruthy();
            }
        });
        invitationDialogPage.getSendToSMSMobilePhoneInput().isSelected().then(function (selected) {
            if (selected) {
                invitationDialogPage.getSendToSMSMobilePhoneInput().click();
                expect(invitationDialogPage.getSendToSMSMobilePhoneInput().isSelected()).toBeFalsy();
            } else {
                invitationDialogPage.getSendToSMSMobilePhoneInput().click();
                expect(invitationDialogPage.getSendToSMSMobilePhoneInput().isSelected()).toBeTruthy();
            }
        });
        invitationDialogPage.setPeriodToSendInput(12310020012301);
        expect(invitationDialogPage.getPeriodToSendInput()).toMatch('2001-12-31T02:30');
        invitationDialogPage.getStatusInvitationInput().isSelected().then(function (selected) {
            if (selected) {
                invitationDialogPage.getStatusInvitationInput().click();
                expect(invitationDialogPage.getStatusInvitationInput().isSelected()).toBeFalsy();
            } else {
                invitationDialogPage.getStatusInvitationInput().click();
                expect(invitationDialogPage.getStatusInvitationInput().isSelected()).toBeTruthy();
            }
        });
        invitationDialogPage.setCreatedInput(12310020012301);
        expect(invitationDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        invitationDialogPage.eventSelectLastOption();
        invitationDialogPage.save();
        expect(invitationDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class InvitationComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-invitation-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class InvitationDialogPage {
    modalTitle = element(by.css('h4#myInvitationLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userIdInput = element(by.css('input#field_userId'));
    corporateInput = element(by.css('input#field_corporate'));
    fullNameInput = element(by.css('input#field_fullName'));
    emailInput = element(by.css('input#field_email'));
    phoneInput = element(by.css('input#field_phone'));
    mobilePhoneInput = element(by.css('input#field_mobilePhone'));
    messageInput = element(by.css('input#field_message'));
    voiceMessageInput = element(by.css('input#field_voiceMessage'));
    sendToEmailInput = element(by.css('input#field_sendToEmail'));
    sendToCallPhoneInput = element(by.css('input#field_sendToCallPhone'));
    sendToVoiceMobilePhoneInput = element(by.css('input#field_sendToVoiceMobilePhone'));
    sendToSMSMobilePhoneInput = element(by.css('input#field_sendToSMSMobilePhone'));
    periodToSendInput = element(by.css('input#field_periodToSend'));
    statusInvitationInput = element(by.css('input#field_statusInvitation'));
    createdInput = element(by.css('input#field_created'));
    eventSelect = element(by.css('select#field_event'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setUserIdInput = function (userId) {
        this.userIdInput.sendKeys(userId);
    }

    getUserIdInput = function () {
        return this.userIdInput.getAttribute('value');
    }

    setCorporateInput = function (corporate) {
        this.corporateInput.sendKeys(corporate);
    }

    getCorporateInput = function () {
        return this.corporateInput.getAttribute('value');
    }

    setFullNameInput = function (fullName) {
        this.fullNameInput.sendKeys(fullName);
    }

    getFullNameInput = function () {
        return this.fullNameInput.getAttribute('value');
    }

    setEmailInput = function (email) {
        this.emailInput.sendKeys(email);
    }

    getEmailInput = function () {
        return this.emailInput.getAttribute('value');
    }

    setPhoneInput = function (phone) {
        this.phoneInput.sendKeys(phone);
    }

    getPhoneInput = function () {
        return this.phoneInput.getAttribute('value');
    }

    setMobilePhoneInput = function (mobilePhone) {
        this.mobilePhoneInput.sendKeys(mobilePhone);
    }

    getMobilePhoneInput = function () {
        return this.mobilePhoneInput.getAttribute('value');
    }

    setMessageInput = function (message) {
        this.messageInput.sendKeys(message);
    }

    getMessageInput = function () {
        return this.messageInput.getAttribute('value');
    }

    setVoiceMessageInput = function (voiceMessage) {
        this.voiceMessageInput.sendKeys(voiceMessage);
    }

    getVoiceMessageInput = function () {
        return this.voiceMessageInput.getAttribute('value');
    }

    getSendToEmailInput = function () {
        return this.sendToEmailInput;
    }
    getSendToCallPhoneInput = function () {
        return this.sendToCallPhoneInput;
    }
    getSendToVoiceMobilePhoneInput = function () {
        return this.sendToVoiceMobilePhoneInput;
    }
    getSendToSMSMobilePhoneInput = function () {
        return this.sendToSMSMobilePhoneInput;
    }
    setPeriodToSendInput = function (periodToSend) {
        this.periodToSendInput.sendKeys(periodToSend);
    }

    getPeriodToSendInput = function () {
        return this.periodToSendInput.getAttribute('value');
    }

    getStatusInvitationInput = function () {
        return this.statusInvitationInput;
    }
    setCreatedInput = function (created) {
        this.createdInput.sendKeys(created);
    }

    getCreatedInput = function () {
        return this.createdInput.getAttribute('value');
    }

    eventSelectLastOption = function () {
        this.eventSelect.all(by.tagName('option')).last().click();
    }

    eventSelectOption = function (option) {
        this.eventSelect.sendKeys(option);
    }

    getEventSelect = function () {
        return this.eventSelect;
    }

    getEventSelectedOption = function () {
        return this.eventSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
