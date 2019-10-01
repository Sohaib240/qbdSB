package com.hourtimesheet.transformer;

import com.hourtimesheet.desktop.ClassRet;
import com.hourtimesheet.desktop.QBXML;
import com.ourtimesheet.qbd.domain.QuickBooksClass;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by click chain 2 on 9/16/2015.
 */
public class QbdClassTransformer extends BaseTransformer implements Transformer<String, List<QuickBooksClass>> {

    private static final Logger LOG = LoggerFactory.getLogger(QbdClassTransformer.class);
    @Override
    public List<QuickBooksClass> transform(String s) throws Exception {
        List<QuickBooksClass> quickBooksClasses = new ArrayList<>();
        LOG.warn("Entered into QBD class");
        QBXML qbxmlType = transformToQBXML(s);
        List<ClassRet> classRets = qbxmlType.getQBXMLMsgsRs().getClassQueryRs().getClassRet();
        quickBooksClasses.addAll(classRets.stream().map(this::createClass).collect(Collectors.toList()));
        LOG.warn("Transformation done");
        return quickBooksClasses;
    }

    private QuickBooksClass createClass(ClassRet quickBooksClass) {
        return new QuickBooksClass(UUID.randomUUID(), quickBooksClass.getName(), BooleanUtils.toBoolean(quickBooksClass.getIsActive()), quickBooksClass.getListID());
    }
}
