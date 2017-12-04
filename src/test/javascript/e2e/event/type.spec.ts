import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('EventType e2e test', () => {

    let navBarPage: NavBarPage;
    let eventTypeDialogPage: EventTypeDialogPage;
    let eventTypeComponentsPage: EventTypeComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load EventTypes', () => {
        navBarPage.goToEntity('event-type-my-suffix');
        eventTypeComponentsPage = new EventTypeComponentsPage();
        expect(eventTypeComponentsPage.getTitle()).toMatch(/Event Types/);

    });

    it('should load create EventType dialog', () => {
        eventTypeComponentsPage.clickOnCreateButton();
        eventTypeDialogPage = new EventTypeDialogPage();
        expect(eventTypeDialogPage.getModalTitle()).toMatch(/Create or edit a Event Type/);
        eventTypeDialogPage.close();
    });

    it('should create and save EventTypes', () => {
        eventTypeComponentsPage.clickOnCreateButton();
        eventTypeDialogPage.setEventTypeInput('eventType');
        expect(eventTypeDialogPage.getEventTypeInput()).toMatch('eventType');
        eventTypeDialogPage.setImageUrlInput('imageUrl');
        expect(eventTypeDialogPage.getImageUrlInput()).toMatch('imageUrl');
        eventTypeDialogPage.save();
        expect(eventTypeDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class EventTypeComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-event-type-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class EventTypeDialogPage {
    modalTitle = element(by.css('h4#myEventTypeLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    eventTypeInput = element(by.css('input#field_eventType'));
    imageUrlInput = element(by.css('input#field_imageUrl'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setEventTypeInput = function (eventType) {
        this.eventTypeInput.sendKeys(eventType);
    }

    getEventTypeInput = function () {
        return this.eventTypeInput.getAttribute('value');
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
