package com.muzkat.jdbc.project.dao;

import com.muzkat.jdbc.project.ConnectionPoolManager;
import com.muzkat.jdbc.project.dto.EmplExampleFilter;
import com.muzkat.jdbc.project.entity.EmplExample;
import com.muzkat.jdbc.project.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;


public class EmplExampleDao {
    private static final EmplExampleDao INSTANCE = new EmplExampleDao();
    private static final String DELETE_SQL = """
            DELETE FROM empl_example
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO empl_example(first_name,second_name, company_id, salary)
            VALUES (?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE empl_example
            SET first_name = ?,
                second_name = ?,
                company_id = ?,
                salary = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT  id,
                   first_name,
                   second_name,
                   company_id,
                   salary
            FROM empl_example
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT   id,
                    first_name,
                    second_name,
                    company_id,
                    salary
            FROM empl_example
            """;

    private EmplExampleDao() {
    }

    public List<EmplExample> findAll(EmplExampleFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.firstName() != null){
            parameters.add("first_name LIKE ?");
            parameters.add("%" + filter.firstName() + "%");
        }
        if (filter.secondName() != null){
            parameters.add("second_name LIKE = ?");
            parameters.add(filter.secondName());
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        var where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        // если в фильтре не будет ни одного параметра, то нужно добавить пустую строку вместо WHERE
        // в противном случае возникнет ошибка
        var sql = FIND_ALL_SQL + where;

        try (var connection = ConnectionPoolManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            final ResultSet resultSet = preparedStatement.executeQuery();
            List<EmplExample> emplExamples = new ArrayList<>();
            while(resultSet.next()){
                emplExamples.add(buildEmplExample(resultSet));
            }
            return emplExamples;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<EmplExample> findAll() {
        try (var connection = ConnectionPoolManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            List<EmplExample> employees = new ArrayList<>();

            while (resultSet.next()) {
                employees.add(buildEmplExample(resultSet));
            }
            return employees;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<EmplExample> findById(Long id) {
        try (Connection connection = ConnectionPoolManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            final ResultSet resultSet = preparedStatement.executeQuery();
            EmplExample emplExample = null;
            if (resultSet.next()) {
                emplExample = buildEmplExample(resultSet);
            }

            return Optional.ofNullable(emplExample);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static EmplExample buildEmplExample(ResultSet resultSet) throws SQLException {
        return new EmplExample(
                resultSet.getLong("id"),
                resultSet.getLong("company_id"),
                resultSet.getString("first_name"),
                resultSet.getString("second_name"),
                resultSet.getBigDecimal("salary")
        );
    }

    public void update(EmplExample emplExample) {
        try (var connection = ConnectionPoolManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, emplExample.getFirstName());
            preparedStatement.setString(2, emplExample.getSecondName());
            preparedStatement.setLong(3, emplExample.getCompanyId());
            preparedStatement.setBigDecimal(4, emplExample.getSalary());

            preparedStatement.setLong(5, emplExample.getId());

            final int i = preparedStatement.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public EmplExample save(EmplExample emplExample) {
        try (var connection = ConnectionPoolManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, emplExample.getFirstName());
            preparedStatement.setString(2, emplExample.getSecondName());
            preparedStatement.setLong(3, emplExample.getCompanyId());
            preparedStatement.setBigDecimal(4, emplExample.getSalary());
            preparedStatement.executeUpdate();

            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                emplExample.setCompanyId(generatedKeys.getLong("id"));
            }
            return emplExample;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionPoolManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public static EmplExampleDao getInstance() {
        return INSTANCE;
    }

}
