import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('EventMedia e2e test', () => {

    let navBarPage: NavBarPage;
    let eventMediaDialogPage: EventMediaDialogPage;
    let eventMediaComponentsPage: EventMediaComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load EventMedias', () => {
        navBarPage.goToEntity('event-media-my-suffix');
        eventMediaComponentsPage = new EventMediaComponentsPage();
        expect(eventMediaComponentsPage.getTitle()).toMatch(/Event Medias/);

    });

    it('should load create EventMedia dialog', () => {
        eventMediaComponentsPage.clickOnCreateButton();
        eventMediaDialogPage = new EventMediaDialogPage();
        expect(eventMediaDialogPage.getModalTitle()).toMatch(/Create or edit a Event Media/);
        eventMediaDialogPage.close();
    });

    it('should create and save EventMedias', () => {
        eventMediaComponentsPage.clickOnCreateButton();
        eventMediaDialogPage.setCreatedInput(12310020012301);
        expect(eventMediaDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        eventMediaDialogPage.mediaSelectLastOption();
        eventMediaDialogPage.save();
        expect(eventMediaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class EventMediaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-event-media-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class EventMediaDialogPage {
    modalTitle = element(by.css('h4#myEventMediaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    createdInput = element(by.css('input#field_created'));
    mediaSelect = element(by.css('select#field_media'));

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
