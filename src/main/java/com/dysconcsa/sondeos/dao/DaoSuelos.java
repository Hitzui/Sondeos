package com.dysconcsa.sondeos.dao;

import com.dysconcsa.sondeos.model.SuelosProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoSuelos {

    Exception _error;

    public Exception get_error() {
        return _error;
    }

    DataConnection connection;

    List<SuelosProperty> listSuelos;

    public DaoSuelos() {
        connection = new DataConnection();
        try {
            Connection conn = connection.getConnection();
            String sql = "CREATE TABLE IF NOT EXISTS `suelos` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `nombre` TEXT NOT NULL, `simbolo` TEXT NOT NULL, `imagen` TEXT NOT NULL )";
            Statement statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            _error = ex;
        }
    }

    public List<SuelosProperty> findAll() throws SQLException {
        listSuelos = new ArrayList<>();
        try {
            Connection conn = connection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM suelos");
            while (rs.next()) {
                SuelosProperty suelosProperty = new SuelosProperty(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("simbolo"), rs.getString("imagen"));
                listSuelos.add(suelosProperty);
            }
            return listSuelos;
        } catch (SQLException ex) {
            _error = ex;
            return new ArrayList<>();
        } finally {
            connection.getConnection().close();
        }
    }

    public SuelosProperty findById(int id) {
        SuelosProperty suelosProperty = null;
        try {
            String sql = "select * from suelos where id = ?";
            Connection cnn = connection.getConnection();
            PreparedStatement preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                suelosProperty = new SuelosProperty(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("simbolo"), resultSet.getString("imagen"));
            }
            return suelosProperty;
        } catch (SQLException ex) {
            _error = ex;
            return suelosProperty;
        }
    }

    public SuelosProperty findBYSimbolo(String simbolo) {
        SuelosProperty suelosProperty = null;
        try {
            String sql = "select * from suelos where simbolo= ?";
            Connection cnn = connection.getConnection();
            PreparedStatement preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setString(1, simbolo);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                suelosProperty = new SuelosProperty(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("simbolo"), resultSet.getString("imagen"));
            }
        } catch (Exception ex) {
            _error = ex;
        }
        return suelosProperty;
    }

    public void save(SuelosProperty suelosProperty) {
        try {
            String sql = "INSERT INTO suelos(nombre,simbolo,imagen) VALUES (?,?,?)";
            Connection cnn = connection.getConnection();
            PreparedStatement preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setString(1, suelosProperty.nombreProperty().get());
            preparedStatement.setString(2, suelosProperty.simboloProperty().get());
            preparedStatement.setString(3, suelosProperty.imagenProperty().get());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            _error = ex;
        }
    }

    public void update(SuelosProperty suelosProperty) {
        try {
            String sql = "UPDATE suelos SET nombre = ?, simbolo = ?, imagen = ? WHERE id=?";
            Connection cnn = connection.getConnection();
            PreparedStatement preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setString(1, suelosProperty.getNombre());
            preparedStatement.setString(2, suelosProperty.getSimbolo());
            preparedStatement.setString(3, suelosProperty.getImagen());
            preparedStatement.setInt(4, suelosProperty.getID());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            _error = ex;
        }
    }

    public void delete(int id) {
        try {
            String sql = "DELETE FROM suelos WHERE id = ?";
            Connection cnn = connection.getConnection();
            PreparedStatement preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            _error = ex;
        }
    }
}
