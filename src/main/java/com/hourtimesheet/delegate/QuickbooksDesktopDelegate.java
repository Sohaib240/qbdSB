package com.hourtimesheet.delegate;

import com.hourtimesheet.expert.CompanyAuthenticationExpert;
import com.hourtimesheet.expert.UserAuthenticationExpert;
import com.hourtimesheet.expert.WebConnectorFileExpert;
import com.hourtimesheet.model.FileResponseModel;


/**
 * Created by Noor's on 4/26/2017.
 */
public class QuickbooksDesktopDelegate {

    private final WebConnectorFileExpert webConnectorFileExpert;
    private final UserAuthenticationExpert userAuthenticationExpert;
    private final CompanyAuthenticationExpert companyAuthenticationExpert;

    public QuickbooksDesktopDelegate(WebConnectorFileExpert webConnectorFileExpert, UserAuthenticationExpert userAuthenticationExpert, CompanyAuthenticationExpert companyAuthenticationExpert) {
        this.webConnectorFileExpert = webConnectorFileExpert;
        this.userAuthenticationExpert = userAuthenticationExpert;
        this.companyAuthenticationExpert = companyAuthenticationExpert;
    }

    public FileResponseModel getQwcFile(String companyName, String email, String password) {
        try {
            companyAuthenticationExpert.authenticate(companyName);
            userAuthenticationExpert.authenticateEncrypted(email, password, companyName);
            return new FileResponseModel(webConnectorFileExpert.getQBWCFileAsString(email, companyName), "", false);
        } catch (Exception ex) {
            return new FileResponseModel(null, "Authentication Failed", true);
        }
    }
}
