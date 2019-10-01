package com.hourtimesheet.factory;

import com.hourtimesheet.transformer.*;
import com.ourtimesheet.qbd.query.*;
import com.ourtimesheet.qbd.query.hourExport.LeaveHourSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.OverTimeHoursSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.RegularHoursSyncRequest;
import com.ourtimesheet.qbd.service.QuickBooksDesktopService;

/**
 * Created by Abdus Salam on 2/14/2017.
 */
public class TransformerFactory {

    private final QuickBooksDesktopService quickBooksDesktopService;

    public TransformerFactory(QuickBooksDesktopService quickBooksDesktopService) {
        this.quickBooksDesktopService = quickBooksDesktopService;
    }

    public Transformer create() {
        QBDRequest qbdRequest = quickBooksDesktopService.findPreferenceQuery();
        Transformer transformer = null;
        if (qbdRequest.getClass().isAssignableFrom(EmployeeSyncRequest.class)) {
            transformer = new EmployeeTransformer();
        } else if (qbdRequest.getClass().isAssignableFrom(ClassSyncRequest.class)) {
            transformer = new QbdClassTransformer();
        } else if (qbdRequest.getClass().isAssignableFrom(ServiceItemSyncRequest.class)) {
            transformer = new ServiceItemTransformer();
        } else if (qbdRequest.getClass().isAssignableFrom(VendorSyncRequest.class)) {
            transformer = new VendorTransformer();
        } else if (qbdRequest.getClass().isAssignableFrom(CustomerJobSyncRequest.class)) {
            transformer = new CustomerTransformer();
        } else if (qbdRequest.getClass().isAssignableFrom(PayrollItemSyncRequest.class)) {
            transformer = new PayRollTransformer();
        } else if (qbdRequest.getClass().isAssignableFrom(OverTimeHoursSyncRequest.class) || qbdRequest.getClass().isAssignableFrom(RegularHoursSyncRequest.class) || qbdRequest.getClass().isAssignableFrom(LeaveHourSyncRequest.class)) {
            transformer = new HoursExportResponseTransformer();
        }
        return transformer;
    }


}
