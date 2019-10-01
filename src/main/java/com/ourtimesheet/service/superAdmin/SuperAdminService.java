package com.ourtimesheet.service.superAdmin;

import com.ourtimesheet.employee.SuperAdmin;

/**
 * Created by Abdus Salam on 10/4/2017.
 */
public interface SuperAdminService {

    SuperAdmin findByEmail(String emailAddress);

}