package com.ourtimesheet.repositorydo;

import com.ourtimesheet.data.MasterConfigurationDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by hassan on 3/14/16.
 */
public interface MasterConfigurationDORepository extends MongoRepository<MasterConfigurationDO, String> {

}
