package org.example.repository;

import org.example.jdbc.JdbcUtils;
import org.example.repository.dao.UrlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UrlRepositoryImpl implements UrlRepository {



    @Override
    public Optional<UrlDao> findUrlByLongForm(String longForm) throws SQLException {
        String sql = "SELECT * FROM \"Urls\" WHERE \"LongForm\" =?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, longForm);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            UrlDao urlDao = new UrlDao(
                    resultSet.getString("LongForm"),
                    resultSet.getString("ShortForm")
            );
            return Optional.of(urlDao);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UrlDao> findUrlByShortForm(String shortForm) throws SQLException {
        String sql = "SELECT * FROM \"Urls\" WHERE \"ShortForm\" =?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, shortForm);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            UrlDao urlDao = new UrlDao(
                    resultSet.getString("LongForm"),
                    resultSet.getString("ShortForm")
            );
            return Optional.of(urlDao);
        }
        return Optional.empty();
    }

    @Override
    public void save(UrlDao urlDao) throws SQLException {
        String sql = "INSERT INTO \"Urls\" (\"ShortForm\", \"LongForm\") VALUES (?,?)";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, urlDao.shortForm());
        preparedStatement.setString(2, urlDao.longForm());
        preparedStatement.execute();
        preparedStatement.close();
    }
}