package com.ourtimesheet.qbd.expert;

import com.ourtimesheet.qbd.domain.QuickBooksClass;
import com.ourtimesheet.qbd.repository.DesktopClassRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Click Chain on 15/02/2017.
 */
public class QbdClassExpert {

    private final DesktopClassRepository classRepository;

    public QbdClassExpert(DesktopClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public void saveAll(Set<QuickBooksClass> classes) {
        Set<QuickBooksClass> updatedQuickBooksClasses = new HashSet<>();
        classes.forEach(importedClass -> {
            QuickBooksClass quickBooksClassFromDb = classRepository.findByQuickBooksId(importedClass.getQuickBooksId());
            updatedQuickBooksClasses.add(new QuickBooksClass(quickBooksClassFromDb != null ? quickBooksClassFromDb.getId() : importedClass.getId(), importedClass.getName(), importedClass.isActive(), importedClass.getQuickBooksId()));
        });
        classRepository.saveAll(updatedQuickBooksClasses);
    }

    public Iterable<QuickBooksClass> findAll() {
        return classRepository.findAll();
    }

    public QuickBooksClass findById(UUID id) {
        return classRepository.findById(id).get();
    }
}