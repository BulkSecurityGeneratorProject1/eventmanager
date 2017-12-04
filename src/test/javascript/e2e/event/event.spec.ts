import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Event e2e test', () => {

    let navBarPage: NavBarPage;
    let eventDialogPage: EventDialogPage;
    let eventComponentsPage: EventComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Events', () => {
        navBarPage.goToEntity('event-my-suffix');
        eventComponentsPage = new EventComponentsPage();
        expect(eventComponentsPage.getTitle()).toMatch(/Events/);

    });

    it('should load create Event dialog', () => {
        eventComponentsPage.clickOnCreateButton();
        eventDialogPage = new EventDialogPage();
        expect(eventDialogPage.getModalTitle()).toMatch(/Create or edit a Event/);
        eventDialogPage.close();
    });

    it('should create and save Events', () => {
        eventComponentsPage.clickOnCreateButton();
        eventDialogPage.setEventInput('event');
        expect(eventDialogPage.getEventInput()).toMatch('event');
        eventDialogPage.setDescriptionInput('description');
        expect(eventDialogPage.getDescriptionInput()).toMatch('description');
        eventDialogPage.setNumberOfPlacesInput('5');
        expect(eventDialogPage.getNumberOfPlacesInput()).toMatch('5');
        eventDialogPage.setNumberOfPlacesRemainingInput('5');
        expect(eventDialogPage.getNumberOfPlacesRemainingInput()).toMatch('5');
        eventDialogPage.setLatitudeInput('5');
        expect(eventDialogPage.getLatitudeInput()).toMatch('5');
        eventDialogPage.setLongitudeInput('5');
        expect(eventDialogPage.getLongitudeInput()).toMatch('5');
        eventDialogPage.setStartEventInput(12310020012301);
        expect(eventDialogPage.getStartEventInput()).toMatch('2001-12-31T02:30');
        eventDialogPage.setEndEventInput(12310020012301);
        expect(eventDialogPage.getEndEventInput()).toMatch('2001-12-31T02:30');
        eventDialogPage.setImageUrlInput('imageUrl');
        expect(eventDialogPage.getImageUrlInput()).toMatch('imageUrl');
        eventDialogPage.setOthersInput('others');
        expect(eventDialogPage.getOthersInput()).toMatch('others');
        eventDialogPage.getPrivateEventInput().isSelected().then(function (selected) {
            if (selected) {
                eventDialogPage.getPrivateEventInput().click();
                expect(eventDialogPage.getPrivateEventInput().isSelected()).toBeFalsy();
            } else {
                eventDialogPage.getPrivateEventInput().click();
                expect(eventDialogPage.getPrivateEventInput().isSelected()).toBeTruthy();
            }
        });
        eventDialogPage.getStatusEventInput().isSelected().then(function (selected) {
            if (selected) {
                eventDialogPage.getStatusEventInput().click();
                expect(eventDialogPage.getStatusEventInput().isSelected()).toBeFalsy();
            } else {
                eventDialogPage.getStatusEventInput().click();
                expect(eventDialogPage.getStatusEventInput().isSelected()).toBeTruthy();
            }
        });
        eventDialogPage.setCreatedInput(12310020012301);
        expect(eventDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        eventDialogPage.eventTypeSelectLastOption();
        eventDialogPage.eventMediaSelectLastOption();
        eventDialogPage.save();
        expect(eventDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class EventComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-event-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class EventDialogPage {
    modalTitle = element(by.css('h4#myEventLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    eventInput = element(by.css('input#field_event'));
    descriptionInput = element(by.css('input#field_description'));
    numberOfPlacesInput = element(by.css('input#field_numberOfPlaces'));
    numberOfPlacesRemainingInput = element(by.css('input#field_numberOfPlacesRemaining'));
    latitudeInput = element(by.css('input#field_latitude'));
    longitudeInput = element(by.css('input#field_longitude'));
    startEventInput = element(by.css('input#field_startEvent'));
    endEventInput = element(by.css('input#field_endEvent'));
    imageUrlInput = element(by.css('input#field_imageUrl'));
    othersInput = element(by.css('input#field_others'));
    privateEventInput = element(by.css('input#field_privateEvent'));
    statusEventInput = element(by.css('input#field_statusEvent'));
    createdInput = element(by.css('input#field_created'));
    eventTypeSelect = element(by.css('select#field_eventType'));
    eventMediaSelect = element(by.css('select#field_eventMedia'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setEventInput = function (event) {
        this.eventInput.sendKeys(event);
    }

    getEventInput = function () {
        return this.eventInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setNumberOfPlacesInput = function (numberOfPlaces) {
        this.numberOfPlacesInput.sendKeys(numberOfPlaces);
    }

    getNumberOfPlacesInput = function () {
        return this.numberOfPlacesInput.getAttribute('value');
    }

    setNumberOfPlacesRemainingInput = function (numberOfPlacesRemaining) {
        this.numberOfPlacesRemainingInput.sendKeys(numberOfPlacesRemaining);
    }

    getNumberOfPlacesRemainingInput = function () {
        return this.numberOfPlacesRemainingInput.getAttribute('value');
    }

    setLatitudeInput = function (latitude) {
        this.latitudeInput.sendKeys(latitude);
    }

    getLatitudeInput = function () {
        return this.latitudeInput.getAttribute('value');
    }

    setLongitudeInput = function (longitude) {
        this.longitudeInput.sendKeys(longitude);
    }

    getLongitudeInput = function () {
        return this.longitudeInput.getAttribute('value');
    }

    setStartEventInput = function (startEvent) {
        this.startEventInput.sendKeys(startEvent);
    }

    getStartEventInput = function () {
        return this.startEventInput.getAttribute('value');
    }

    setEndEventInput = function (endEvent) {
        this.endEventInput.sendKeys(endEvent);
    }

    getEndEventInput = function () {
        return this.endEventInput.getAttribute('value');
    }

    setImageUrlInput = function (imageUrl) {
        this.imageUrlInput.sendKeys(imageUrl);
    }

    getImageUrlInput = function () {
        return this.imageUrlInput.getAttribute('value');
    }

    setOthersInput = function (others) {
        this.othersInput.sendKeys(others);
    }

    getOthersInput = function () {
        return this.othersInput.getAttribute('value');
    }

    getPrivateEventInput = function () {
        return this.privateEventInput;
    }
    getStatusEventInput = function () {
        return this.statusEventInput;
    }
    setCreatedInput = function (created) {
        this.createdInput.sendKeys(created);
    }

    getCreatedInput = function () {
        return this.createdInput.getAttribute('value');
    }

    eventTypeSelectLastOption = function () {
        this.eventTypeSelect.all(by.tagName('option')).last().click();
    }

    eventTypeSelectOption = function (option) {
        this.eventTypeSelect.sendKeys(option);
    }

    getEventTypeSelect = function () {
        return this.eventTypeSelect;
    }

    getEventTypeSelectedOption = function () {
        return this.eventTypeSelect.element(by.css('option:checked')).getText();
    }

    eventMediaSelectLastOption = function () {
        this.eventMediaSelect.all(by.tagName('option')).last().click();
    }

    eventMediaSelectOption = function (option) {
        this.eventMediaSelect.sendKeys(option);
    }

    getEventMediaSelect = function () {
        return this.eventMediaSelect;
    }

    getEventMediaSelectedOption = function () {
        return this.eventMediaSelect.element(by.css('option:checked')).getText();
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
