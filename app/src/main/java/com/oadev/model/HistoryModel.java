package com.oadev.model;

public class HistoryModel {
    private String historyTittle;
    private String dateTime;
    private String amount;
    private String status;

    public HistoryModel(String historyTittle, String dateTime, String amount, String status) {
        this.historyTittle = historyTittle;
        this.dateTime = dateTime;
        this.amount = amount;
        this.status = status;
    }

    public String getHistoryTittle() {
        return historyTittle;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
