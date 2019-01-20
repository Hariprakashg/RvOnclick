package com.example.RvOnclick;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONObject;

@Entity(indices = {@Index(value = {"invoiceNumber"},unique = true)})
public class Invoice {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long invoiceId;


    private String invoiceNumber;
    private String customer;
    private String company;
    private Double grandTotal;
    private Double outstanding;
    private Boolean invoiceStatus;
    private String invoiceDate;
    private Double paidAmount;
    private String invoiceItems;

    public String getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(String invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }



    @NonNull
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(@NonNull Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Double outstanding) {
        this.outstanding = outstanding;
    }

    public Boolean getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Boolean invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}
