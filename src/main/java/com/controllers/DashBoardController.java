package com.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;

import com.model.Item;
import com.model.PriceQuotationsReport;
import com.model.PurchaseRequest;
import com.model.dto.PurchaseOrderDTO;
import com.service.dao.ItemDAO;
import com.service.dao.PriceQuotationReportDAO;
import com.service.dao.PurchaseOrderDAO;
import com.service.dao.PurchaseRequestDAO;
import com.util.PriceQuotationReportStatus;
import com.util.PurchaseRequestStatus;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class DashBoardController {

    private @FXML ResourceBundle resources;
    private @FXML URL location;

    @FXML
    private PieChart pieChartPOs;

    @FXML
    private PieChart pieChartPQs;

    @FXML
    private PieChart pieChartPRs;

    @FXML
    private Label lblAwaitingDeliveryCount;

    @FXML
    private Label lblDeliveredCount;

    @FXML
    private Label lblRequestedItemCount;

    @FXML
    void initialize() {
        populateLabels(new ItemDAO().selectAll());
        populateCharts();
    }

    private void populateLabels(List<Item> items) {
        lblRequestedItemCount.setText(String.valueOf(
                getCount(items, item -> true)));

        lblAwaitingDeliveryCount.setText(String.valueOf(
                getCount(items, item -> item.getItemStatus().equals(PurchaseRequestStatus.AWAITING))));

        lblDeliveredCount.setText(String.valueOf(
                getCount(items, item -> item.getItemStatus().equals(PurchaseRequestStatus.DELIVERED))));
    }

    private long getCount(List<Item> items, Predicate<Item> block) {
        return items.stream()
                .filter(block)
                .count();
    }

    private <T> void populateCharts() {

        populatePRChart();
        populatePQChart();
        populatePOChart();

    }

    private void populatePRChart() {
        final var prs = new PurchaseRequestDAO().getAllPurchaseRequests();

        Function<Predicate<PurchaseRequest>, Long> func = block -> {
            return prs.stream()
                    .filter(block)
                    .count();
        };

        var processingPRs = func.apply(pr -> pr.getRequestStatus().equals(PurchaseRequestStatus.PROCESSING));
        var awaitingPRs = func.apply(pr -> pr.getRequestStatus().equals(PurchaseRequestStatus.AWAITING));
        var deliveredPRs = func.apply(pr -> pr.getRequestStatus().equals(PurchaseRequestStatus.DELIVERED));

        pieChartPRs.getData().addAll(
                new PieChart.Data("Processing", processingPRs),
                new PieChart.Data("Awaiting", awaitingPRs),
                new PieChart.Data("Delivered", deliveredPRs));

        pieChartPRs.getData().forEach(data -> {
            Tooltip tooltip = new Tooltip();
            String percentage = String.format("%.2f%%", data.getPieValue());
            String info = percentage + "\n" + data.getName();
            tooltip.setText(info);
            Tooltip.install(data.getNode(), tooltip);
        });
    }

    private void populatePQChart() {
        final var pqs = new PriceQuotationReportDAO().selectAll();

        Function<Predicate<PriceQuotationsReport>, Long> func = block -> {
            return pqs.stream()
                    .filter(block)
                    .count();
        };

        var approvedPRs = func.apply(pq -> pq.getStatus().equals(PriceQuotationReportStatus.PENDING_APPROVAL));
        var pendingPRs = func.apply(pq -> pq.getStatus().equals(PriceQuotationReportStatus.APPROVED));

        pieChartPQs.getData().addAll(
                new PieChart.Data("Approved", approvedPRs),
                new PieChart.Data("Pending Approval", pendingPRs));

        pieChartPQs.getData().forEach(data -> {
            Tooltip tooltip = new Tooltip();
            String percentage = String.format("%.2f%%", data.getPieValue());
            String info = percentage + "\n" + data.getName();
            tooltip.setText(info);
            Tooltip.install(data.getNode(), tooltip);
        });
    }

    private void populatePOChart() {
        final var prs = new PurchaseOrderDAO().selectAll();

        Function<Predicate<PurchaseOrderDTO>, Long> func = block -> {
            return prs.stream()
                    .filter(block)
                    .count();
        };

        var awaitingPos = func.apply(po -> po.status.get().equals(PurchaseRequestStatus.AWAITING));
        var deliveredPos = func.apply(po -> po.status.get().equals(PurchaseRequestStatus.DELIVERED));

        pieChartPOs.getData().addAll(
                new PieChart.Data("Awaiting", awaitingPos),
                new PieChart.Data("Delivered", deliveredPos));

        pieChartPOs.getData().forEach(data -> {
            Tooltip tooltip = new Tooltip();
            String percentage = String.format("%.2f%%", data.getPieValue());
            String info = percentage + "\n" + data.getName();
            tooltip.setText(info);
            Tooltip.install(data.getNode(), tooltip);
        });
    }

}
