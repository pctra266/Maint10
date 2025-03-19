package Model;

import java.sql.Timestamp;

public class ContractorCard {

    private int contractorCardID;
    private int warrantyCardID;
    private int staffID;
    private int contractorID;
    private String status;
    private String note;
    private Timestamp date;
    private String staffName;
    private String warrantyCardCode;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getWarrantyCardCode() {
        return warrantyCardCode;
    }

    public void setWarrantyCardCode(String warrantyCardCode) {
        this.warrantyCardCode = warrantyCardCode;
    }

    public ContractorCard() {
    }

    public int getContractorCardID() {
        return contractorCardID;
    }

    public void setContractorCardID(int contractorCardID) {
        this.contractorCardID = contractorCardID;
    }

    public int getWarrantyCardID() {
        return warrantyCardID;
    }

    public void setWarrantyCardID(int warrantyCardID) {
        this.warrantyCardID = warrantyCardID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getContractorID() {
        return contractorID;
    }

    public void setContractorID(int contractorID) {
        this.contractorID = contractorID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ContractorCard{" + "contractorCardID=" + contractorCardID + ", warrantyCardID=" + warrantyCardID + ", staffID=" + staffID + ", contractorID=" + contractorID + ", status=" + status + ", note=" + note + '}';
    }

}
