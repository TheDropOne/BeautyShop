package lab.work;

import java.sql.*;
import java.util.*;

public abstract class DBHelper<T> {

    public abstract String getTableName();

    public abstract T loadObject(ResultSet rs) throws SQLException;

    public ArrayList<T> loadAllData() {

        ArrayList<T> data = new ArrayList<T>();
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BeautyShop.s3db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + getTableName() + ";");
            while (rs.next()) {
                data.add(loadObject(rs));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully");

        return data;
    }


    public void addDataRecord(String[] fields, String[] data) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BeautyShop.s3db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            StringBuilder fieldList = new StringBuilder();
            StringBuilder dataList = new StringBuilder();
            for (int i = 0; i < fields.length; i++) {
                if (i == 0) {
                    fieldList.append(fields[0]);
                    dataList.append("'").append(data[0]).append("'");
                } else {
                    fieldList.append(",").append(fields[i]);
                    dataList.append(",").append("'").append(data[i]).append("'");
                }
            }
            String sql = "INSERT INTO " + getTableName() + " (" + fieldList.toString() + ") VALUES (" + dataList.toString() + ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }

    public void deleteRecordByID(int id) {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BeautyShop.s3db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "DELETE from " + getTableName() + " where ID=" + id + ";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

}
