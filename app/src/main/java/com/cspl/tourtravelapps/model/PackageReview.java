package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a_man on 24-01-2018.
 */

public class PackageReview {
    @SerializedName("Feedback_Count")
    @Expose
    private Integer feedbackCount;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(Integer feedbackCount) {
        this.feedbackCount = feedbackCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
