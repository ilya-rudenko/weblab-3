package com.ilyarudenko.lab3.lab3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private boolean devMode = false;
    private String host;
    private String username;
    private String password;

    private Connection connection;

    public DBManager () {

        if(devMode){
            host = "localhost";
            username = "ilyarudenko";
            password = "Kra_st1254";
        }
        else {
            host = "pg";
            username = "s335157";
            password = "OjmHfeW8SBhoq9pB";
        }

        connect();
        createTable();
    }

    void connect () {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://"+ host + ":5432/studs",username, password);
            System.out.println("Connected to DB!");
            this.connection=connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DB connection went wrong...");
        }
    }

    void createTable (){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS s335157Entries("
                + "id INT PRIMARY KEY, "
                + "x float NOT NULL,"
                + "y float NOT NULL, "
                + "r INT NOT NULL, "
                + "hit_result boolean NOT NULL, "
                + "date varchar(50), "
                + "script_time bigint"
                + ")";
        try {
            Statement statement = connection.createStatement();
//        statement.executeUpdate("DROP TABLE s335157Entries");


            statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");
            statement.executeUpdate(createTableSQL);
            System.out.println("Table \"s335157Entries\" is created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer addEntry(Entry entry){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLRequests.addEntry.getStatement());
            Integer newId = setEntryToStatement(preparedStatement, entry);
            System.out.println(newId);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return (newId == null) ? 0 : newId;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SQL problem with adding new element!");
            return 0;
        }
    }



    private Integer setEntryToStatement(PreparedStatement statement, Entry entry) throws SQLException {
        Integer newId = generateId();
        if (newId == null) return null;

        entry.setId(newId);
        statement.setInt(1, newId);
        statement.setFloat(2, entry.getxValue());
        statement.setFloat(3, entry.getyValue());
        statement.setInt(4, entry.getrValue());
        statement.setBoolean(5,entry.getHitResult());
        statement.setString(6,entry.getDateTime());
        statement.setLong(7,entry.getScriptTime());

        return newId;
    }

    private Integer generateId() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLRequests.generateId.getStatement());
            if (resultSet.next()) return resultSet.getInt("nextval");
            return null;
        } catch (SQLException e) {
            System.out.println("SQL problem with generating id!");
            return null;
        }
    }

    public ResultSet getResults() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLRequests.takeAll.getStatement());
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL problem with taking all collection!");
            return null;
        }
    }

    public boolean clear() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequests.clearTable.getStatement())) {
            preparedStatement.executeUpdate();

            PreparedStatement clearSequence = connection.prepareStatement(SQLRequests.restartSequence.getStatement());
            clearSequence.executeUpdate();

            clearSequence = connection.prepareStatement(SQLRequests.updateSequence.getStatement());
            clearSequence.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("SQL problem with removing elements!");
            return false;
        }
    }

    public ResultSet getResultPage(int start){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM s335157Entries where id > "+start+" LIMIT 20;");
            System.out.println(preparedStatement);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL problem with taking all collection!");
            return null;
        }
    }

    public List<Entry> getPage(int pageNumber) {
        int start=20*(pageNumber-1);

        try{
            ResultSet data = getResultPage(start);
            List<Entry> entries = new ArrayList<>();

            while(data.next()){
                int id = data.getInt(1);
                Float xValue = data.getFloat(2);
                Float yValue = data.getFloat(3);
                Integer rValue = data.getInt(4);
                boolean hit_result = data.getBoolean(5);
                String date = data.getString(6);
                Long script_time = data.getLong(7);

                Entry entry = new Entry();

                entry.setId(id);
                entry.setxValue(xValue);
                entry.setyValue(yValue);
                entry.setrValue(rValue);
                entry.setHitResult(hit_result);
                entry.setDateTime(date);
                entry.setScriptTime(script_time);


                entries.add(entry);
            }
            return entries;
        }catch (SQLException | NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Entry> getEntries() {
        try{
            ResultSet data = getResults();
            List<Entry> entries = new ArrayList<>();

            while(data.next()){
                int id = data.getInt(1);
                Float xValue = data.getFloat(2);
                Float yValue = data.getFloat(3);
                Integer rValue = data.getInt(4);
                boolean hit_result = data.getBoolean(5);
                String date = data.getString(6);
                Long script_time = data.getLong(7);

                Entry entry = new Entry();

                entry.setId(id);
                entry.setxValue(xValue);
                entry.setyValue(yValue);
                entry.setrValue(rValue);
                entry.setHitResult(hit_result);
                entry.setDateTime(date);
                entry.setScriptTime(script_time);


                entries.add(entry);
            }
            return entries;
        }catch (SQLException | NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

}
