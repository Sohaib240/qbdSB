package com.ourtimesheet.service.superAdmin;

import com.ourtimesheet.employee.SuperAdmin;
import com.ourtimesheet.repository.superAdmin.SuperAdminRepository;

/**
 * Created by Abdus Salam on 10/4/2017.
 */
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository superAdminRepository;

    public SuperAdminServiceImpl(SuperAdminRepository superAdminRepository) {
        this.superAdminRepository = superAdminRepository;
    }


    @Override
    public SuperAdmin findByEmail(String emailAddress) {
        return superAdminRepository.findByEmail(emailAddress);
    }
}
