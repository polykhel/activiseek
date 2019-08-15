package com.activiseek.api.domain.yelp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Hours {
    private List<OpeningHours> open;
    private String hoursType;
    private boolean isOpenNow;

    public List<OpeningHours> getOpen() {
        return open;
    }

    public void setOpen(List<OpeningHours> open) {
        this.open = open;
    }

    public String getHoursType() {
        return hoursType;
    }

    public void setHoursType(String hoursType) {
        this.hoursType = hoursType;
    }

    @JsonProperty("is_open_now")
    public boolean isOpenNow() {
        return isOpenNow;
    }

    public void setOpenNow(boolean openNow) {
        isOpenNow = openNow;
    }
}
