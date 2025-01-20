package com.moviebooking.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moviebooking.model.Times;
import com.moviebooking.util.DBConnectionUtil;

public class TimesDAO {
    public static List<Times> loadTimesByMovie(int movieId) {
        List<Times> times = new ArrayList<>();

        String query = "SELECT * FROM times WHERE movie_id = " + movieId;

        try (Connection conn = DBConnectionUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int timeId = rs.getInt("time_id");
                String time = rs.getString("time");

                times.add(new Times(timeId, time, movieId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return times;
    }

    public static Integer getTimeId(int movieId, String time) {
        String query = "SELECT time_id FROM times WHERE movie_id = " + movieId + " AND time = '" + time + "'";

        try (Connection conn = DBConnectionUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("time_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
