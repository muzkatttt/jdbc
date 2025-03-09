package com.muzkat.jdbc.project.entity;

import java.math.BigDecimal;

public class EmplExampleWithCompany {
    private Long id;

    private Company company;

    private String firstName;

    private String secondName;

    private BigDecimal salary;

    public EmplExampleWithCompany(Long id, Company company, String firstName, String secondName, BigDecimal salary) {
        this.id = id;
        this.company = company;
        this.firstName = firstName;
        this.secondName = secondName;
        this.salary = salary;
    }

    public EmplExampleWithCompany() {
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
