/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.kln.mit.restapi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lk.kln.mit.restapi.Database.Database;

/**
 *
 * @author Lasith
 */
public class User {

    private int id = -1;
    private String nic;
    private String fullName;
    private String address;
    private String dob;
    private String nationality;
    private String gender;
    private String age = null;
    private String state = null;
    private String created_by = null;
    private String created_on = null;
    private String created_at = null;
    private String modified_by = null;
    private String modified_on = null;
    private String modified_at = null;
    private String future_column_1 = null;
    private String future_column_2 = null;
    private String future_column_3 = null;

    private static final String FIND_ALL = "SELECT * FROM user WHERE state=1 ORDER BY modified_at DESC;";
    private static final String GET_COUNT = "SELECT COUNT(*) as count FROM user WHERE state=1";
    private static final String FIND_ONE = "SELECT * FROM user WHERE id = ? AND state=1";
    private static final String USER_REGISTRATION_COUNT = "SELECT COUNT(*) as count FROM user WHERE state=1 AND created_on >= DATE_SUB(CURDATE(), INTERVAL ? DAY); ";
    private static final String FILTERED_RESULT = "SELECT * FROM user WHERE state=1";
    private static final String GET_ACTIVITIES = "SELECT COUNT(*) as count FROM user WHERE state= ? AND modified_on >= (Now() - INTERVAL ? DAY); ";
    private static final String GET_NATIONALTY_COUNT = "SELECT SUM(CASE WHEN nationality = 'Sinhalese' THEN 1 ELSE 0 END) as `Sinhalese`,SUM(CASE WHEN nationality = 'Hindu' THEN 1 ELSE 0 END) as `Hindu`,SUM(CASE WHEN nationality = 'Islamic' THEN 1 ELSE 0 END) as `Islamic` FROM user;";
    private static final String GET_GENDER_COUNT = "SELECT SUM(CASE WHEN gender = 'Male' THEN 1 ELSE 0 END) as `Male`,SUM(CASE WHEN gender = 'Female' THEN 1 ELSE 0 END) as `Female` FROM user WHERE state=1";
    private static final String CREATE_USER = "INSERT INTO `user`(`nic`, `full_name`, `address`, `dob`, `nationality`, `gender`, `state`, `created_by`, `created_on`, `created_at`) VALUES (?,?,?,?,?,?,1,?,CAST(now() as Date),CAST(now() as Time))";
    private static final String UPDATE_USER = "UPDATE `user` SET `nic`= ? ,`full_name`= ? ,`address`= ?,`dob`= ?,`nationality`= ?,`gender`= ?, `modified_by`= ?,`modified_on`= CAST(now() as Date),`modified_at`= CAST(now() as Time) WHERE id = ?";
    private static final String DELETE_USER = "UPDATE `user` SET `state`= 0 , `modified_on`= CAST(now() as Date) , `modified_at`= CAST(now() as Time) WHERE id = ?";
    private static final String RECENT_ACTIVITIES = "SELECT * FROM `user` WHERE (`created_on` > Now() - INTERVAL 1 Day AND `created_at` > Now() - INTERVAL 1 Hour) OR (`modified_on`> Now() - INTERVAL 1 Day AND `modified_at` > Now() - INTERVAL 1 Hour) ORDER BY COALESCE(`modified_at`, `created_at`) DESC LIMIT 3;";
    private static final String SET_OF_USERS = "SELECT `id`, `nic`, `full_name`, `address`, `dob`, (year(curdate()) - year(dob)) as age, `nationality`, `gender`, `state`, `created_by`, `created_on`, `created_at`, `modified_by`, `modified_on`, `modified_at`, `future_column_1`, `future_column_2`, `future_column_3` FROM `user` WHERE state=1 ORDER BY COALESCE(`modified_at`, `created_at`) DESC LIMIT ? OFFSET ? ; ";
    private static final String AGE_GROUP_GRAPH = "SELECT SUM(CASE WHEN (year(curdate())-year(dob)) >18 AND (year(curdate())-year(dob))< 30 THEN 1 else 0 END) as `18-30`, SUM(CASE WHEN (year(curdate())-year(dob)) >30 AND (year(curdate())-year(dob))< 40 THEN 1 else 0 END) as `30-40`, SUM(CASE WHEN (year(curdate())-year(dob)) >40 AND (year(curdate())-year(dob))< 50 THEN 1 else 0 END) as `40-50`, SUM(CASE WHEN (year(curdate())-year(dob)) >50 AND (year(curdate())-year(dob))< 60 THEN 1 else 0 END) as `50-60`, SUM(CASE WHEN (year(curdate())-year(dob)) >=60 AND (year(curdate())-year(dob))<70 THEN 1 else 0 END) as `60-70`, SUM(CASE WHEN (year(curdate())-year(dob)) >= 70 THEN 1 else 0 END) as `70+` FROM user WHERE state = 1; ";

    public User() {

    }

    public User(int id, String nic, String fullName, String address, String dob, String nationality, String gender,
            String state, String created_by, String created_on, String created_at, String modified_by,
            String modified_on,
            String modified_at, String future_column_1, String future_column_2, String future_column_3) {
        this.id = id;
        this.nic = nic;
        this.fullName = fullName;
        this.address = address;
        this.dob = dob;
        this.nationality = nationality;
        this.gender = gender;
        this.state = state;
        this.created_by = created_by;
        this.created_on = created_on;
        this.created_at = created_at;
        this.modified_by = modified_by;
        this.modified_on = modified_on;
        this.modified_at = modified_at;
        this.future_column_1 = null;
        this.future_column_2 = null;
        this.future_column_3 = null;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public static int getNoOfUsers() {

        ResultSet resultSet;
        try (Connection conn = Database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_COUNT);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            } else {
                return 0;
            }

        } catch (Exception e) {

            return 0;
        }

    }

    public static int getUserRegistrationCount(int noOfDays) {

        ResultSet resultSet;
        try (Connection conn = Database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(USER_REGISTRATION_COUNT);
            statement.setInt(1, noOfDays);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            } else {
                return -1;
            }

        } catch (Exception e) {

            return -1;
        }

    }

    public static int getActivityCount(int noOfDays, String activity) {

        ResultSet resultSet;
        try (Connection conn = Database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_ACTIVITIES);

            if (activity.equals("delete")) {
                statement.setInt(1, 0);
            } else if (activity.equals("update")) {
                statement.setInt(1, 1);
            }

            statement.setInt(2, noOfDays);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            } else {
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static List<User> getSetOfUsers(int limit, int offset) {

        List<User> users = new ArrayList();
        User user;
        ResultSet resultSet;
        try (Connection conn = Database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SET_OF_USERS);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nic"),
                        resultSet.getString("full_name"),
                        resultSet.getString("address"),
                        resultSet.getString("dob"),
                        resultSet.getString("nationality"),
                        resultSet.getString("gender"),
                        resultSet.getString("state"),
                        resultSet.getString("created_by"),
                        resultSet.getString("created_on"),
                        resultSet.getString("created_at"),
                        resultSet.getString("modified_by"),
                        resultSet.getString("modified_on"),
                        resultSet.getString("modified_at"),
                        resultSet.getString("future_column_1"),
                        resultSet.getString("future_column_2"),
                        resultSet.getString("future_column_3")
                );
                user.setAge(String.valueOf(resultSet.getInt("age")));
                users.add(user);
            }

        } catch (Exception e) {

        }
        return users;
    }

    public static List<User> getRecentActivities() {

        ResultSet resultSet;
        List<User> users = new ArrayList();
        User user;

        try {
            Connection conn = Database.getConnection();
            PreparedStatement statement = conn.prepareStatement(RECENT_ACTIVITIES);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nic"),
                        resultSet.getString("full_name"),
                        resultSet.getString("address"),
                        resultSet.getString("dob"),
                        resultSet.getString("nationality"),
                        resultSet.getString("gender"),
                        resultSet.getString("state"),
                        resultSet.getString("created_by"),
                        resultSet.getString("created_on"),
                        resultSet.getString("created_at"),
                        resultSet.getString("modified_by"),
                        resultSet.getString("modified_on"),
                        resultSet.getString("modified_at"),
                        resultSet.getString("future_column_1"),
                        resultSet.getString("future_column_2"),
                        resultSet.getString("future_column_3")
                );
                users.add(user);
            }

        } catch (Exception e) {

        }
        return users;
    }

    public static List<User> find() {
        List<User> users = new ArrayList();
        User user;

        ResultSet resultSet;
        try {
            Connection conn = Database.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nic"),
                        resultSet.getString("full_name"),
                        resultSet.getString("address"),
                        resultSet.getString("dob"),
                        resultSet.getString("nationality"),
                        resultSet.getString("gender"),
                        resultSet.getString("state"),
                        resultSet.getString("created_by"),
                        resultSet.getString("created_on"),
                        resultSet.getString("created_at"),
                        resultSet.getString("modified_by"),
                        resultSet.getString("modified_on"),
                        resultSet.getString("modified_at"),
                        resultSet.getString("future_column_1"),
                        resultSet.getString("future_column_2"),
                        resultSet.getString("future_column_3")
                );
                users.add(user);
            }

        } catch (Exception e) {

        }
        return users;
    }

    public User getUser() {

        User user = null;
        ResultSet resultSet;

        try {
            Connection conn = Database.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ONE);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nic"),
                        resultSet.getString("full_name"),
                        resultSet.getString("address"),
                        resultSet.getString("dob"),
                        resultSet.getString("nationality"),
                        resultSet.getString("gender"),
                        resultSet.getString("state"),
                        resultSet.getString("created_by"),
                        resultSet.getString("created_on"),
                        resultSet.getString("created_at"),
                        resultSet.getString("modified_by"),
                        resultSet.getString("modified_on"),
                        resultSet.getString("modified_at"),
                        resultSet.getString("future_column_1"),
                        resultSet.getString("future_column_2"),
                        resultSet.getString("future_column_3"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public int save() {

        // boolean isNicDobGenderValid = validateNicDobGender(user.getNic(),
        // user.getDob().toString(), user.getGender());
        // boolean isNameValid = validName(user.getFullName());
        // boolean isAddressValid = validAddress(user.getAddress());
        if (nic == null || fullName == null || nationality == null || gender == null || dob == null
                || address == null || created_by == null) {
            return 2;
        }

        try (Connection conn = Database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER);
            statement.setString(1, nic);
            statement.setString(2, fullName);
            statement.setString(3, address);
            statement.setString(4, dob);
            statement.setString(5, nationality);
            statement.setString(6, gender);
            statement.setString(7, created_by);
            statement.executeUpdate();
            return 1;

        } catch (SQLIntegrityConstraintViolationException e) {
            return 4;
        } catch (Exception e) {

            return 0;
        }

    }

    public int update() {
        // boolean isNicDobGenderValid = validateNicDobGender(user.getNic(),
        // user.getDob().toString(), user.getGender());
        // boolean isNameValid = validName(user.getFullName());
        // boolean isAddressValid = validAddress(user.getAddress());

        if (id == -1 || nic == null || fullName == null || nationality == null || gender == null || dob == null || address == null || modified_by == null) {
            return 2;
        }

        try (Connection conn = Database.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(UPDATE_USER);
            statement.setString(1, nic);
            statement.setString(2, fullName);
            statement.setString(3, address);
            statement.setString(4, dob);
            statement.setString(5, nationality);
            statement.setString(6, gender);
            statement.setString(7, modified_by);
            statement.setInt(8, id);

            statement.executeUpdate();

            return 1;

        } catch (SQLIntegrityConstraintViolationException e) {
            return 4;
        } catch (Exception e) {
            return 0;
        }

    }

    public int remove() {

        if (id == -1) {
            return 2;
        }

        try (Connection conn = Database.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(DELETE_USER);
            statement.setInt(1, id);
            statement.executeUpdate();
            return 1;

        } catch (Exception e) {

            return 0;

        }
    }

    public static boolean validateNicDobGender(String nic, String submittedDob, String submittedGender) {

        int months[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        Pattern oldNicPattern = Pattern.compile("^\\d{9}[vxVX]{1}");
        Pattern newNicPattern = Pattern.compile("^\\d{12}");
        Matcher matcher = null;

        boolean validNic = false;
        boolean oldNIC = false;

        if (nic.length() == 0) {

            return false;

        } else {
            if (nic.length() == 12) {
                matcher = newNicPattern.matcher(nic);
                if (matcher.find()) {
                    validNic = true;
                    oldNIC = false;
                } else {
                    return false;
                }

            } else if (nic.length() == 10) {
                matcher = oldNicPattern.matcher(nic);
                if (matcher.find()) {

                    validNic = true;
                    oldNIC = true;
                } else {
                    return false;
                }

            }

            String gender = "";
            int year;
            int month = 0;
            int date;
            int monthDate;
            int yearDigits;

            if (validNic) {

                if (oldNIC) {
                    yearDigits = Integer.parseInt(nic.substring(0, 2));
                    monthDate = Integer.parseInt(nic.substring(2, 5));
                    year = 1900 + yearDigits;
                } else {
                    yearDigits = Integer.parseInt(nic.substring(0, 4));
                    monthDate = Integer.parseInt(nic.substring(4, 7));
                    year = yearDigits;
                }

                if (monthDate > 500) {
                    monthDate = monthDate - 500;
                    gender = "Female";
                } else {
                    gender = "Male";
                }

                int tempMd = monthDate;

                for (int i = 0; i < 12; i++) {
                    if (tempMd <= months[i]) {
                        month = i + 1;
                        break;
                    } else {
                        tempMd = tempMd - months[i];
                    }
                }

                // date
                for (int i = 0; i < month - 1; i++) {
                    monthDate = monthDate - months[i];
                }
                date = monthDate;
                String yearStr = Integer.toString(year);
                String monthStr = Integer.toString(month);
                if (monthStr.length() != 2) {
                    monthStr = "0" + monthStr;
                }
                String dateStr = Integer.toString(date);
                String generatedDob = yearStr + "-" + monthStr + "-" + dateStr;
                // compare submitted values and return
                if (submittedDob.equals(generatedDob) && submittedGender.equals(gender)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    public static boolean validName(String name) {
        Pattern namePattern = Pattern.compile("^[a-zA-Z]");
        Matcher matcher = namePattern.matcher(name);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validAddress(String address) {
        Pattern addressPattern = Pattern.compile("^[a-zA-Z0-9/.,]");
        Matcher matcher = addressPattern.matcher(address);
        if (address.length() == 0) {
            return false;
        }
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, Integer> getAgeGroupGraph() {

        Map<String, Integer> map = new LinkedHashMap<>();

        try (Connection conn = Database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(AGE_GROUP_GRAPH);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                for (int column = 1; column <= columnCount; column++) {
                    map.put(resultSetMetaData.getColumnName(column), resultSet.getInt(resultSetMetaData.getColumnName(column)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Integer> getNationalityCount() {

        Map<String, Integer> map = new LinkedHashMap<>();

        try (Connection conn = Database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_NATIONALTY_COUNT);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                for (int column = 1; column <= columnCount; column++) {
                    map.put(resultSetMetaData.getColumnName(column), resultSet.getInt(resultSetMetaData.getColumnName(column)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Integer> getGnderCount() {

        Map<String, Integer> map = new LinkedHashMap<>();

        try (Connection conn = Database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_GENDER_COUNT);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                for (int column = 1; column <= columnCount; column++) {
                    map.put(resultSetMetaData.getColumnName(column), resultSet.getInt(resultSetMetaData.getColumnName(column)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
