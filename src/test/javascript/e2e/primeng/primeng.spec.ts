import { browser, element, by } from 'protractor';

describe('administration', () => {

    const username = element(by.id('username'));
    const password = element(by.id('password'));
    const accountMenu = element(by.id('account-menu'));
    const primengMenu = element(by.id('primeng-menu'));
    const login = element(by.id('login'));
    const logout = element(by.id('logout'));

    beforeAll(() => {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
        browser.waitForAngular();
    });

    beforeEach(() => {
        primengMenu.click();
    });

    it('should load BarChart', () => {
        element(by.css('[routerLink="barchart"]')).click();
            const expect1 = /BarChart/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load DoughnutChart', () => {
        element(by.css('[routerLink="doughnutchart"]')).click();
            const expect1 = /DoughnutChart/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load LineChart', () => {
        element(by.css('[routerLink="linechart"]')).click();
            const expect1 = /LineChart/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load PieChart', () => {
        element(by.css('[routerLink="piechart"]')).click();
            const expect1 = /PieChart/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load PolarAreaChart', () => {
        element(by.css('[routerLink="polarareachart"]')).click();
            const expect1 = /PolarAreaChart/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load RadarChart', () => {
        element(by.css('[routerLink="radarchart"]')).click();
            const expect1 = /RadarChart/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load InputText', () => {
        element(by.css('[routerLink="inputtext"]')).click();
            const expect1 = /InputText/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load InputGroup', () => {
        element(by.css('[routerLink="inputgroup"]')).click();
            const expect1 = /InputGroup/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load InputTextArea', () => {
        element(by.css('[routerLink="inputtextarea"]')).click();
            const expect1 = /InputTextArea/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Calendar', () => {
        element(by.css('[routerLink="calendar"]')).click();
            const expect1 = /Calendar/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Chips', () => {
        element(by.css('[routerLink="chips"]')).click();
            const expect1 = /Chips/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load InputSwitch', () => {
        element(by.css('[routerLink="inputswitch"]')).click();
            const expect1 = /InputSwitch/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load InputMask', () => {
        element(by.css('[routerLink="inputmask"]')).click();
            const expect1 = /InputMask/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load PasswordIndicator', () => {
        element(by.css('[routerLink="password"]')).click();
            const expect1 = /Password/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Rating', () => {
        element(by.css('[routerLink="rating"]')).click();
            const expect1 = /Rating/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Spinner', () => {
        element(by.css('[routerLink="spinner"]')).click();
            const expect1 = /Spinner/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load ToggleButton', () => {
        element(by.css('[routerLink="togglebutton"]')).click();
            const expect1 = /ToggleButton/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load AutoComplete', () => {
        element(by.css('[routerLink="autocomplete"]')).click();
            const expect1 = /AutoComplete/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Checkbox', () => {
        element(by.css('[routerLink="checkbox"]')).click();
            const expect1 = /Checkbox/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load ColorPicker', () => {
        element(by.css('[routerLink="ColorPicker"]')).click();
            const expect1 = /ColorPicker/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Editor', () => {
        element(by.css('[routerLink="editor"]')).click();
            const expect1 = /Editor/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Listbox', () => {
        element(by.css('[routerLink="listbox"]')).click();
            const expect1 = /Listbox/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Select', () => {
        element(by.css('[routerLink="select"]')).click();
            const expect1 = /Select/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load RadioButton', () => {
        element(by.css('[routerLink="select"]')).click();
            const expect1 = /Select/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Slider', () => {
        element(by.css('[routerLink="slider"]')).click();
            const expect1 = /Slider/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load SelectButton', () => {
        element(by.css('[routerLink="selectbutton"]')).click();
            const expect1 = /SelectButton/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load DataTable', () => {
        element(by.css('[routerLink="datatable"]')).click();
            const expect1 = /DataTable/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load DataGrid', () => {
        element(by.css('[routerLink="datagrid"]')).click();
            const expect1 = /DataGrid/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Carousel', () => {
        element(by.css('[routerLink="carousel"]')).click();
            const expect1 = /Carousel/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load OrderList', () => {
        element(by.css('[routerLink="orderlist"]')).click();
            const expect1 = /OrderList/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load DataList', () => {
        element(by.css('[routerLink="datalist"]')).click();
            const expect1 = /DataList/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Paginator', () => {
        element(by.css('[routerLink="paginator"]')).click();
            const expect1 = /Paginator/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Schedule', () => {
        element(by.css('[routerLink="schedule"]')).click();
            const expect1 = /Schedule/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load TreeTable', () => {
        element(by.css('[routerLink="treetable"]')).click();
            const expect1 = /TreeTable/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load DataScroller', () => {
        element(by.css('[routerLink="datascroller"]')).click();
            const expect1 = /DataScroller/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load OrgChart', () => {
        element(by.css('[routerLink="orgchart"]')).click();
            const expect1 = /OrgChart/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Gmap', () => {
        element(by.css('[routerLink="gmap"]')).click();
            const expect1 = /Gmap/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load PickList', () => {
        element(by.css('[routerLink="picklist"]')).click();
            const expect1 = /PickList/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Tree', () => {
        element(by.css('[routerLink="tree"]')).click();
            const expect1 = /Tree/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Accordion', () => {
        element(by.css('[routerLink="accordion"]')).click();
            const expect1 = /Accordion/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Panel', () => {
        element(by.css('[routerLink="panel"]')).click();
            const expect1 = /Panel/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load TabView', () => {
        element(by.css('[routerLink="tabview"]')).click();
            const expect1 = /TabView/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Fieldset', () => {
        element(by.css('[routerLink="fieldset"]')).click();
            const expect1 = /Fieldset/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Grid', () => {
        element(by.css('[routerLink="grid"]')).click();
            const expect1 = /Grid/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Toolbar', () => {
        element(by.css('[routerLink="toolbar"]')).click();
            const expect1 = /Toolbar/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load sidebar', () => {
        element(by.css('[routerLink="sidebar"]')).click();
            const expect1 = /SideBar/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Dialog', () => {
        element(by.css('[routerLink="dialog"]')).click();
            const expect1 = /Dialog/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load ConfirmDialog', () => {
        element(by.css('[routerLink="confirmdialog"]')).click();
            const expect1 = /ConfirmDialog/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load LightBox', () => {
        element(by.css('[routerLink="lightbox"]')).click();
            const expect1 = /LightBox/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load OverlayPanel', () => {
        element(by.css('[routerLink="overlaypanel"]')).click();
            const expect1 = /OverlayPanel/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Tooltip', () => {
        element(by.css('[routerLink="tooltip"]')).click();
            const expect1 = /Tooltip/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load FileUpload', () => {
        element(by.css('[routerLink="fileupload"]')).click();
            const expect1 = /FileUpload/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Menu', () => {
        element(by.css('[routerLink="menu"]')).click();
            const expect1 = /Menu/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load ContextMenu', () => {
        element(by.css('[routerLink="contextmenu"]')).click();
            const expect1 = /ContextMenu/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load PanelMenu', () => {
        element(by.css('[routerLink="panelmenu"]')).click();
            const expect1 = /PanelMenu/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Steps', () => {
        element(by.css('[routerLink="steps"]')).click();
            const expect1 = /Steps/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load TieredMenu', () => {
        element(by.css('[routerLink="tieredmenu"]')).click();
            const expect1 = /TieredMenu/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Breadcrumb', () => {
        element(by.css('[routerLink="breadcrumb"]')).click();
            const expect1 = /Breadcrumb/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load MegaMenu', () => {
        element(by.css('[routerLink="megamenu"]')).click();
            const expect1 = /MegaMenu/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Menubar', () => {
        element(by.css('[routerLink="menubar"]')).click();
            const expect1 = /Menubar/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load SlideMenu', () => {
        element(by.css('[routerLink="slidemenu"]')).click();
            const expect1 = /SlideMenu/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load TabMenu', () => {
        element(by.css('[routerLink="tabmenu"]')).click();
            const expect1 = /TabMenu/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Messages', () => {
        element(by.css('[routerLink="messages"]')).click();
            const expect1 = /Messages/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Growl', () => {
        element(by.css('[routerLink="growl"]')).click();
            const expect1 = /Growl/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Galleria', () => {
        element(by.css('[routerLink="galleria"]')).click();
            const expect1 = /Galleria/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load DragDrop', () => {
        element(by.css('[routerLink="dragdrop"]')).click();
            const expect1 = /DragDrop/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Captcha', () => {
        element(by.css('[routerLink="captcha"]')).click();
            const expect1 = /Captcha/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Defer', () => {
        element(by.css('[routerLink="defer"]')).click();
            const expect1 = /Defer/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load RTL', () => {
        element(by.css('[routerLink="rtl"]')).click();
            const expect1 = /RTL/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load BlockUI', () => {
        element(by.css('[routerLink="blockui"]')).click();
            const expect1 = /BlockUI/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Terminal', () => {
        element(by.css('[routerLink="terminal"]')).click();
            const expect1 = /Terminal/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Inplace', () => {
        element(by.css('[routerLink="inplace"]')).click();
            const expect1 = /Inplace/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load ProgressBar', () => {
        element(by.css('[routerLink="progressbar"]')).click();
            const expect1 = /ProgressBar/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Validation', () => {
        element(by.css('[routerLink="validation"]')).click();
            const expect1 = /Validation/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Progress Spinner', () => {
        element(by.css('[routerLink="progressspinner"]')).click();
            const expect1 = /ProgressSpinner/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load Button', () => {
        element(by.css('[routerLink="button"]')).click();
            const expect1 = /Button/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    it('should load SplitButton', () => {
        element(by.css('[routerLink="splitbutton"]')).click();
            const expect1 = /SplitButton/;
        element.all(by.css('h2 span')).first().getText().then((value) => {
            expect(value).toMatch(expect1);
        });
    });

    afterAll(() => {
        accountMenu.click();
        logout.click();
    });
});
