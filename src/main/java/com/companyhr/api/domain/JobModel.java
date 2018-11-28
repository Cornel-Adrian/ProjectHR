package com.companyhr.api.domain;

public class JobModel {

    private int jobId;
    private String name;

    public JobModel(){

    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
