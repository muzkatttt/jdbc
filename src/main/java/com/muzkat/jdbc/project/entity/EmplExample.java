package com.muzkat.jdbc.project.entity;

import java.math.BigDecimal;

public class EmplExample {
    private Long id;

    private Long companyId;

    private String firstName;

    private String secondName;

    private BigDecimal salary;

    public EmplExample(Long id, Long companyId, String firstName, String secondName, BigDecimal salary) {
        this.id = id;
        this.companyId = companyId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.salary = salary;
    }

    public EmplExample() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return """
EmplExample{
    id=$id,
    companyId=$companyId,
    firstName='$firstName',
    secondName='$secondName',
    salary=$salary
}""";
    }
}
