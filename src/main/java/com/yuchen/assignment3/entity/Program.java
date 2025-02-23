package com.yuchen.assignment3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "programs")
public class Program {

    @Id
    @Column(name = "program_code")
    private String programCode;

    @Column(name = "program_name")
    private String programName;

    @Column(name = "duration")
    private String duration;

    @Column(name = "fee")
    private double fee;
    
    @Column(name = "start_dates")
    private String startDates;

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
    
    public String getStartDates() { 
    	return startDates; 
    	}
    
    public void setStartDates(String startDates) {
    	this.startDates = startDates; 
    	}

}
