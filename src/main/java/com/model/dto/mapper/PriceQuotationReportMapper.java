package com.model.dto.mapper;

import com.model.PriceQuotationsReport;
import com.model.dto.PriceQuotationsReportDTO;

public class PriceQuotationReportMapper {

        public static PriceQuotationsReport toPriceQuotationReport(PriceQuotationsReportDTO dto) {

                return new PriceQuotationsReport()
                                .setCreatedDate(dto.createdDate.get())
                                .setStatus(dto.status.get());
        }

        public static PriceQuotationsReportDTO toDTO(PriceQuotationsReport report) {

                return new PriceQuotationsReportDTO()
                                .setQuotationReportId(report.getId())
                                .setCreatedDate(report.getCreatedDate())
                                .setStatus(report.getStatus());

        }

}
