package com.expbank.climatemonitor;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class ClimateInfo {

    private static final String NameField = "Station_Name";
    private static final String ProvinceField = "Province";
    private static final String DateField = "Date";
    private static final String MeanTempField = "Mean_Temp";
    private static final String HighestTempField = "Highest_Monthly_Maxi_Temp";
    private static final String LowestTempField = "Lowest_Monthly_Min_Temp";

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

    //@NotBlank(message = "Date is mandatory")
    @Column(name = "Date")
    @JsonProperty(DateField)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
