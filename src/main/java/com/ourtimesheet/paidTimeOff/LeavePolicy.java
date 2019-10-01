package com.ourtimesheet.paidTimeOff;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paidTimeOff.Rule.LeaveRule;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Talha on 9/26/2017.
 */
@Document(collection = "leavePolicy")
public abstract class LeavePolicy extends Entity {

    private final String name;
    private final String description;
    protected List<LeaveRule> leaveRules;

    protected LeavePolicy(UUID id, String name, String description, List<LeaveRule> leaveRules) {
        super(id);
        this.name = name;
        this.description = description;
        this.leaveRules = leaveRules;
    }
}