package com.expbank.climatemonitor;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class ClimateInfo {

    public static final String NameField = "Station_Name";
    public static final String ProvinceField = "Province";
    public static final String DateField = "Date";
    public static final String MeanTempField = "Mean_Temp";
    public static final String HighestTempField = "Highest_Monthly_Maxi_Temp";
    public static final String LowestTempField = "Lowest_Monthly_Min_Temp";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "Station_Name")
    @JsonProperty(NameField)
    private String stationName;

    @NotBlank(message = "Province is mandatory")
    @Column(name = "Province")
    @JsonProperty(ProvinceField)
    private String province;

    @NotBlank(message = "Date is mandatory")
    @Column(name = "Date")
    @JsonProperty(DateField)
    private String date;

    @Column(name = "Mean_Temp")
    @JsonProperty(MeanTempField)
    private String meanTemp;

    @Column(name = "Highest_Monthly_Maxi_Temp")
    @JsonProperty(HighestTempField)
    private String highestMonthlyTemp;

    @Column(name = "Lowest_Monthly_Min_Temp")
    @JsonProperty(LowestTempField)
    private String lowestMonthlyTemp;

    public ClimateInfo(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeanTemp() {
        return meanTemp;
    }

    public void setMeanTemp(String meanTemp) {
        this.meanTemp = meanTemp;
    }

    public String getHighestMonthlyTemp() {
        return highestMonthlyTemp;
    }

    public void setHighestMonthlyTemp(String highestMonthlyTemp) {
        this.highestMonthlyTemp = highestMonthlyTemp;
    }

    public String getLowestMonthlyTemp() {
        return lowestMonthlyTemp;
    }

    public void setLowestMonthlyTemp(String lowestMonthlyTemp) {
        this.lowestMonthlyTemp = lowestMonthlyTemp;
    }
}
