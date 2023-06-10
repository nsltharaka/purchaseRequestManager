package com.model.idGenerators;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class RequestIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {

        long count = session.createQuery("select count(*) from PurchaseRequest", Long.class).getSingleResult();

        return String.format("PR%03d", count + 1);
    }
}
