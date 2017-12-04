import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Media e2e test', () => {

    let navBarPage: NavBarPage;
    let mediaDialogPage: MediaDialogPage;
    let mediaComponentsPage: MediaComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Media', () => {
        navBarPage.goToEntity('media-my-suffix');
        mediaComponentsPage = new MediaComponentsPage();
        expect(mediaComponentsPage.getTitle()).toMatch(/Media/);

    });

    it('should load create Media dialog', () => {
        mediaComponentsPage.clickOnCreateButton();
        mediaDialogPage = new MediaDialogPage();
        expect(mediaDialogPage.getModalTitle()).toMatch(/Create or edit a Media/);
        mediaDialogPage.close();
    });

    it('should create and save Media', () => {
        mediaComponentsPage.clickOnCreateButton();
        mediaDialogPage.setMediaInput('media');
        expect(mediaDialogPage.getMediaInput()).toMatch('media');
        mediaDialogPage.setMediaUrlInput('mediaUrl');
        expect(mediaDialogPage.getMediaUrlInput()).toMatch('mediaUrl');
        mediaDialogPage.getStatusInvitationInput().isSelected().then(function (selected) {
            if (selected) {
                mediaDialogPage.getStatusInvitationInput().click();
                expect(mediaDialogPage.getStatusInvitationInput().isSelected()).toBeFalsy();
            } else {
                mediaDialogPage.getStatusInvitationInput().click();
                expect(mediaDialogPage.getStatusInvitationInput().isSelected()).toBeTruthy();
            }
        });
        mediaDialogPage.setCreatedInput(12310020012301);
        expect(mediaDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        mediaDialogPage.mediaTypeSelectLastOption();
        mediaDialogPage.save();
        expect(mediaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MediaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-media-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class MediaDialogPage {
    modalTitle = element(by.css('h4#myMediaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    mediaInput = element(by.css('input#field_media'));
    mediaUrlInput = element(by.css('input#field_mediaUrl'));
    statusInvitationInput = element(by.css('input#field_statusInvitation'));
    createdInput = element(by.css('input#field_created'));
    mediaTypeSelect = element(by.css('select#field_mediaType'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setMediaInput = function (media) {
        this.mediaInput.sendKeys(media);
    }

    getMediaInput = function () {
        return this.mediaInput.getAttribute('value');
    }

    setMediaUrlInput = function (mediaUrl) {
        this.mediaUrlInput.sendKeys(mediaUrl);
    }

    getMediaUrlInput = function () {
        return this.mediaUrlInput.getAttribute('value');
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

    mediaTypeSelectLastOption = function () {
        this.mediaTypeSelect.all(by.tagName('option')).last().click();
    }

    mediaTypeSelectOption = function (option) {
        this.mediaTypeSelect.sendKeys(option);
    }

    getMediaTypeSelect = function () {
        return this.mediaTypeSelect;
    }

    getMediaTypeSelectedOption = function () {
        return this.mediaTypeSelect.element(by.css('option:checked')).getText();
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
