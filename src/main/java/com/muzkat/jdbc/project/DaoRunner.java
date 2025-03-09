package com.muzkat.jdbc.project;

import com.muzkat.jdbc.project.dao.EmplExampleDao;
import com.muzkat.jdbc.project.dto.EmplExampleFilter;
import com.muzkat.jdbc.project.entity.EmplExample;

import java.math.BigDecimal;
import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {
        var emplExampleFilter = new EmplExampleFilter(3, 0, null, null);
        var emplExamplAll = EmplExampleDao.getInstance().findAll(emplExampleFilter);
        System.out.println(emplExamplAll);

        findAllTest();
        updateTest();
        deleteTest();
        saveTest();

    }

    private static void findAllTest() {
        var emplExamples = EmplExampleDao.getInstance().findAll();
        System.out.println(emplExamples);
    }

    private static void updateTest() {
        var emplExampleDao = EmplExampleDao.getInstance();
        final Optional<EmplExample> mayBeEmployee = emplExampleDao.findById(1L);
        System.out.println(mayBeEmployee);

        mayBeEmployee.ifPresent(emplExample -> {
            emplExample.setSalary(BigDecimal.valueOf(33333));
            emplExampleDao.update(emplExample);
        });
    }

    private static void deleteTest() {
        var emplExampleDao1= EmplExampleDao.getInstance();
        var deleteResult = emplExampleDao1.delete(1L);
        System.out.println(deleteResult);
    }

    private static void saveTest() {
        final EmplExampleDao emplExampleDao = EmplExampleDao.getInstance();
        final EmplExample emplExample = new EmplExample();
        emplExample.setCompanyId(1L);
        emplExample.setFirstName("Dev");
        emplExample.setSecondName("Ooot");
        emplExample.setCompanyId(1L);
        emplExample.setSalary(BigDecimal.TEN);
        final EmplExample savedEmplExample = emplExampleDao.save(emplExample);
        System.out.println(savedEmplExample);
    }
}
