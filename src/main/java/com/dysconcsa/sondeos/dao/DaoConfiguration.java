package com.dysconcsa.sondeos.dao;

import com.dysconcsa.sondeos.model.ConfigurationProperty;
import com.dysconcsa.sondeos.util.AlertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DaoConfiguration {

    private DataConnection dataConnection;
    private Connection connection;
    private Exception _error = null;

    public Exception get_error() {
        return _error;
    }

    public DaoConfiguration() {
        dataConnection = new DataConnection();
        try {
            String sql = "create table if not exists configuration(id INTEGER not null primary key autoincrement,nombreEmpresa TEXT not null,imagen  TEXT not null)";
            dataConnection = new DataConnection();
            connection = dataConnection.getConnection();
            connection.prepareStatement(sql).execute();
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    public ConfigurationProperty findOne() {
        ConfigurationProperty configurationProperty = null;
        try {
            String sql = "select * from configuration limit 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                configurationProperty = new ConfigurationProperty(rs.getInt("id"), rs.getString("nombreEmpresa").toUpperCase(), rs.getString("imagen"));
            }
        } catch (Exception ex) {
            _error = ex;
        }
        return configurationProperty;
    }

    public void delete(ConfigurationProperty configurationProperty){
        try {
            String sql = "delete from configuration where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, configurationProperty.getId());
            preparedStatement.execute();
        } catch (Exception ex) {
            _error = ex;
        }
    }

    public ObservableList<ConfigurationProperty> findAll() {
        ObservableList<ConfigurationProperty> configurationProperties = FXCollections.observableArrayList();
        try {
            String sql = "select * from configuration";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                configurationProperties.add(new ConfigurationProperty(rs.getInt("id"), rs.getString("nombreEmpresa"), rs.getString("imagen")));
            }
        } catch (Exception ex) {
            _error = ex;
        }
        return configurationProperties;
    }

    public void save(ConfigurationProperty configurationProperty) {
        try {
            String sql = "insert into configuration(nombreEmpresa, imagen) values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, configurationProperty.getNombreEmpresa().toUpperCase());
            preparedStatement.setString(2, configurationProperty.getImagen());
            preparedStatement.execute();
        } catch (Exception ex) {
            _error = ex;
        }
    }

    public void update(ConfigurationProperty configurationProperty) {
        try {
            String sql = "update configuration set nombreEmpresa = ?, imagen = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, configurationProperty.getNombreEmpresa().toUpperCase());
            preparedStatement.setString(2, configurationProperty.getImagen());
            preparedStatement.execute();
        } catch (Exception ex) {
            _error = ex;
        }
    }
}
