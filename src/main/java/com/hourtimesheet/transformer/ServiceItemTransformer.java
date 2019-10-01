package com.hourtimesheet.transformer;

import com.hourtimesheet.desktop.ItemServiceRet;
import com.hourtimesheet.desktop.QBXML;
import com.ourtimesheet.qbd.domain.ServiceItem;
import org.apache.commons.lang.BooleanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Abdus Salam on 2/14/2017.
 */
public class ServiceItemTransformer extends BaseTransformer implements Transformer<String, List<ServiceItem>> {

    @Override
    public List<ServiceItem> transform(String s) throws Exception {
        List<ServiceItem> serviceItems = new ArrayList<>();
        QBXML qbxmlType = transformToQBXML(s);
        List<ItemServiceRet> serviceRets = qbxmlType.getQBXMLMsgsRs().getItemServiceQueryRs().getItemServiceRet();
        serviceItems.addAll(serviceRets.stream().map(this::createServiceItem).collect(Collectors.toList()));
        return serviceItems;
    }

    private ServiceItem createServiceItem(ItemServiceRet serviceRet) {
        return new ServiceItem(UUID.randomUUID(), serviceRet.getFullName().replace(":", " > "), BooleanUtils.toBoolean(serviceRet.getIsActive()), serviceRet.getListID(), serviceRet.getName());
    }
}
