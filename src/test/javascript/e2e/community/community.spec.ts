import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Community e2e test', () => {

    let navBarPage: NavBarPage;
    let communityDialogPage: CommunityDialogPage;
    let communityComponentsPage: CommunityComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Communities', () => {
        navBarPage.goToEntity('community-my-suffix');
        communityComponentsPage = new CommunityComponentsPage();
        expect(communityComponentsPage.getTitle()).toMatch(/Communities/);

    });

    it('should load create Community dialog', () => {
        communityComponentsPage.clickOnCreateButton();
        communityDialogPage = new CommunityDialogPage();
        expect(communityDialogPage.getModalTitle()).toMatch(/Create or edit a Community/);
        communityDialogPage.close();
    });

    it('should create and save Communities', () => {
        communityComponentsPage.clickOnCreateButton();
        communityDialogPage.setCommunityInput('community');
        expect(communityDialogPage.getCommunityInput()).toMatch('community');
        communityDialogPage.setDescriptionInput('description');
        expect(communityDialogPage.getDescriptionInput()).toMatch('description');
        communityDialogPage.setImageUrlInput('imageUrl');
        expect(communityDialogPage.getImageUrlInput()).toMatch('imageUrl');
        communityDialogPage.getPrivateGroupInput().isSelected().then(function (selected) {
            if (selected) {
                communityDialogPage.getPrivateGroupInput().click();
                expect(communityDialogPage.getPrivateGroupInput().isSelected()).toBeFalsy();
            } else {
                communityDialogPage.getPrivateGroupInput().click();
                expect(communityDialogPage.getPrivateGroupInput().isSelected()).toBeTruthy();
            }
        });
        communityDialogPage.setCreatedInput(12310020012301);
        expect(communityDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        communityDialogPage.save();
        expect(communityDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CommunityComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-community-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class CommunityDialogPage {
    modalTitle = element(by.css('h4#myCommunityLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    communityInput = element(by.css('input#field_community'));
    descriptionInput = element(by.css('input#field_description'));
    imageUrlInput = element(by.css('input#field_imageUrl'));
    privateGroupInput = element(by.css('input#field_privateGroup'));
    createdInput = element(by.css('input#field_created'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setCommunityInput = function (community) {
        this.communityInput.sendKeys(community);
    }

    getCommunityInput = function () {
        return this.communityInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setImageUrlInput = function (imageUrl) {
        this.imageUrlInput.sendKeys(imageUrl);
    }

    getImageUrlInput = function () {
        return this.imageUrlInput.getAttribute('value');
    }

    getPrivateGroupInput = function () {
        return this.privateGroupInput;
    }
    setCreatedInput = function (created) {
        this.createdInput.sendKeys(created);
    }

    getCreatedInput = function () {
        return this.createdInput.getAttribute('value');
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
