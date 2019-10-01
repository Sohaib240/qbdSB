package com.ourtimesheet.repository.superAdmin;

import com.ourtimesheet.employee.SuperAdmin;
import com.ourtimesheet.repository.Repository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Created by Abdus Salam on 10/4/2017.
 */
public interface SuperAdminRepository extends Repository<SuperAdmin> {

    SuperAdmin findByEmail(String emailAddress);

}