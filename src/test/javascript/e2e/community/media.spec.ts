import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('CommunityMedia e2e test', () => {

    let navBarPage: NavBarPage;
    let communityMediaDialogPage: CommunityMediaDialogPage;
    let communityMediaComponentsPage: CommunityMediaComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CommunityMedias', () => {
        navBarPage.goToEntity('community-media-my-suffix');
        communityMediaComponentsPage = new CommunityMediaComponentsPage();
        expect(communityMediaComponentsPage.getTitle()).toMatch(/Community Medias/);

    });

    it('should load create CommunityMedia dialog', () => {
        communityMediaComponentsPage.clickOnCreateButton();
        communityMediaDialogPage = new CommunityMediaDialogPage();
        expect(communityMediaDialogPage.getModalTitle()).toMatch(/Create or edit a Community Media/);
        communityMediaDialogPage.close();
    });

    it('should create and save CommunityMedias', () => {
        communityMediaComponentsPage.clickOnCreateButton();
        communityMediaDialogPage.setCreatedInput(12310020012301);
        expect(communityMediaDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        communityMediaDialogPage.mediaSelectLastOption();
        communityMediaDialogPage.communitySelectLastOption();
        communityMediaDialogPage.save();
        expect(communityMediaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CommunityMediaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-community-media-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class CommunityMediaDialogPage {
    modalTitle = element(by.css('h4#myCommunityMediaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    createdInput = element(by.css('input#field_created'));
    mediaSelect = element(by.css('select#field_media'));
    communitySelect = element(by.css('select#field_community'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setCreatedInput = function (created) {
        this.createdInput.sendKeys(created);
    }

    getCreatedInput = function () {
        return this.createdInput.getAttribute('value');
    }

    mediaSelectLastOption = function () {
        this.mediaSelect.all(by.tagName('option')).last().click();
    }

    mediaSelectOption = function (option) {
        this.mediaSelect.sendKeys(option);
    }

    getMediaSelect = function () {
        return this.mediaSelect;
    }

    getMediaSelectedOption = function () {
        return this.mediaSelect.element(by.css('option:checked')).getText();
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
