package com.model.dto.mapper;

import com.model.PriceQuotation;
import com.model.dto.PriceQuotationDTO;

public class PriceQuotationMapper {

    public static PriceQuotation toPriceQuotation(PriceQuotationDTO dto) {

        return new PriceQuotation()
                .setQuotationId(dto.priceQuotationId.get())
                .setApproved(dto.isApproved.get())
                .setSupplierName(dto.supplierName.get())
                .setSupplierAddress(dto.supplierAddress.get())
                .setPriceQuotationsReport(dto.priceQuotationReportId.get());

    }

}