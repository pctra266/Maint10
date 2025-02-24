package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Staff;

/**
 *
 * @author ADMIN
 */
public class StaffDAO extends DBContext {

    public Staff getStaffByUsenamePassword(String username, String password) {
        String sql = """
                     SELECT [StaffID]
                           ,[UsernameS]
                           ,[PasswordS]
                           ,[Name]
                           ,[RoleID]
                           ,[Email]
                           ,[Phone]
                           ,[Address]
                           ,[Image]
                       FROM [dbo].[Staff]
                       WHERE UsernameS = ? AND PasswordS = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setUsernameS(rs.getString("UsernameS"));
                staff.setPasswordS(rs.getString("PasswordS"));
                staff.setRole(rs.getInt("RoleID"));
                staff.setName(rs.getString("Name"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setImgage(rs.getString("Image"));
                return staff;

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void changePassword(Staff s) {
        String sql = """
                     UPDATE [dbo].[Staff]
                        SET [PasswordS] = ?
                      WHERE UsernameS = ?;""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, s.getPasswordS());
            ps.setString(2, s.getUsernameS());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Staff getStaffByEmail(String email) {
        String sql = "SELECT [StaffID]\n"
                + "      ,[UsernameS]\n"
                + "      ,[PasswordS]\n"
                + "      ,[Name]\n"
                + "      ,[RoleID]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Staff]\n"
                + "  WHERE Email=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setUsernameS(rs.getString("UsernameS"));
                staff.setPasswordS(rs.getString("PasswordS"));
                staff.setRole(rs.getInt("RoleID"));
                staff.setName(rs.getString("Name"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setImgage(rs.getString("Image"));
                return staff;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean addStaff(String useNameS, String passworldS, String name, String role, String gender, String date, String email, String phone, String address, String image) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "INSERT INTO Staff (UsernameS, PasswordS, [Name], [RoleID],Gender, DateOfBirth, Email, Phone, [Address],Image) VALUES (?,?,?,?,?,?,?,?,?,?);";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, useNameS);
            stm.setString(2, passworldS);
            stm.setString(3, name);
            stm.setString(4, role);
            stm.setString(5, gender);
            stm.setString(6, date);
            stm.setString(7, email);
            stm.setString(8, phone);
            stm.setString(9, address);
            stm.setString(10, image);

            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public boolean addStaff_Role(String staffID, String roleID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "INSERT INTO Staff_Role (StaffID, RoleID) VALUES (?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, staffID);
            stm.setString(2, roleID);
            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public boolean updateStaff_Role(String staffID, String roleID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "UPDATE Staff_Role set RoleID = ? where StaffID =?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, roleID);
            stm.setString(2, staffID);
            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public String updateStaff_Role(String phone) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String staffID = null;
        String sql = "SELECT StaffID FROM Staff WHERE Phone = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                staffID = rs.getString("StaffID");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return staffID;
    }

    public boolean updateStaff(String staffID, String useNameS, String passworldS, String role, String gender, String date, String name, String email, String phone, String address, String image) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "UPDATE Staff SET UsernameS = ?, PasswordS = ?, Name = ?, RoleID = ? , Gender = ?, DateOfBirth = ?, Email = ?, Phone = ?, Address = ? ,Image = ? WHERE StaffID = ?;";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, useNameS);
            stm.setString(2, passworldS);
            stm.setString(3, name);
            stm.setString(4, role);
            stm.setString(5, gender);
            stm.setString(6, date);
            stm.setString(7, email);
            stm.setString(8, phone);
            stm.setString(9, address);
            stm.setString(10, image);
            stm.setString(11, staffID);

            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public ArrayList<Staff> getAllStaff(String searchname, String search, int pageIndex, int pageSize, String column, String sortOrder) {
        ArrayList<Staff> list = new ArrayList<>();
        String sql = "select *from Staff";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (searchname != null && !searchname.trim().isEmpty()) {
            if (search.equals("Name")) {
                sql += " WHERE Name LIKE ?";
            } else {
                sql += " WHERE Email LIKE ?";
            }
        }
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by StaffID ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        }
        sql += " offset ? rows  fetch next ? rows only;";
        try {
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }
            int startIndex = (pageIndex - 1) * pageSize;
            stm.setInt(count++, startIndex);
            stm.setInt(count++, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                int role = rs.getInt("roleid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String date = rs.getString("dateofbirth");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                list.add(new Staff(staffID, usernameS, passwordS, role, name, gender, date, email, phone, address, image));

            }
        } catch (SQLException e) {
            System.out.println(e);

        }

        return list;
    }

    public ArrayList<Staff> getAllStaff(String searchname, String search, String column, String sortOrder) {
        ArrayList<Staff> list = new ArrayList<>();
        String sql = "select *from Staff ";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (searchname != null && !searchname.trim().isEmpty()) {
            if (search.equals("Name")) {
                sql += " WHERE Name LIKE ? ";
            } else {
                sql += " WHERE Role LIKE ? ";
            }
        }
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by StaffID ";
        }

        try {
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                int role = rs.getInt("roleid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String date = rs.getString("dateofbirth");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                list.add(new Staff(staffID, usernameS, passwordS, role, name, gender, date, email, phone, address, image));

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public Staff getInformationByID(String id) {
        Staff staff = new Staff();
        PreparedStatement stm;
        ResultSet rs;
        String sql = "SELECT * FROM Staff WHERE StaffID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                int role = rs.getInt("roleid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String date = rs.getString("dateofbirth");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                if (image == null || image.isEmpty()) {
                    image = "default-image.jpg"; // Đặt ảnh mặc định nếu không có ảnh trong DB
                }
                staff = new Staff(staffID, usernameS, passwordS, role, name, gender, date, email, phone, address, image);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return staff;
    }

    public boolean deleteStaff(String staffID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "DELETE FROM Staff WHERE StaffID=?;";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, staffID);
            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public boolean isPhoneExists(String phone) {
        String query = "SELECT Phone FROM Staff WHERE Phone = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, phone);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean isUpdatePhoneExists(String phone, String staffID) {
        String query = "SELECT Phone FROM Staff WHERE Phone = ? AND StaffID <> ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, phone);
            pstmt.setString(2, staffID);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public Staff getStaffById(int staffId) {
        String query = "SELECT * FROM Staff WHERE StaffID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                            rs.getInt("StaffID"),
                            rs.getString("UsernameS"),
                            rs.getString("PasswordS"),
                            rs.getInt("RoleID"),
                            rs.getString("Name"),
                            rs.getString("Gender"),
                            rs.getString("DateOfBirth"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
                            rs.getString("Image")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        StaffDAO staffDAO = new StaffDAO();

        Staff s = staffDAO.getStaffById(1);
        System.out.println(s.getName());
    }
}
