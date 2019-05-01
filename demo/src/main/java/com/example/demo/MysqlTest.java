package com.example.demo;

import com.example.demo.pojo.Student;


import java.sql.*;
import java.util.Objects;

public class MysqlTest {
    private  static Connection conn;

    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false";
        String username = "root";
        String password = "034816";
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static void close() throws SQLException {
        conn.close();
    }

    private static int insert(Student student) throws SQLException {

        String sql = "insert into student values (?,?,?,?)";
        int index = 0;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConn().prepareStatement(sql);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2,student.getEnglish());
            preparedStatement.setInt(3,student.getMath());
            preparedStatement.setInt(4,student.getComputer());
            index = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            close();
        }
        return index;
    }

    private static int getEnglish(String name) throws SQLException {
        String sql = "select english from student where name = ?";
        PreparedStatement ps = null;
        try {
            ps = getConn().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("English");
            }
            return -1;
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ps.close();
            close();
        }
        return -1;
    }

    public static void main(String[] args) throws SQLException {
//        Student student = new Student("scofield",45,89,100);
//        System.out.println(insert(student));
        System.out.println(getEnglish("scofield"));
    }
}
