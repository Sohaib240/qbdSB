package com.ourtimesheet.qbd.expert;

import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.qbd.domain.paytype.*;
import com.ourtimesheet.repository.payType.PayTypeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Click Chain on 15/02/2017.
 */
public class QbdPayTypeExpert {

    private final PayTypeRepository payTypeRepository;

    public QbdPayTypeExpert(PayTypeRepository payTypeRepository) {
        this.payTypeRepository = payTypeRepository;
    }

    public void saveAll(Set<PayType> payTypes) {
        Set<PayType> updatedPayTypes = new HashSet<>();
        payTypes.forEach(payType -> {
            PayType payTypeFromDb = payTypeRepository.findByQuickBooksId(payType.getQuickBooksId());
            updatedPayTypes.add(createPayTypes(payTypeFromDb == null ? UUID.randomUUID() : payTypeFromDb.getId(), payType, payTypeFromDb));
        });
        payTypeRepository.saveAll(updatedPayTypes);
    }

    public List<PayType> findAll() {
        return payTypeRepository.findList();
    }

    public PayType findById(UUID id) {
        return payTypeRepository.findById(id).get();
    }

    private PayType createPayTypes(UUID id, PayType payType, PayType payTypeFromDb) {
        if (payType.getClass().isAssignableFrom(RegularPay.class) && (payTypeFromDb == null || !payTypeFromDb.getClass().isAssignableFrom(DoubleOvertimePay.class))) {
            return new RegularPay(id, payType.getName(), payType.isActive(), payType.getQuickBooksId());
        } else if (payType.getClass().isAssignableFrom(OvertimePay.class) && (payTypeFromDb == null || !payTypeFromDb.getClass().isAssignableFrom(DoubleOvertimePay.class))) {
            return new OvertimePay(id, payType.getName(), payType.isActive(), payType.getQuickBooksId());
        } else if (payType.getClass().isAssignableFrom(SickPay.class) && (payTypeFromDb == null || !payTypeFromDb.getClass().isAssignableFrom(DoubleOvertimePay.class))) {
            return new SickPay(id, payType.getName(), payType.isActive(), payType.getQuickBooksId());
        } else if (payType.getClass().isAssignableFrom(VacationPay.class) && (payTypeFromDb == null || !payTypeFromDb.getClass().isAssignableFrom(DoubleOvertimePay.class))) {
            return new VacationPay(id, payType.getName(), payType.isActive(), payType.getQuickBooksId());
        } else if (payTypeFromDb != null && payTypeFromDb.getClass().isAssignableFrom(DoubleOvertimePay.class)) {
            return new DoubleOvertimePay(id, payType.getName(), payType.isActive(), payType.getQuickBooksId());
        } else {
            return null;
        }
    }
}
