package com.ourtimesheet.util;

import com.ourtimesheet.data.QBDConnectionSessionDO;
import com.ourtimesheet.exception.CompanyNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Created by hassan on 3/25/16.
 */

public interface MasterConfigUtils {


    String getDBForDomain(String subdomain) throws CompanyNotFoundException;

    List<String> getAllCompanies();

    void saveQBDConnectionSessionDOForCompany(QBDConnectionSessionDO qbdConnectionSessionDO);

    void removeQBDConnectionSessionDOForCompany(String sessionId);

    QBDConnectionSessionDO getQBDConnectionSessionDOByCompanyName(String companyName);

    QBDConnectionSessionDO getQBDConnectionSessionDOById(UUID sessionId);

}
