package com.yuchen.assignment3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @Column(name = "application_no")
    private String applicationNo;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "program_code")
    private String programCode;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "amount_paid")
    private double amountPaid;

    @Column(name = "status")
    private String status;

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String string) {
        this.studentId = string;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
