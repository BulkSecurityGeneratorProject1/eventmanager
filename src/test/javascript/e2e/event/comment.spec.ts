import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Comment e2e test', () => {

    let navBarPage: NavBarPage;
    let commentDialogPage: CommentDialogPage;
    let commentComponentsPage: CommentComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-event-manager.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Comments', () => {
        navBarPage.goToEntity('comment-my-suffix');
        commentComponentsPage = new CommentComponentsPage();
        expect(commentComponentsPage.getTitle()).toMatch(/Comments/);

    });

    it('should load create Comment dialog', () => {
        commentComponentsPage.clickOnCreateButton();
        commentDialogPage = new CommentDialogPage();
        expect(commentDialogPage.getModalTitle()).toMatch(/Create or edit a Comment/);
        commentDialogPage.close();
    });

    it('should create and save Comments', () => {
        commentComponentsPage.clickOnCreateButton();
        commentDialogPage.setUserIdInput('5');
        expect(commentDialogPage.getUserIdInput()).toMatch('5');
        commentDialogPage.setCommentInput('comment');
        expect(commentDialogPage.getCommentInput()).toMatch('comment');
        commentDialogPage.getPublishInput().isSelected().then(function (selected) {
            if (selected) {
                commentDialogPage.getPublishInput().click();
                expect(commentDialogPage.getPublishInput().isSelected()).toBeFalsy();
            } else {
                commentDialogPage.getPublishInput().click();
                expect(commentDialogPage.getPublishInput().isSelected()).toBeTruthy();
            }
        });
        commentDialogPage.setCreatedInput(12310020012301);
        expect(commentDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        commentDialogPage.eventSelectLastOption();
        commentDialogPage.save();
        expect(commentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CommentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-comment-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class CommentDialogPage {
    modalTitle = element(by.css('h4#myCommentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userIdInput = element(by.css('input#field_userId'));
    commentInput = element(by.css('input#field_comment'));
    publishInput = element(by.css('input#field_publish'));
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

    setCommentInput = function (comment) {
        this.commentInput.sendKeys(comment);
    }

    getCommentInput = function () {
        return this.commentInput.getAttribute('value');
    }

    getPublishInput = function () {
        return this.publishInput;
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
