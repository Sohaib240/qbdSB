package com.hourtimesheet.expert;

import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.util.MasterConfigUtils;

/**
 * Created by Noor's on 4/26/2017.
 */
public class CompanyAuthenticationExpert {

    private final MasterConfigUtils masterConfigUtils;

    public CompanyAuthenticationExpert(MasterConfigUtils masterConfigUtils) {
        this.masterConfigUtils = masterConfigUtils;
    }

    public void authenticate(String domainName) throws CompanyNotFoundException {
        masterConfigUtils.getDBForDomain(domainName);
    }
}
