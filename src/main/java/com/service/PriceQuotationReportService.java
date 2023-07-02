package com.service;

import java.util.List;

import com.model.PriceQuotation;
import com.model.dto.PriceQuotationsReportDTO;
import com.model.dto.mapper.PriceQuotationMapper;
import com.model.dto.mapper.PriceQuotationReportMapper;
import com.model.idGenerators.PriceQuotationIDGenerator;
import com.model.idGenerators.QuotationReportIdGenerator;
import com.service.dao.ItemDAO;
import com.service.dao.PriceQuotationDAO;
import com.service.dao.PriceQuotationReportDAO;
import com.util.PurchaseRequestStatus;

public class PriceQuotationReportService {

        private PriceQuotationReportDAO reportDAO;

        public PriceQuotationReportService() {
                this.reportDAO = new PriceQuotationReportDAO();
        }

        public List<PriceQuotationsReportDTO> getAllPriceQuotationReports() {

                var resultSet = reportDAO.selectAll();

                if (resultSet.isEmpty())
                        return List.of();

                var pqrList = resultSet.stream()
                                .map(PriceQuotationReportMapper::toDTO)
                                .toList();

                return pqrList;

        }

        public boolean insertPriceQuotationReport(PriceQuotationsReportDTO dto) {

                var itemDAO = new ItemDAO();

                String reportId = QuotationReportIdGenerator.generate();

                String[] ItemIds = dto.itemsDTOs.stream()
                                .map(i -> i.itemId.get())
                                .toArray(size -> new String[size]);

                List<PriceQuotation> pqList = dto.priceQuotationDTOs.stream()
                                .map(PriceQuotationMapper::toPriceQuotation)
                                .peek(pq -> pq.setQuotationId(PriceQuotationIDGenerator.generate()))
                                .peek(pq -> pq.setPriceQuotationsReport(reportId))
                                .toList();

                return reportDAO.insert(
                                pqr -> pqr
                                                .setId(reportId)
                                                .setCreatedDate(dto.createdDate.get())
                                                .setStatus(dto.status.get()))

                                && itemDAO.updateColumnWhere("price_quotation_report_id", reportId, ItemIds)
                                && itemDAO.updateColumnWhere("item_status",
                                                PurchaseRequestStatus.PROCESSING.toString(),
                                                ItemIds)

                                && new PriceQuotationDAO().insertAll(pqList);

        }

        public boolean setApproved(PriceQuotationsReportDTO dto) {

                // TODO
                /**
                 * VALIDATION
                 * 
                 * if the pq is already approved, unsupported operation
                 */

                var approvedPQ = dto.priceQuotationDTOs.stream()
                                .filter(pq -> pq.isApproved.get())
                                .findAny();

                if (approvedPQ.isPresent()) {

                        reportDAO.setApproved(approvedPQ.get().priceQuotationId.get());
                        return true;
                }

                return false;

        }
}
