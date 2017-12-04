
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EventmanagerButtonDemoModule } from './buttons/button/buttondemo.module';
import { EventmanagerSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { EventmanagerDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { EventmanagerConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { EventmanagerLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { EventmanagerTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { EventmanagerOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { EventmanagerSideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { EventmanagerInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { EventmanagerInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { EventmanagerInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { EventmanagerCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { EventmanagerCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { EventmanagerChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { EventmanagerColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { EventmanagerInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { EventmanagerInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { EventmanagerPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { EventmanagerAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { EventmanagerSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { EventmanagerSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { EventmanagerRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { EventmanagerSelectDemoModule } from './inputs/select/selectdemo.module';
import { EventmanagerSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { EventmanagerListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { EventmanagerRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { EventmanagerToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';


import { EventmanagerGrowlDemoModule } from './messages/growl/growldemo.module';
import { EventmanagerMessagesDemoModule } from './messages/messages/messagesdemo.module';
import { EventmanagerGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { EventmanagerFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { EventmanagerAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { EventmanagerPanelDemoModule } from './panel/panel/paneldemo.module';
import { EventmanagerTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { EventmanagerFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { EventmanagerToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { EventmanagerGridDemoModule } from './panel/grid/griddemo.module';

import { EventmanagerDataTableDemoModule } from './data/datatable/datatabledemo.module';
import { EventmanagerDataGridDemoModule } from './data/datagrid/datagriddemo.module';
import { EventmanagerDataListDemoModule } from './data/datalist/datalistdemo.module';
import { EventmanagerDataScrollerDemoModule } from './data/datascroller/datascrollerdemo.module';
import { EventmanagerPickListDemoModule } from './data/picklist/picklistdemo.module';
import { EventmanagerOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { EventmanagerScheduleDemoModule } from './data/schedule/scheduledemo.module';
import { EventmanagerTreeDemoModule } from './data/tree/treedemo.module';
import { EventmanagerTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { EventmanagerPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { EventmanagerOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { EventmanagerCarouselDemoModule } from './data/carousel/carouseldemo.module';

import { EventmanagerBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { EventmanagerDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { EventmanagerLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { EventmanagerPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { EventmanagerPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { EventmanagerRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { EventmanagerDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';


import { EventmanagerMenuDemoModule } from './menu/menu/menudemo.module';
import { EventmanagerContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { EventmanagerPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { EventmanagerStepsDemoModule } from './menu/steps/stepsdemo.module';
import { EventmanagerTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { EventmanagerBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { EventmanagerMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { EventmanagerMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { EventmanagerSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { EventmanagerTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { EventmanagerBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { EventmanagerCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { EventmanagerDeferDemoModule } from './misc/defer/deferdemo.module';
import { EventmanagerInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { EventmanagerProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { EventmanagerRTLDemoModule } from './misc/rtl/rtldemo.module';
import { EventmanagerTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { EventmanagerValidationDemoModule } from './misc/validation/validationdemo.module';
import { EventmanagerProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [

        EventmanagerMenuDemoModule,
        EventmanagerContextMenuDemoModule,
        EventmanagerPanelMenuDemoModule,
        EventmanagerStepsDemoModule,
        EventmanagerTieredMenuDemoModule,
        EventmanagerBreadcrumbDemoModule,
        EventmanagerMegaMenuDemoModule,
        EventmanagerMenuBarDemoModule,
        EventmanagerSlideMenuDemoModule,
        EventmanagerTabMenuDemoModule,

        EventmanagerBlockUIDemoModule,
        EventmanagerCaptchaDemoModule,
        EventmanagerDeferDemoModule,
        EventmanagerInplaceDemoModule,
        EventmanagerProgressBarDemoModule,
        EventmanagerInputMaskDemoModule,
        EventmanagerRTLDemoModule,
        EventmanagerTerminalDemoModule,
        EventmanagerValidationDemoModule,

        EventmanagerButtonDemoModule,
        EventmanagerSplitbuttonDemoModule,

        EventmanagerInputTextDemoModule,
        EventmanagerInputTextAreaDemoModule,
        EventmanagerInputGroupDemoModule,
        EventmanagerCalendarDemoModule,
        EventmanagerChipsDemoModule,
        EventmanagerInputMaskDemoModule,
        EventmanagerInputSwitchDemoModule,
        EventmanagerPasswordIndicatorDemoModule,
        EventmanagerAutoCompleteDemoModule,
        EventmanagerSliderDemoModule,
        EventmanagerSpinnerDemoModule,
        EventmanagerRatingDemoModule,
        EventmanagerSelectDemoModule,
        EventmanagerSelectButtonDemoModule,
        EventmanagerListboxDemoModule,
        EventmanagerRadioButtonDemoModule,
        EventmanagerToggleButtonDemoModule,
        EventmanagerColorPickerDemoModule,
        EventmanagerCheckboxDemoModule,

        EventmanagerGrowlDemoModule,
        EventmanagerMessagesDemoModule,
        EventmanagerGalleriaDemoModule,

        EventmanagerFileUploadDemoModule,

        EventmanagerAccordionDemoModule,
        EventmanagerPanelDemoModule,
        EventmanagerTabViewDemoModule,
        EventmanagerFieldsetDemoModule,
        EventmanagerToolbarDemoModule,
        EventmanagerGridDemoModule,

        EventmanagerBarchartDemoModule,
        EventmanagerDoughnutchartDemoModule,
        EventmanagerLinechartDemoModule,
        EventmanagerPiechartDemoModule,
        EventmanagerPolarareachartDemoModule,
        EventmanagerRadarchartDemoModule,

        EventmanagerDragDropDemoModule,

        EventmanagerDialogDemoModule,
        EventmanagerConfirmDialogDemoModule,
        EventmanagerLightboxDemoModule,
        EventmanagerTooltipDemoModule,
        EventmanagerOverlayPanelDemoModule,
        EventmanagerSideBarDemoModule,

        EventmanagerDataTableDemoModule,
        EventmanagerDataGridDemoModule,
        EventmanagerDataListDemoModule,
        EventmanagerDataScrollerDemoModule,
        EventmanagerScheduleDemoModule,
        EventmanagerOrderListDemoModule,
        EventmanagerPickListDemoModule,
        EventmanagerTreeDemoModule,
        EventmanagerTreeTableDemoModule,
        EventmanagerPaginatorDemoModule,
        EventmanagerOrgChartDemoModule,
        EventmanagerCarouselDemoModule,
        EventmanagerProgressSpinnerDemoModule

    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventmanagerprimengModule {}
