package com.service;

import java.util.List;
import java.util.Optional;

import com.model.PriceQuotation;
import com.model.dto.PriceQuotationDTO;
import com.model.dto.mapper.PriceQuotationMapper;
import com.model.idGenerators.PriceQuotationIDGenerator;
import com.service.dao.PriceQuotationDAO;

import javafx.beans.property.SimpleListProperty;

public class PriceQuotationService {

    private PriceQuotationDAO dao = new PriceQuotationDAO();

    public boolean insertPriceQuotations(SimpleListProperty<PriceQuotationDTO> dtos) {

        List<PriceQuotation> pqList = dtos.stream()
                .map(PriceQuotationMapper::toPriceQuotation)
                .peek(pq -> pq.setQuotationId(PriceQuotationIDGenerator.generate()))
                .toList();

        return dao.insertAll(pqList);

    }

    public List<PriceQuotationDTO> getPriceQuotations(String pqrId) {

        List<PriceQuotation> pqList = dao.selectPriceQuotations(pqrId);

        var list = pqList.stream()
                .map(PriceQuotationMapper::toDTO)
                .toList();

        return list;

    }

    public Optional<PriceQuotationDTO> getApprovedPriceQuotation(String pqrId) {

        var pqList = getPriceQuotations(pqrId);

        var approvedPq = pqList.stream()
                .filter(pq -> pq.isApproved.get())
                .findAny();

        if (approvedPq.isPresent()) {
            return Optional.of(approvedPq.get());
        } else {
            return Optional.empty();
        }

    }

}