package com.dysconcsa.sondeos.dao;

import com.dysconcsa.sondeos.model.ConfigurationProperty;
import com.dysconcsa.sondeos.util.AlertError;

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

    public ConfigurationProperty findOne(){
        ConfigurationProperty configurationProperty = null;
        try{
            String sql ="select * from configuration";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                configurationProperty = new ConfigurationProperty(rs.getInt("id"),rs.getString("nombreEmpresa"),rs.getString("imagen"));
            }
        }catch (Exception ex){
            _error = ex;
        }
        return configurationProperty;
    }

    public boolean save(ConfigurationProperty configurationProperty) {
        try {
            String sql = "insert into configuration(nombreEmpresa, imagen) values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, configurationProperty.getNombreEmpresa());
            preparedStatement.setString(2, configurationProperty.getImagen());
            return preparedStatement.execute();
        } catch (Exception ex) {
            _error = ex;
            return false;
        }
    }

    public boolean update(ConfigurationProperty configurationProperty) {
        try {
            String sql = "update configuration set nombreEmpresa = ?, imagen = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, configurationProperty.getNombreEmpresa());
            preparedStatement.setString(2, configurationProperty.getImagen());
            return preparedStatement.execute();
        } catch (Exception ex) {
            _error = ex;
            return false;
        }
    }
}
