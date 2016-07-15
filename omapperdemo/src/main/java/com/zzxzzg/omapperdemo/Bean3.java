package com.zzxzzg.omapperdemo;

import org.omapper.annotations.Mappable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16/7/15.
 */
public class Bean3 {

    /** The company name. */
    private String companyName;

    /** The positions list. */
    private List<String> positionsList;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getPositionsList() {
        return positionsList;
    }

    public void setPositionsList(List<String> positionsList) {
        this.positionsList = positionsList;
    }
}
