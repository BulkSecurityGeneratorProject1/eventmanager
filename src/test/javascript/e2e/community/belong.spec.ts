import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Belong e2e test', () => {

    let navBarPage: NavBarPage;
    let belongDialogPage: BelongDialogPage;
    let belongComponentsPage: BelongComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Belongs', () => {
        navBarPage.goToEntity('belong-my-suffix');
        belongComponentsPage = new BelongComponentsPage();
        expect(belongComponentsPage.getTitle()).toMatch(/Belongs/);

    });

    it('should load create Belong dialog', () => {
        belongComponentsPage.clickOnCreateButton();
        belongDialogPage = new BelongDialogPage();
        expect(belongDialogPage.getModalTitle()).toMatch(/Create or edit a Belong/);
        belongDialogPage.close();
    });

    it('should create and save Belongs', () => {
        belongComponentsPage.clickOnCreateButton();
        belongDialogPage.setUserIdInput('5');
        expect(belongDialogPage.getUserIdInput()).toMatch('5');
        belongDialogPage.setCreatedInput(12310020012301);
        expect(belongDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        belongDialogPage.communitySelectLastOption();
        belongDialogPage.save();
        expect(belongDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BelongComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-belong-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class BelongDialogPage {
    modalTitle = element(by.css('h4#myBelongLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userIdInput = element(by.css('input#field_userId'));
    createdInput = element(by.css('input#field_created'));
    communitySelect = element(by.css('select#field_community'));

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

    communitySelectLastOption = function () {
        this.communitySelect.all(by.tagName('option')).last().click();
    }

    communitySelectOption = function (option) {
        this.communitySelect.sendKeys(option);
    }

    getCommunitySelect = function () {
        return this.communitySelect;
    }

    getCommunitySelectedOption = function () {
        return this.communitySelect.element(by.css('option:checked')).getText();
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
