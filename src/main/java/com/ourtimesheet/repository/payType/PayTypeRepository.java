package com.ourtimesheet.repository.payType;

import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.repository.Repository;

/**
 * Created by Abdus Salam on 2/15/2017.
 */
public interface PayTypeRepository extends Repository<PayType> {

    PayType findByQuickBooksId(String quickBooksId);
}
