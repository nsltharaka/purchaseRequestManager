package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
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
    void initialize() {

        pieChartPRs.getData().addAll(
                new PieChart.Data("Processing", 25),
                new PieChart.Data("Awaiting", 10),
                new PieChart.Data("Delivered", 22));

        pieChartPRs.getData().forEach(data -> {
            Tooltip tooltip = new Tooltip();
            String percentage = String.format("%.2f%%", data.getPieValue());
            String info = percentage + "\n" + data.getName();
            tooltip.setText(info);
            Tooltip.install(data.getNode(), tooltip);
        });

        pieChartPQs.getData().addAll(
                new PieChart.Data("Approved", 25),
                new PieChart.Data("Pending Approval", 75));

        pieChartPQs.getData().forEach(data -> {
            Tooltip tooltip = new Tooltip();
            String percentage = String.format("%.2f%%", data.getPieValue());
            String info = percentage + "\n" + data.getName();
            tooltip.setText(info);
            Tooltip.install(data.getNode(), tooltip);
        });

        pieChartPOs.getData().addAll(
                new PieChart.Data("Delivered", 25),
                new PieChart.Data("Awaiting Delivery", 10));

        pieChartPOs.getData().forEach(data -> {
            Tooltip tooltip = new Tooltip();
            String percentage = String.format("%.2f%%", data.getPieValue());
            String info = percentage + "\n" + data.getName();
            tooltip.setText(info);
            Tooltip.install(data.getNode(), tooltip);
        });

    }

}
