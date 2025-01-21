/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Tra Pham
 */
public class FeedbackLog {
    private int FeedbackLogID, FeedbackID;
    private String Action,OldFeedbackText, NewFeedbackText;
    private int ModifiedBy;
    private Date DateModified;

    public FeedbackLog() {
    }

    public FeedbackLog(int FeedbackLogID, int FeedbackID, String Action, String OldFeedbackText, String NewFeedbackText, int ModifiedBy, Date DateModified) {
        this.FeedbackLogID = FeedbackLogID;
        this.FeedbackID = FeedbackID;
        this.Action = Action;
        this.OldFeedbackText = OldFeedbackText;
        this.NewFeedbackText = NewFeedbackText;
        this.ModifiedBy = ModifiedBy;
        this.DateModified = DateModified;
    }

    public int getFeedbackLogID() {
        return FeedbackLogID;
    }

    public void setFeedbackLogID(int FeedbackLogID) {
        this.FeedbackLogID = FeedbackLogID;
    }

    public int getFeedbackID() {
        return FeedbackID;
    }

    public void setFeedbackID(int FeedbackID) {
        this.FeedbackID = FeedbackID;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }

    public String getOldFeedbackText() {
        return OldFeedbackText;
    }

    public void setOldFeedbackText(String OldFeedbackText) {
        this.OldFeedbackText = OldFeedbackText;
    }

    public String getNewFeedbackText() {
        return NewFeedbackText;
    }

    public void setNewFeedbackText(String NewFeedbackText) {
        this.NewFeedbackText = NewFeedbackText;
    }

    public int getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(int ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public Date getDateModified() {
        return DateModified;
    }

    public void setDateModified(Date DateModified) {
        this.DateModified = DateModified;
    }

    @Override
    public String toString() {
        return "FeedbackLog{" + "FeedbackLogID=" + FeedbackLogID + ", FeedbackID=" + FeedbackID + ", Action=" + Action + ", OldFeedbackText=" + OldFeedbackText + ", NewFeedbackText=" + NewFeedbackText + ", ModifiedBy=" + ModifiedBy + ", DateModified=" + DateModified + '}';
    }
    
}
