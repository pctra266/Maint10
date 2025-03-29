/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Permissions;
import Model.RolePermission;
import Model.Roles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author PC
 */
public class PermissionDAO extends DBContext {

    /**
     * Get All permission
     *
     * @return
     */
    public ArrayList<Permissions> getAllPermission() {
        ArrayList<Permissions> listPermission = new ArrayList<>();
        String sql = "SELECT [PermissionID], [PermissionName], [Link], [Description]\n"
                + "FROM [dbo].[Permissions]\n"
                + "WHERE 1 = 1";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Permissions permission = new Permissions();
                permission.setPermissionID(rs.getInt("PermissionID"));
                permission.setPermissionName(rs.getString("PermissionName"));
                permission.setLink(rs.getString("Link"));
                permission.setDescription(rs.getString("Description"));

                listPermission.add(permission);
            }
        } catch (SQLException e) {

        }
        return listPermission;
    }

    public int GetPermissionPage() {
        String sql = "SELECT COUNT(*)\n"
                + "FROM [dbo].[Permissions]\n"
                + "WHERE 1 = 1";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    /**
     * Get permission by role ID
     *
     * @param roleID
     * @return
     */
    public ArrayList<Permissions> getPermissionByRoleID(int roleID) {
        ArrayList<Permissions> listPermission = new ArrayList<>();
        String sql = "SELECT p.PermissionID ,p.PermissionName , p.Link,p.Description\n"
                + "FROM Permissions p LEFT JOIN Role_Permissions rp ON p.PermissionID = rp.PermissionID\n"
                + "                   LEFT JOIN Role r ON r.RoleID = rp.RoleID\n"
                + "				   WHERE r.RoleID = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Permissions permission = new Permissions();
                permission.setPermissionID(rs.getInt("PermissionID"));
                permission.setPermissionName(rs.getString("PermissionName"));
                permission.setLink(rs.getString("Link"));
                permission.setDescription(rs.getString("Description"));
                listPermission.add(permission);
            }
        } catch (SQLException e) {

        }
        return listPermission;
    }

    public ArrayList<Roles> getAllRole() {
        ArrayList<Roles> listRole = new ArrayList<>();
        String sql = "SELECT [RoleID]\n"
                + "      ,[RoleName]\n"
                + "  FROM [dbo].[Role]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles role = new Roles();
                role.setRoleID(rs.getInt("RoleID"));
                role.setRoleNamel(rs.getString("RoleName"));
                listRole.add(role);
            }
        } catch (Exception e) {

        }
        return listRole;
    }

    public ArrayList<RolePermission> getAllRoleAndPermission() {
        ArrayList<RolePermission> list = new ArrayList<>();
        String sql = "SELECT r.RoleID , r.RoleName,p.PermissionID,p.PermissionName,p.Link,p.Description\n"
                + "FROM Role r LEFT JOIN Role_Permissions rp ON r.RoleID = rp.RoleID\n"
                + "            LEFT JOIN Permissions p ON p.PermissionID = rp.PermissionID";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RolePermission rolePerrmission = new RolePermission();
                rolePerrmission.setRoleID(rs.getInt("RoleID"));
                rolePerrmission.setRoleName(rs.getString("RoleName"));
                rolePerrmission.setPermissionID(rs.getInt("PermissionID"));
                rolePerrmission.setPermissionName(rs.getString("PermissionName"));
                rolePerrmission.setLink(rs.getString("Link"));
                rolePerrmission.setDescription(rs.getString("Description"));

                list.add(rolePerrmission);
            }
        } catch (SQLException e) {

        }
        return list;
    }

    public void addRoleAndPermission(int roleID, int permissionID) {
        String sql = "INSERT INTO [dbo].[Role_Permissions]\n"
                + "           ([RoleID]\n"
                + "           ,[PermissionID])\n"
                + "     VALUES\n"
                + "           (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleID);
            ps.setInt(2, permissionID);
            ps.executeUpdate();
        } catch (SQLException e) {

        }
    }
    //

    /**
     * Check role permission
     *
     * @param roleID
     * @param link
     * @return
     */
    public boolean checkRolePermission(int roleID, String link) {
        String sql = "SELECT COUNT(*)\n"
                + "FROM Role r \n"
                + "JOIN Role_Permissions rp ON r.RoleID = rp.RoleID\n"
                + "JOIN Permissions p ON p.PermissionID = rp.PermissionID\n"
                + "WHERE r.RoleID = ? AND p.Link = ?\n"
                + "AND p.Link IS NOT NULL;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleID);
            ps.setString(2, link);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT(*) > 0 thì có quyền
            }
        } catch (SQLException e) {

        }
        return false;
    }

    public List<Integer> getPermissiIDonByRoleID(int roleID) {
        List<Integer> rolePermissions = new ArrayList<>();
        String sql = "SELECT [RoleID]\n"
                + "      ,[PermissionID]\n"
                + "  FROM [dbo].[Role_Permissions]\n"
                + "  WHERE RoleID =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rolePermissions.add(rs.getInt("PermissionID"));
            }
        } catch (Exception e) {

        }
        return rolePermissions;
    }

    public void deletePermission(int roleID, int permissionID) {
        String sql = "DELETE FROM [dbo].[Role_Permissions]\n"
                + "      WHERE RoleID = ? AND PermissionID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleID);
            ps.setInt(2, permissionID);
            ps.executeUpdate();
        } catch (Exception e) {

        }

    }

    public void addPermission(int roleId, int permissionID) {
        String sql = "INSERT INTO [dbo].[Role_Permissions]\n"
                + "           ([RoleID]\n"
                + "           ,[PermissionID])\n"
                + "     VALUES\n"
                + "           (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleId);
            ps.setInt(2, permissionID);
            ps.executeUpdate();
        } catch (Exception e) {

        }
    }
//

    /**
     * Add permisstion in to permisson table
     *
     * @param permission
     */
    public void addPermissionII(Permissions permission) {
        String sql = "INSERT INTO [dbo].[Permissions]\n"
                + "           ([PermissionName]\n"
                + "           ,[Link]\n"
                + "           ,[Description])\n"
                + "     VALUES\n"
                + "           (?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, permission.getPermissionName());
            ps.setString(2, permission.getLink());
            ps.setString(3, permission.getDescription());
            ps.executeUpdate();

        } catch (SQLException e) {

        }
    }

    public Permissions getPermisstionByNameAndLink(String name, String link) {
        String sql = "SELECT [PermissionID]\n"
                + "      ,[PermissionName]\n"
                + "      ,[Link]\n"
                + "      ,[Description]\n"
                + "  FROM [dbo].[Permissions]\n"
                + "  WHERE PermissionName = ? AND Link =?";
        try{
            PreparedStatement ps = connection.prepareStatement(link);
            ps.setString(1, name);
            ps.setString(2, link);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Permissions permissions = new Permissions();
                permissions.setPermissionID(rs.getInt("PermissionID"));
                permissions.setPermissionName(rs.getString("PermissionName"));
                permissions.setLink(rs.getString("Link"));
                permissions.setDescription(rs.getString("Description"));
                return  permissions;
            }
         } catch (SQLException e) {
             
         }
        return null;
    }

    public static void main(String[] args) {
        PermissionDAO permissionDAO = new PermissionDAO();
        List<Integer> permissions = permissionDAO.getPermissiIDonByRoleID(1);

        // Kiểm tra danh sách có dữ liệu không
        if (permissions.isEmpty()) {
            System.out.println("Không có quyền nào được trả về!");
        } else {
            System.out.println("Danh sách quyền của roleId=1:");
            for (Integer permId : permissions) {
                System.out.println("Permission ID: " + permId);
            }
        }
    }

}
