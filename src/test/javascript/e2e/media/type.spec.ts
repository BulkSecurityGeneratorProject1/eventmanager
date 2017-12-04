import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('MediaType e2e test', () => {

    let navBarPage: NavBarPage;
    let mediaTypeDialogPage: MediaTypeDialogPage;
    let mediaTypeComponentsPage: MediaTypeComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MediaTypes', () => {
        navBarPage.goToEntity('media-type-my-suffix');
        mediaTypeComponentsPage = new MediaTypeComponentsPage();
        expect(mediaTypeComponentsPage.getTitle()).toMatch(/Media Types/);

    });

    it('should load create MediaType dialog', () => {
        mediaTypeComponentsPage.clickOnCreateButton();
        mediaTypeDialogPage = new MediaTypeDialogPage();
        expect(mediaTypeDialogPage.getModalTitle()).toMatch(/Create or edit a Media Type/);
        mediaTypeDialogPage.close();
    });

    it('should create and save MediaTypes', () => {
        mediaTypeComponentsPage.clickOnCreateButton();
        mediaTypeDialogPage.setMediaTypeInput('mediaType');
        expect(mediaTypeDialogPage.getMediaTypeInput()).toMatch('mediaType');
        mediaTypeDialogPage.setImageUrlInput('imageUrl');
        expect(mediaTypeDialogPage.getImageUrlInput()).toMatch('imageUrl');
        mediaTypeDialogPage.save();
        expect(mediaTypeDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MediaTypeComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-media-type-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class MediaTypeDialogPage {
    modalTitle = element(by.css('h4#myMediaTypeLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    mediaTypeInput = element(by.css('input#field_mediaType'));
    imageUrlInput = element(by.css('input#field_imageUrl'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setMediaTypeInput = function (mediaType) {
        this.mediaTypeInput.sendKeys(mediaType);
    }

    getMediaTypeInput = function () {
        return this.mediaTypeInput.getAttribute('value');
    }

    setImageUrlInput = function (imageUrl) {
        this.imageUrlInput.sendKeys(imageUrl);
    }

    getImageUrlInput = function () {
        return this.imageUrlInput.getAttribute('value');
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
