package com.service;

import java.util.List;
import java.util.Optional;

import com.model.dto.PriceQuotationsReportDTO;
import com.model.dto.mapper.PriceQuotationReportMapper;
import com.model.idGenerators.QuotationReportIdGenerator;
import com.service.dao.ItemDAO;
import com.service.dao.PriceQuotationReportDAO;
import com.util.PurchaseRequestStatus;

public class PriceQuotationReportService {

    private PriceQuotationReportDAO reportDAO;

    public PriceQuotationReportService() {
        this.reportDAO = new PriceQuotationReportDAO();
    }

    public Optional<List<PriceQuotationsReportDTO>> getAllPriceQuotationReports() {

        var resultSet = reportDAO.selectAll();

        if (resultSet.isEmpty())
            return Optional.empty();

        var pqrList = resultSet.stream()
                .map(PriceQuotationReportMapper::toDTO)
                .toList();

        return Optional.of(pqrList);

    }

    public boolean insertPriceQuotationReport(PriceQuotationsReportDTO dto) {

        var itemDAO = new ItemDAO();

        String reportId = QuotationReportIdGenerator.generate();

        String[] ItemIds = dto.itemsDTOs.stream()
                .map(i -> i.itemId.get())
                .toArray(size -> new String[size]);

        return reportDAO.insert(
                pqr -> pqr
                        .setId(reportId)
                        .setCreatedDate(dto.createdDate.get())
                        .setStatus(dto.status.get()))

                &&

                itemDAO.updateColumnWhere("price_quotation_report_id", reportId, ItemIds)
                &&
                itemDAO.updateColumnWhere("item_status",
                        PurchaseRequestStatus.PROCESSING.toString(),
                        ItemIds);

    }

}
