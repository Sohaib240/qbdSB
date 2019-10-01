package com.ourtimesheet.repository.payType;

import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.repository.GenericRepository;
import com.ourtimesheet.repositorydo.PayTypeDORepository;

/**
 * Created by Abdus Salam on 2/15/2017.
 */
public class PayTypeRepositoryImpl extends GenericRepository<PayType> implements PayTypeRepository {

    private final PayTypeDORepository payTypeDORepository;

    public PayTypeRepositoryImpl(PayTypeDORepository payTypeDORepository) {
        super(payTypeDORepository);
        this.payTypeDORepository = payTypeDORepository;
    }

    @Override
    public PayType findByQuickBooksId(String quickBooksId) {
        return payTypeDORepository.findByQuickBooksId(quickBooksId);
    }
}
