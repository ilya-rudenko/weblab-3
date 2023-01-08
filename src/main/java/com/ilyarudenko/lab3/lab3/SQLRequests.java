package com.ilyarudenko.lab3.lab3;

public enum SQLRequests {

    addEntry("INSERT INTO s335157Entries" +
            "(id, x, y, r, hit_result, date, script_time) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?)"),

    generateId("SELECT nextval('ids')"),

    takeAll("SELECT * FROM s335157Entries"),

    restartSequence("ALTER SEQUENCE ids RESTART"),
    updateSequence("UPDATE s335157Entries SET id = DEFAULT"),

    clearTable("DELETE FROM s335157Entries");

    private final String statement;

    SQLRequests(String aStatement) {
        statement = aStatement;
    }
    public String getStatement() {
        return statement;
    }
}
