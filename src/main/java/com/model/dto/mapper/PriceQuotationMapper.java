package com.model.dto.mapper;

import com.model.PriceQuotation;
import com.model.dto.PriceQuotationDTO;

public class PriceQuotationMapper {

    public static PriceQuotation toPriceQuotation(PriceQuotationDTO dto) {

        return new PriceQuotation()
                .setApproved(dto.isApproved.get())
                .setSupplierName(dto.supplierName.get())
                .setSupplierAddress(dto.supplierAddress.get())
                .setQuotedTotal(dto.quotedTotal.get())
                .setItem_quotedPrice(dto.item_quotedPrice);

    }

    public static PriceQuotationDTO toDTO(PriceQuotation priceQuotation) {

        return new PriceQuotationDTO()
                .setPriceQuotationId(priceQuotation.getQuotationId().toString())
                .setPriceQuotationReportId(priceQuotation.getPriceQuotationsReportId())
                .setIsApproved(priceQuotation.isApproved())
                .setSupplierName(priceQuotation.getSupplierName())
                .setSupplierAddress(priceQuotation.getSupplierAddress())
                .setQuotedTotal(priceQuotation.getQuotedTotal())
                .setItem_quotedPrice(priceQuotation.getItem_quotedPrice());

    }

}
