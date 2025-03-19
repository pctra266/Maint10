package DAO;

import Model.ReportComponent;
import Model.ReportStaff;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Staff;
import java.util.List;

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
                staff.setImage(rs.getString("Image"));
                staff.setPermission(getPermissionsOfStaff(staff));
                return staff;

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<String> getPermissionsOfStaff(Staff staff) {
        List<String> list = new ArrayList<>();
        String sql = """
                     select p.PermissionName from Staff s 
                     join Role_Permissions rp on s.RoleID=rp.RoleID
                     join [Permissions] p on rp.PermissionID=p.PermissionID
                     where s.StaffID=?""";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, staff.getStaffID());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("PermissionName"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
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
                staff.setImage(rs.getString("Image"));
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
    public int getAllStaff() {
        String sql = "select *from Staff";
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        try {
            stm = connection.prepareStatement(sql);          
            rs = stm.executeQuery();
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
            System.out.println(e);

        }

        return count;
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
                sql += " WHERE Email LIKE ? ";
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
//                if (image == null || image.isEmpty()) {
//                    image = "default-image.jpg"; // Đặt ảnh mặc định nếu không có ảnh trong DB
//                }
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

    public boolean isExists(String str) {
        String query = "SELECT * FROM Staff";
        if(str.contains("@")){
            query +=" WHERE Email = ?";
        }else if(str.matches("\\d+")){
            query +=" WHERE Phone = ?";
        }else{
            query +=" WHERE UsernameS = ?";
        }
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, str);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean isUpdatePhoneExists(String str, String staffID) {
        String query = "SELECT * FROM Staff";
        if(str.contains("@")){
            query +=" WHERE Email = ? And StaffID <> ?";
        }else if(str.matches("\\d+")){
            query +=" WHERE Phone = ? And StaffID <> ?";
        }else{
            query +=" WHERE UsernameS = ? And StaffID <> ?";
        }
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, str);
            pstmt.setString(2, staffID);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    

    public int[] importStaff(List<Staff> staffList) throws SQLException {
        String selectSQL = "SELECT * FROM Staff WHERE StaffID = ?";
        String insertSQL = "INSERT INTO Staff (UsernameS, PasswordS, Name, RoleID, Gender, DateOfBirth, Email, Phone, Address, Image) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSQL = "UPDATE Staff SET UsernameS=?, PasswordS=?, Name=?, RoleID=?, Gender=?, DateOfBirth=?, "
                + "Email=?, Phone=?, Address=?, Image=? WHERE StaffID=?";
        int countInsert=0;
        int countUpdate=0;
        try {
            PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
            PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
            for (Staff staff : staffList) {
                selectStmt.setInt(1, staff.getStaffID());
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) { // Nếu tồn tại, kiểm tra sự thay đổi
                    boolean isChanged = !staff.getUsernameS().equals(rs.getString("UsernameS"))
                            || !staff.getPasswordS().equals(rs.getString("PasswordS"))
                            || !staff.getName().equals(rs.getString("Name"))
                            || staff.getRole() != rs.getInt("RoleID")
                            || !staff.getGender().equals(rs.getString("Gender"))
                            || !staff.getDate().equals(rs.getString("DateOfBirth"))
                            || !staff.getEmail().equals(rs.getString("Email"))
                            || !staff.getPhone().equals(rs.getString("Phone"))
                            || !staff.getAddress().equals(rs.getString("Address"))
                            || !staff.getImage().equals(rs.getString("Image"));

                    if (isChanged) { // Nếu có thay đổi, thực hiện UPDATE
                        updateStmt.setString(1, staff.getUsernameS());
                        updateStmt.setString(2, staff.getPasswordS());
                        updateStmt.setString(3, staff.getName());
                        updateStmt.setInt(4, staff.getRole());
                        updateStmt.setString(5, staff.getGender());
                        updateStmt.setString(6, staff.getDate());
                        updateStmt.setString(7, staff.getEmail());
                        updateStmt.setString(8, staff.getPhone());
                        updateStmt.setString(9, staff.getAddress());
                        updateStmt.setString(10, staff.getImage());
                        updateStmt.setInt(11, staff.getStaffID());

                        updateStmt.executeUpdate();
                        countUpdate++;

                    }
                } else { // Nếu chưa tồn tại, INSERT mới
                    insertStmt.setString(1, staff.getUsernameS());
                    insertStmt.setString(2, staff.getPasswordS());
                    insertStmt.setString(3, staff.getName());
                    insertStmt.setInt(4, staff.getRole());
                    insertStmt.setString(5, staff.getGender());
                    insertStmt.setString(6, staff.getDate());
                    insertStmt.setString(7, staff.getEmail());
                    insertStmt.setString(8, staff.getPhone());
                    insertStmt.setString(9, staff.getAddress());
                    insertStmt.setString(10, staff.getImage());

                    insertStmt.executeUpdate();
                    countInsert++;
                }
            }
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return  new int[]{countInsert, countUpdate};
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

    public String getRoleNameByStaffID(int staffID) {
        String roleName = null;
        String sql = "SELECT r.RoleName FROM Staff s "
                + "JOIN Role r ON s.RoleID = r.RoleID "
                + "WHERE s.StaffID = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                roleName = rs.getString("RoleName");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return roleName;
    }

    public boolean updateStaffWithNoImage(int staffID, String name, String gender, String dateOfBirth,
            String email, String phone, String address) {
        String sql = "UPDATE Staff SET Name = ?, Gender = ?, DateOfBirth = ?, Email = ?, Phone = ?, Address = ? WHERE StaffID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, gender);
            stmt.setString(3, dateOfBirth);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, address);
            stmt.setInt(7, staffID);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean updateStaffImage(int staffId, String imageUrl) {
        String sql = "UPDATE Staff SET Image = ? WHERE StaffID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, imageUrl);
            stmt.setInt(2, staffId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public List<Staff> getAllTechnicians() {
        List<Staff> technicians = new ArrayList<>();
        String sql = "SELECT s.StaffID, s.UsernameS, s.Name, s.Gender, s.DateOfBirth, s.Email, s.Phone, s.Address, s.Image "
                + "FROM Staff s "
                + "JOIN [Role] r ON s.RoleID = r.RoleID "
                + "WHERE r.RoleName = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "Technician");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setUsernameS(rs.getString("UsernameS"));
                staff.setName(rs.getString("Name"));
                staff.setGender(rs.getString("Gender"));
                staff.setDate(rs.getString("DateOfBirth"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setImage(rs.getString("Image"));
                technicians.add(staff);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return technicians;
    }
    
    public List<Staff> getStaffByRoleName(String role) {
        List<Staff> technicians = new ArrayList<>();
        String sql = "SELECT s.StaffID, s.UsernameS, s.Name, s.Gender, s.DateOfBirth, s.Email, s.Phone, s.Address, s.Image, s.RoleID "
                + "FROM Staff s "
                + "JOIN [Role] r ON s.RoleID = r.RoleID "
                + "WHERE r.RoleName = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setUsernameS(rs.getString("UsernameS"));
                staff.setName(rs.getString("Name"));
                staff.setRole(rs.getInt("RoleID"));
                staff.setGender(rs.getString("Gender"));
                staff.setDate(rs.getString("DateOfBirth"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setImage(rs.getString("Image"));
                technicians.add(staff);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return technicians;
    }

    // Report Staff
    public ArrayList<Staff> getAllReport(){
        ArrayList<Staff> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT \n" +
                    "    s.StaffID,\n" +
                    "    s.Name,\n" +
                    "    s.Gender,\n" +
                    "    s.Image,\n" +
                    "    r.RoleName,\n" +
                    "    COUNT(DISTINCT wp.WarrantyCardID) AS UniqueWarrantyCards\n" +
                    "FROM Staff s\n" +
                    "JOIN WarrantyCardProcess wp ON wp.HandlerID = s.StaffID\n" +
                    "JOIN [Role] r ON r.RoleID = s.RoleID\n" +
                    "LEFT JOIN WarrantyCardProcess wp_latest \n" +
                    "    ON wp.WarrantyCardID = wp_latest.WarrantyCardID \n" +
                    "    AND wp_latest.ActionDate = (\n" +
                    "        SELECT MAX(ActionDate) \n" +
                    "        FROM WarrantyCardProcess \n" +
                    "        WHERE WarrantyCardID = wp.WarrantyCardID\n" +
                    "    )\n" +
                    "WHERE (wp_latest.Action IS NULL OR wp_latest.Action <> 'completed')\n" +
                    "GROUP BY \n" +
                    "    s.StaffID, \n" +
                    "    s.Name, \n" +
                    "    s.Gender, \n" +
                    "    s.Image, \n" +
                    "    r.RoleName;";
        try {
        stm = connection.prepareStatement(sql);
        rs = stm.executeQuery();
        
        while (rs.next()) {
            int staffID = rs.getInt("StaffID");
            String name = rs.getString("Name");
            String gender = rs.getString("Gender");
            String image = rs.getString("Image");
            String role = rs.getString("RoleName");
            int count = rs.getInt("UniqueWarrantyCards");
            list.add(new Staff(staffID, name, gender, image, count, role));
        }
        
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return list;
    }
    public ArrayList<Staff> getAllReportOut(){
        ArrayList<Staff> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT s.StaffID, s.Name, s.Gender, s.Image, r.RoleName\n" +
                    "FROM Staff s\n" +
                    "JOIN [Role] r ON r.RoleID = s.RoleID\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1 FROM (\n" +
                    "         SELECT wp.WarrantyCardID, MAX(wp.ActionDate) AS LastActionDate\n" +
                    "         FROM WarrantyCardProcess wp\n" +
                    "         WHERE wp.HandlerID = s.StaffID\n" +
                    "         GROUP BY wp.WarrantyCardID\n" +
                    "    ) AS LastRecords\n" +
                    "    JOIN WarrantyCardProcess wp_last ON wp_last.WarrantyCardID = LastRecords.WarrantyCardID AND wp_last.ActionDate = LastRecords.LastActionDate\n" +
                    "    WHERE wp_last.Action <> 'completed'\n" +
                    ")\n" +
                    "ORDER BY s.StaffID ASC;";
        try {
        stm = connection.prepareStatement(sql);
        rs = stm.executeQuery();
        
        while (rs.next()) {
            int staffID = rs.getInt("StaffID");
            String name = rs.getString("Name");
            String gender = rs.getString("Gender");
            String image = rs.getString("Image");
            String role = rs.getString("RoleName");
            int count = 0;
            list.add(new Staff(staffID, name, gender, image, count, role));
        }
        
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return list;
    }
    public ArrayList<ReportStaff> getAllStaffRepairByID(String id,int pageIndex, int pageSize){
        ArrayList<ReportStaff> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT s.StaffID, s.Name, s.Gender, s.Image, r.RoleName, wp.Action, wp.ActionDate, w.WarrantyCardCode\n" +
                    "FROM Staff s\n" +
                    "JOIN [Role] r ON r.RoleID = s.RoleID\n" +
                    "JOIN WarrantyCardProcess wp ON wp.HandlerID = s.StaffID\n" +
                    "JOIN WarrantyCard w ON w.WarrantyCardID = wp.WarrantyCardID\n" +
                    "WHERE s.StaffID = ? \n" +
                    "AND NOT EXISTS (\n" +
                    "    SELECT 1 \n" +
                    "    FROM WarrantyCardProcess wp_latest\n" +
                    "    WHERE wp.WarrantyCardID = wp_latest.WarrantyCardID \n" +
                    "    AND wp_latest.Action = 'completed' \n" +
                    "    AND wp_latest.ActionDate = (\n" +
                    "        SELECT MAX(ActionDate) \n" +
                    "        FROM WarrantyCardProcess \n" +
                    "        WHERE WarrantyCardID = wp.WarrantyCardID\n" +
                    "    )\n" +
                    ")\n" +
                    "ORDER BY wp.ActionDate DESC\n" +
                    "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        try {
        stm = connection.prepareStatement(sql);
        stm.setString(1, id);
        int startIndex = (pageIndex - 1) * pageSize;
            stm.setInt(2, startIndex);
            stm.setInt(3, pageSize);
        rs = stm.executeQuery();
        
        while (rs.next()) {
            int staffID = rs.getInt("StaffID");
            String name = rs.getString("Name");
            String gender = rs.getString("Gender");
            String image = rs.getString("Image");
            String role = rs.getString("RoleName");
            String action = rs.getString("Action");
            String actiondate = rs.getString("ActionDate");
            String warrantycardcode = rs.getString("WarrantyCardCode");
            list.add(new ReportStaff(staffID, name, gender, image, role, action, actiondate, warrantycardcode));
            
        }
        
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return list;
    }
    public ArrayList<ReportStaff> getAllStaffRepairByID(String id){
        ArrayList<ReportStaff> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT s.StaffID, s.Name, s.Gender, s.Image, r.RoleName, wp.Action, wp.ActionDate, w.WarrantyCardCode\n" +
                    "FROM Staff s\n" +
                    "JOIN [Role] r ON r.RoleID = s.RoleID\n" +
                    "JOIN WarrantyCardProcess wp ON wp.HandlerID = s.StaffID\n" +
                    "JOIN WarrantyCard w ON w.WarrantyCardID = wp.WarrantyCardID\n" +
                    "WHERE s.StaffID = ? AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM WarrantyCardProcess wp_latest\n" +
                    "    WHERE wp.WarrantyCardID = wp_latest.WarrantyCardID AND wp_latest.Action = 'completed' AND wp_latest.ActionDate = (\n" +
                    "        SELECT MAX(ActionDate) FROM WarrantyCardProcess \n" +
                    "		WHERE WarrantyCardID = wp.WarrantyCardID\n" +
                    "    )\n" +
                    ");";
        try {
        stm = connection.prepareStatement(sql);
        stm.setString(1, id);
        
        rs = stm.executeQuery();
        
        while (rs.next()) {
            int staffID = rs.getInt("StaffID");
            String name = rs.getString("Name");
            String gender = rs.getString("Gender");
            String image = rs.getString("Image");
            String role = rs.getString("RoleName");
            String action = rs.getString("Action");
            String actiondate = rs.getString("ActionDate");
            String warrantycardcode = rs.getString("WarrantyCardCode");
            list.add(new ReportStaff(staffID, name, gender, image, role, action, actiondate, warrantycardcode));
            
        }
        
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return list;
    }
    public int getAllStaffNewStaff(){
        ArrayList<ReportStaff> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        String sql = "  SELECT * \n" +
                            "FROM [MaintainManagement].[dbo].[StaffLog]\n" +
                            "WHERE MONTH([Time]) = MONTH(GETDATE()) \n" +
                            "AND YEAR([Time]) = YEAR(GETDATE())\n" +
                            "AND Status = 'Create'";
        try {
        stm = connection.prepareStatement(sql);
        rs = stm.executeQuery();
        
        while (rs.next()) {
            count++;
            
        }
        
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return count;
    }
    
    public ArrayList<ReportComponent> getTop10ComponentInventory() {
        ArrayList<ReportComponent> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT TOP 10 ComponentID, ComponentName, Quantity, Price, (Quantity * Price) AS InventoryValue " +
                     "FROM Component " +
                     "ORDER BY Quantity DESC;";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String componentID = rs.getString("ComponentID");
                String componentName = rs.getString("ComponentName");
                String quantity = rs.getString("Quantity");
                String price = rs.getString("Price");
                String inventoryValue = rs.getString("InventoryValue");

                ReportComponent component = new ReportComponent(componentID, componentName, quantity, price, inventoryValue);
                list.add(component);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return list;
    }
    public ArrayList<ReportComponent> ComponentsStatisticsByType() {
        ArrayList<ReportComponent> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT ct.TypeName, COUNT(*) AS NumComponents,SUM(c.Quantity) AS TotalQuantity,SUM(c.Quantity * c.Price) AS TotalValue\n" +
                        "FROM Component c\n" +
                        "JOIN ComponentType ct ON c.TypeID = ct.TypeID\n" +
                        "GROUP BY ct.TypeName\n" +
                        "ORDER BY TotalQuantity DESC;";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String componentID = rs.getString("TypeName");
                String componentName = rs.getString("NumComponents");
                String quantity = rs.getString("TotalQuantity");
                String price = rs.getString("TotalValue");
                String inventoryValue = "";
                ReportComponent component = new ReportComponent(componentID, componentName, quantity, price, inventoryValue);
                list.add(component);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return list;
    }
    public ArrayList<ReportComponent> ComponentsSummaryReport() {
        ArrayList<ReportComponent> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS TotalComponents,SUM(Quantity) AS TotalQuantity,SUM(Quantity * Price) AS TotalInventoryValue\n" +
                    "FROM Component;";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String componentID = rs.getString("TotalComponents");
                String componentName = "";
                String quantity = rs.getString("TotalQuantity");
                String price = "";
                String inventoryValue = rs.getString("TotalInventoryValue");
                ReportComponent component = new ReportComponent(componentID, componentName, quantity, price, inventoryValue);
                list.add(component);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return list;
    }
    public ArrayList<ReportComponent> ComponentsStatisticsByBrand() {
        ArrayList<ReportComponent> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT b.BrandName,COUNT(*) AS NumComponents, SUM(c.Quantity) AS TotalQuantity, SUM(c.Quantity * c.Price) AS TotalValue\n" +
                    "FROM Component c\n" +
                    "JOIN Brand b ON c.BrandID = b.BrandID\n" +
                    "GROUP BY b.BrandName\n" +
                    "ORDER BY TotalQuantity DESC;";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String componentID = rs.getString("BrandName");
                String componentName = rs.getString("NumComponents");
                String quantity = rs.getString("TotalQuantity");
                String price = rs.getString("TotalValue");
                String inventoryValue = "";
                ReportComponent component = new ReportComponent(componentID, componentName, quantity, price, inventoryValue);
                list.add(component);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return list;
    }
    public ArrayList<ReportComponent> LowInventoryPartsReport() {
        ArrayList<ReportComponent> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT ComponentID,ComponentName,Quantity,Price,(Quantity * Price) AS InventoryValue\n" +
                    "FROM Component\n" +
                    "WHERE Quantity < 10\n" +
                    "ORDER BY Quantity ASC;";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String componentID = rs.getString("ComponentID");
                String componentName = rs.getString("ComponentName");
                String quantity = rs.getString("Quantity");
                String price = rs.getString("Price");
                String inventoryValue = rs.getString("InventoryValue");
                ReportComponent component = new ReportComponent(componentID, componentName, quantity, price, inventoryValue);
                list.add(component);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return list;
    }
     public ArrayList<ReportComponent> OutofStockPartsReport() {
        ArrayList<ReportComponent> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT ComponentID,ComponentName,Quantity,Price,(Quantity * Price) AS InventoryValue\n" +
                    "FROM Component\n" +
                    "WHERE Quantity = 10;\n";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String componentID = rs.getString("ComponentID");
                String componentName = rs.getString("ComponentName");
                String quantity = rs.getString("Quantity");
                String price = rs.getString("Price");
                String inventoryValue = rs.getString("InventoryValue");
                ReportComponent component = new ReportComponent(componentID, componentName, quantity, price, inventoryValue);
                list.add(component);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return list;
    }
      public ArrayList<ReportComponent> MostUsedComponentsInProducts() {
        ArrayList<ReportComponent> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT TOP 10 c.ComponentID, c.ComponentName, SUM(pc.Quantity) AS TotalUsed\n" +
                    "FROM Component c\n" +
                    "JOIN ProductComponents pc ON c.ComponentID = pc.ComponentID\n" +
                    "GROUP BY c.ComponentID, c.ComponentName\n" +
                    "ORDER BY TotalUsed DESC;";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String componentID = rs.getString("ComponentID");
                String componentName = rs.getString("ComponentName");
                String quantity = rs.getString("TotalUsed");
                String price = "";
                String inventoryValue = "";
                ReportComponent component = new ReportComponent(componentID, componentName, quantity, price, inventoryValue);
                list.add(component);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return list;
    }
       public ArrayList<ReportComponent> AveragePriceByComponentType() {
        ArrayList<ReportComponent> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT \n" +
                    "    ct.TypeName,\n" +
                    "    AVG(c.Price) AS AveragePrice\n" +
                    "FROM Component c\n" +
                    "JOIN ComponentType ct ON c.TypeID = ct.TypeID\n" +
                    "GROUP BY ct.TypeName\n" +
                    "ORDER BY AveragePrice DESC;";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String componentID = rs.getString("TypeName");
                String componentName = "";
                String quantity = rs.getString("AveragePrice");
                String price = "";
                String inventoryValue = "";
                ReportComponent component = new ReportComponent(componentID, componentName, quantity, price, inventoryValue);
                list.add(component);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return list;
    }



    
    
    public static void main(String[] args) {
        StaffDAO staffDAO = new StaffDAO();
        System.out.println(staffDAO.getStaffByUsenamePassword("tech01", "Cw2LaFmhUP2i/jGdPuB5aVCxAQg="));
        List<Staff> t = staffDAO.getAllTechnicians();

        for (Staff s : t) {
            System.out.println(s.getName());
        }
    }
}
