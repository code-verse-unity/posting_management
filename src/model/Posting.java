/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author isma
 */
public class Posting {
    public static String TABLE_NAME = "posting";

    private Integer id;
    private Employee employee;
    private Place place;
    private Place oldPlace;
    private Date postingDate;
    private Date serviceDate;

    public Posting(Integer id, Employee employee, Place place, Place oldPlace, Date postingDate, Date serviceDate) {
        this.id = id;
        this.employee = employee;
        this.place = place;
        this.oldPlace = oldPlace;
        this.postingDate = postingDate;
        this.serviceDate = serviceDate;
    }

    public Integer getId() {
        return this.id;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public Place getPlace() {
        return this.place;
    }

    public Place getOldPlace() {
        return this.oldPlace;
    }

    public Date getPostingDate() {
        return this.postingDate;
    }

    public Date getServiceDate() {
        return this.serviceDate;
    }

    public static Posting create(Connection connection, Integer employeeId, Integer oldPlaceId, Integer placeId,
            Date serviceDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + Posting.TABLE_NAME
                    + " (place_id, employee_id, old_place_id, posting_date, service_date) VALUES (?, ?, ?, ?, ?)");
            statement.setObject(1, placeId);
            statement.setObject(2, employeeId);
            statement.setObject(3, oldPlaceId);
            statement.setObject(4, new java.sql.Date((new Date()).getTime()));
            statement.setObject(5, new java.sql.Date((serviceDate).getTime()));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ! the problem is that we don't know the id the post created,
        // ! so we just fetch the latest
        return getLatest(connection);
    }

    public static ArrayList<Posting> getAll(Connection connection) {
        ArrayList<Posting> postings = new ArrayList<Posting>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT p.id as postingId, " +
                            "p.service_date as postingServiceDate, " +
                            "p.posting_date as postingDate, " +
                            "e.id as employeeId, " +
                            "e.civility as employeeCivility, " +
                            "e.first_name as employeeFirstName, " +
                            "e.last_name as employeeLastName, " +
                            "e.email as employeeEmail, " +
                            "e.job as employeeJob, " +
                            "p1.id as actualPlaceId, " +
                            "p1.name as actualPlaceName, " +
                            "p1.province as actualPlaceProvince, " +
                            "p2.id as postingPlaceId, " +
                            "p2.name as postingPlaceName, " +
                            "p2.province as postingPlaceProvince, " +
                            "p3.id as oldPlaceId, " +
                            "p3.name as oldPlaceName, " +
                            "p3.province as oldPlaceProvince " +
                            "FROM " + Posting.TABLE_NAME + " p " +
                            "INNER JOIN " + Employee.TABLE_NAME + " e ON p.employee_id = e.id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p1 ON p1.id = e.place_id " +
                            "INNER JOIN " + Place.TABLE_NAME + " p2 ON p2.id = p.place_id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p3 ON p3.id = p.old_place_id " +
                            "ORDER BY p.id DESC");

            ResultSet resultSet = statement.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (resultSet.next()) {
                postings.add(
                        new Posting(
                                resultSet.getInt("postingId"),
                                new Employee(
                                        resultSet.getInt("employeeId"),
                                        resultSet.getString("employeeLastName"),
                                        resultSet.getString("employeeFirstName"),
                                        resultSet.getString("employeeEmail"),
                                        resultSet.getString("employeeCivility"),
                                        resultSet.getString("employeeJob"),
                                        new Place(
                                                resultSet.getInt("actualPlaceId"),
                                                resultSet.getString("actualPlaceName"),
                                                resultSet.getString("actualPlaceProvince"))),
                                new Place(
                                        resultSet.getInt("postingPlaceId"),
                                        resultSet.getString("postingPlaceName"),
                                        resultSet.getString("postingPlaceProvince")),
                                new Place(
                                        resultSet.getInt("oldPlaceId"),
                                        resultSet.getString("oldPlaceName"),
                                        resultSet.getString("oldPlaceProvince")),
                                dateFormat.parse(resultSet.getString("postingDate")),
                                dateFormat.parse(resultSet.getString("postingServiceDate"))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return postings;
    }

    public static ArrayList<Posting> getBetweenDates(Connection connection, Date startAt, Date endAt) {
        ArrayList<Posting> postings = new ArrayList<Posting>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT p.id as postingId, " +
                            "p.service_date as postingServiceDate, " +
                            "p.posting_date as postingDate, " +
                            "e.id as employeeId, " +
                            "e.civility as employeeCivility, " +
                            "e.first_name as employeeFirstName, " +
                            "e.last_name as employeeLastName, " +
                            "e.email as employeeEmail, " +
                            "e.job as employeeJob, " +
                            "p1.id as actualPlaceId, " +
                            "p1.name as actualPlaceName, " +
                            "p1.province as actualPlaceProvince, " +
                            "p2.id as postingPlaceId, " +
                            "p2.name as postingPlaceName, " +
                            "p2.province as postingPlaceProvince, " +
                            "p3.id as oldPlaceId, " +
                            "p3.name as oldPlaceName, " +
                            "p3.province as oldPlaceProvince " +
                            "FROM " + Posting.TABLE_NAME + " p " +
                            "INNER JOIN " + Employee.TABLE_NAME + " e ON p.employee_id = e.id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p1 ON p1.id = e.place_id " +
                            "INNER JOIN " + Place.TABLE_NAME + " p2 ON p2.id = p.place_id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p3 ON p3.id = p.old_place_id " +
                            "WHERE p.posting_date BETWEEN ? AND ? " +
                            "ORDER BY p.id DESC");

            statement.setObject(1, new java.sql.Date((startAt).getTime()));
            statement.setObject(2, new java.sql.Date((endAt).getTime()));

            ResultSet resultSet = statement.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (resultSet.next()) {
                postings.add(
                        new Posting(
                                resultSet.getInt("postingId"),
                                new Employee(
                                        resultSet.getInt("employeeId"),
                                        resultSet.getString("employeeLastName"),
                                        resultSet.getString("employeeFirstName"),
                                        resultSet.getString("employeeEmail"),
                                        resultSet.getString("employeeCivility"),
                                        resultSet.getString("employeeJob"),
                                        new Place(
                                                resultSet.getInt("actualPlaceId"),
                                                resultSet.getString("actualPlaceName"),
                                                resultSet.getString("actualPlaceProvince"))),
                                new Place(
                                        resultSet.getInt("postingPlaceId"),
                                        resultSet.getString("postingPlaceName"),
                                        resultSet.getString("postingPlaceProvince")),
                                new Place(
                                        resultSet.getInt("oldPlaceId"),
                                        resultSet.getString("oldPlaceName"),
                                        resultSet.getString("oldPlaceProvince")),
                                dateFormat.parse(resultSet.getString("postingDate")),
                                dateFormat.parse(resultSet.getString("postingServiceDate"))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return postings;
    }

    public static ArrayList<Posting> getByEmployeeId(Connection connection, Integer employeeId) {
        ArrayList<Posting> postings = new ArrayList<Posting>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT p.id as postingId, " +
                            "p.service_date as postingServiceDate, " +
                            "p.posting_date as postingDate, " +
                            "e.id as employeeId, " +
                            "e.civility as employeeCivility, " +
                            "e.first_name as employeeFirstName, " +
                            "e.last_name as employeeLastName, " +
                            "e.email as employeeEmail, " +
                            "e.job as employeeJob, " +
                            "p1.id as actualPlaceId, " +
                            "p1.name as actualPlaceName, " +
                            "p1.province as actualPlaceProvince, " +
                            "p2.id as postingPlaceId, " +
                            "p2.name as postingPlaceName, " +
                            "p2.province as postingPlaceProvince, " +
                            "p3.id as oldPlaceId, " +
                            "p3.name as oldPlaceName, " +
                            "p3.province as oldPlaceProvince " +
                            "FROM " + Posting.TABLE_NAME + " p " +
                            "INNER JOIN " + Employee.TABLE_NAME + " e ON p.employee_id = e.id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p1 ON p1.id = e.place_id " +
                            "INNER JOIN " + Place.TABLE_NAME + " p2 ON p2.id = p.place_id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p3 ON p3.id = p.old_place_id " +
                            "WHERE p.employee_id = ? " +
                            "ORDER BY p.id DESC");
            statement.setObject(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (resultSet.next()) {
                postings.add(
                        new Posting(
                                resultSet.getInt("postingId"),
                                new Employee(
                                        resultSet.getInt("employeeId"),
                                        resultSet.getString("employeeLastName"),
                                        resultSet.getString("employeeFirstName"),
                                        resultSet.getString("employeeEmail"),
                                        resultSet.getString("employeeCivility"),
                                        resultSet.getString("employeeJob"),
                                        new Place(
                                                resultSet.getInt("actualPlaceId"),
                                                resultSet.getString("actualPlaceName"),
                                                resultSet.getString("actualPlaceProvince"))),
                                new Place(
                                        resultSet.getInt("postingPlaceId"),
                                        resultSet.getString("postingPlaceName"),
                                        resultSet.getString("postingPlaceProvince")),
                                new Place(
                                        resultSet.getInt("oldPlaceId"),
                                        resultSet.getString("oldPlaceName"),
                                        resultSet.getString("oldPlaceProvince")),
                                dateFormat.parse(resultSet.getString("postingDate")),
                                dateFormat.parse(resultSet.getString("postingServiceDate"))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return postings;
    }

    public static Posting getById(Connection connection, Integer id) {
        Posting posting = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT p.id as postingId, " +
                            "p.service_date as postingServiceDate, " +
                            "p.posting_date as postingDate, " +
                            "e.id as employeeId, " +
                            "e.civility as employeeCivility, " +
                            "e.first_name as employeeFirstName, " +
                            "e.last_name as employeeLastName, " +
                            "e.email as employeeEmail, " +
                            "e.job as employeeJob, " +
                            "p1.id as actualPlaceId, " +
                            "p1.name as actualPlaceName, " +
                            "p1.province as actualPlaceProvince, " +
                            "p2.id as postingPlaceId, " +
                            "p2.name as postingPlaceName, " +
                            "p2.province as postingPlaceProvince, " +
                            "p3.id as oldPlaceId, " +
                            "p3.name as oldPlaceName, " +
                            "p3.province as oldPlaceProvince " +
                            "FROM " + Posting.TABLE_NAME + " p " +
                            "INNER JOIN " + Employee.TABLE_NAME + " e ON p.employee_id = e.id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p1 ON p1.id = e.place_id " +
                            "INNER JOIN " + Place.TABLE_NAME + " p2 ON p2.id = p.place_id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p3 ON p3.id = p.old_place_id " +
                            "WHERE p.id = ? " +
                            "ORDER BY p.id DESC");
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (resultSet.next()) {
                posting = new Posting(
                        resultSet.getInt("postingId"),
                        new Employee(
                                resultSet.getInt("employeeId"),
                                resultSet.getString("employeeLastName"),
                                resultSet.getString("employeeFirstName"),
                                resultSet.getString("employeeEmail"),
                                resultSet.getString("employeeCivility"),
                                resultSet.getString("employeeJob"),
                                new Place(
                                        resultSet.getInt("actualPlaceId"),
                                        resultSet.getString("actualPlaceName"),
                                        resultSet.getString("actualPlaceProvince"))),
                        new Place(
                                resultSet.getInt("postingPlaceId"),
                                resultSet.getString("postingPlaceName"),
                                resultSet.getString("postingPlaceProvince")),
                        new Place(
                                resultSet.getInt("oldPlaceId"),
                                resultSet.getString("oldPlaceName"),
                                resultSet.getString("oldPlaceProvince")),
                        dateFormat.parse(resultSet.getString("postingDate")),
                        dateFormat.parse(resultSet.getString("postingServiceDate")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posting;
    }

    public static Posting getLatest(Connection connection) {
        Posting posting = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT p.id as postingId, " +
                            "p.service_date as postingServiceDate, " +
                            "p.posting_date as postingDate, " +
                            "e.id as employeeId, " +
                            "e.civility as employeeCivility, " +
                            "e.first_name as employeeFirstName, " +
                            "e.last_name as employeeLastName, " +
                            "e.email as employeeEmail, " +
                            "e.job as employeeJob, " +
                            "p1.id as actualPlaceId, " +
                            "p1.name as actualPlaceName, " +
                            "p1.province as actualPlaceProvince, " +
                            "p2.id as postingPlaceId, " +
                            "p2.name as postingPlaceName, " +
                            "p2.province as postingPlaceProvince, " +
                            "p3.id as oldPlaceId, " +
                            "p3.name as oldPlaceName, " +
                            "p3.province as oldPlaceProvince " +
                            "FROM " + Posting.TABLE_NAME + " p " +
                            "INNER JOIN " + Employee.TABLE_NAME + " e ON p.employee_id = e.id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p1 ON p1.id = e.place_id " +
                            "INNER JOIN " + Place.TABLE_NAME + " p2 ON p2.id = p.place_id " +
                            "LEFT JOIN " + Place.TABLE_NAME + " p3 ON p3.id = p.old_place_id " +
                            "ORDER BY p.id DESC LIMIT 1");

            ResultSet resultSet = statement.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (resultSet.next()) {
                posting = new Posting(
                        resultSet.getInt("postingId"),
                        new Employee(
                                resultSet.getInt("employeeId"),
                                resultSet.getString("employeeLastName"),
                                resultSet.getString("employeeFirstName"),
                                resultSet.getString("employeeEmail"),
                                resultSet.getString("employeeCivility"),
                                resultSet.getString("employeeJob"),
                                new Place(
                                        resultSet.getInt("actualPlaceId"),
                                        resultSet.getString("actualPlaceName"),
                                        resultSet.getString("actualPlaceProvince"))),
                        new Place(
                                resultSet.getInt("postingPlaceId"),
                                resultSet.getString("postingPlaceName"),
                                resultSet.getString("postingPlaceProvince")),
                        new Place(
                                resultSet.getInt("oldPlaceId"),
                                resultSet.getString("oldPlaceName"),
                                resultSet.getString("oldPlaceProvince")),
                        dateFormat.parse(resultSet.getString("postingDate")),
                        dateFormat.parse(resultSet.getString("postingServiceDate")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posting;
    }

    public static void update(Connection connection, Integer id, Integer employeeId, Integer placeId,
            Date serviceDate) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE posting SET " +
                            "employee_id = ?, " +
                            "place_id = ?, " +
                            "service_date = ? " +
                            "WHERE id = ?;");
            statement.setObject(1, employeeId);
            statement.setObject(2, placeId);
            statement.setObject(3, new java.sql.Date((serviceDate).getTime()));
            statement.setObject(4, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destroy(Connection connection, Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM " +
                            Posting.TABLE_NAME +
                            " WHERE id = ?;");
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
