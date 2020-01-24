package io.keyko.monitoring.model;

import java.time.ZonedDateTime;

public class AccountCreatedAggregation extends BaseAggregation {


    private String type;

    public AccountCreatedAggregation(ZonedDateTime dateTime, Long count, String type){
        super(dateTime, count);
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
