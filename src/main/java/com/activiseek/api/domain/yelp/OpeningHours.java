package com.activiseek.api.domain.yelp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpeningHours {
    private boolean isOvernight;
    private String start;
    private String end;
    private int day;

    public OpeningHours(boolean isOvernight, String start, String end, int day) {
        this.isOvernight = isOvernight;
        this.start = start;
        this.end = end;
        this.day = day;
    }

    @JsonProperty("is_overnight")
    public boolean isOvernight() {
        return isOvernight;
    }

    public void setOvernight(boolean overnight) {
        isOvernight = overnight;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
