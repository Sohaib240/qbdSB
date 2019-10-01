package com.ourtimesheet.paidTimeOff;

import com.ourtimesheet.paidTimeOff.Rule.LeaveRule;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 3/26/2018.
 */
public class CompTimeLeavePolicy extends LeavePolicy {
    @PersistenceConstructor
    private CompTimeLeavePolicy(UUID id, String name, String description, List<LeaveRule> leaveRules) {
        super(id, name, description, leaveRules);
    }


}
