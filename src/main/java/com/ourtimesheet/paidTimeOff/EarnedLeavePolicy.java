package com.ourtimesheet.paidTimeOff;

import com.ourtimesheet.paidTimeOff.Rule.LeaveRule;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Created by Talha on 2/6/2018.
 */

public class EarnedLeavePolicy extends LeavePolicy {

    @PersistenceConstructor
    private EarnedLeavePolicy(UUID id, String name, String description, List<LeaveRule> leaveRules) {
        super(id, name, description, leaveRules);
    }
}
