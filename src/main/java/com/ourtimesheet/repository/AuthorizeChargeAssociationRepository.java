package com.ourtimesheet.repository;

import com.ourtimesheet.association.AuthorizeChargeAssociation;

import java.util.List;
import java.util.TimeZone;

/**
 * Created by Abdus Salam on 11/2/2017.
 */
public interface AuthorizeChargeAssociationRepository extends Repository<AuthorizeChargeAssociation> {

    void saveAll(List<AuthorizeChargeAssociation> authorizeChargeAssociations, List<String> allChargeCodesTypes, TimeZone timeZone);

}