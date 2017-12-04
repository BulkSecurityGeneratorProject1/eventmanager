import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('UserParticipate e2e test', () => {

    let navBarPage: NavBarPage;
    let userParticipateDialogPage: UserParticipateDialogPage;
    let userParticipateComponentsPage: UserParticipateComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserParticipates', () => {
        navBarPage.goToEntity('user-participate-my-suffix');
        userParticipateComponentsPage = new UserParticipateComponentsPage();
        expect(userParticipateComponentsPage.getTitle()).toMatch(/User Participates/);

    });

    it('should load create UserParticipate dialog', () => {
        userParticipateComponentsPage.clickOnCreateButton();
        userParticipateDialogPage = new UserParticipateDialogPage();
        expect(userParticipateDialogPage.getModalTitle()).toMatch(/Create or edit a User Participate/);
        userParticipateDialogPage.close();
    });

    it('should create and save UserParticipates', () => {
        userParticipateComponentsPage.clickOnCreateButton();
        userParticipateDialogPage.setUserIdInput('5');
        expect(userParticipateDialogPage.getUserIdInput()).toMatch('5');
        userParticipateDialogPage.setCreatedInput(12310020012301);
        expect(userParticipateDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        userParticipateDialogPage.eventSelectLastOption();
        userParticipateDialogPage.save();
        expect(userParticipateDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UserParticipateComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-user-participate-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class UserParticipateDialogPage {
    modalTitle = element(by.css('h4#myUserParticipateLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userIdInput = element(by.css('input#field_userId'));
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
