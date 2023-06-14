package com.model.idGenerators;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class QuotationReportIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        Long result = session.createQuery("select count(*) from PriceQuotation", Long.class).getSingleResult();
        return String.format("PQR%03d", result + 1);
    }

}
