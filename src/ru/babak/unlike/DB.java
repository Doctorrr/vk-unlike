package ru.babak.unlike;

import java.sql.*;

public class DB {

    public Connection cn;
    public String url;

    public void connect( String fileName ) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String dburl = "jdbc:sqlite:" + fileName;

        try ( Connection conn = DriverManager.getConnection(dburl)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());

                String sqlCreate = "CREATE TABLE IF NOT EXISTS likes (\n"
                        + "	id integer PRIMARY KEY,\n"
                        + "	url text NOT NULL"
                        + ");";

                Statement stmt = conn.createStatement();
                stmt.execute(sqlCreate);

                conn.close();

//                cn = conn;

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    public boolean delete( String url ) throws SQLException, ClassNotFoundException {
        if (cn == null) {
            Class.forName("org.sqlite.JDBC");
            String dburl = "jdbc:sqlite:likes.db";
            cn = DriverManager.getConnection(dburl);
        }

        if (cn != null && !isDeleted(url)) {
            Statement stmt = cn.createStatement();
            String sql = "INSERT INTO likes (url) VALUES (\""+url+"\")";
            stmt.executeUpdate(sql);
            return true;

        }

        return false;

    }

    public boolean isDeleted( String url ) throws SQLException, ClassNotFoundException {
        if (cn == null) {
            Class.forName("org.sqlite.JDBC");
            String dburl  = "jdbc:sqlite:likes.db";
            cn = DriverManager.getConnection(dburl);
        }

        Statement stmt = cn.createStatement();
        String sql = "SELECT COUNT(id) AS c FROM likes WHERE url LIKE '"+url+"'";
        ResultSet rs = stmt.executeQuery(sql);

        int rows = rs.getInt( "c" );
        return ( rows > 0 );

    }

}


