/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author PC
 */
public class Roles {
    private int roleID;
    private String roleNamel;
    private List<Permissions> permissions;
    public Roles() {
    }

    public Roles(int roleID, String roleNamel) {
        this.roleID = roleID;
        this.roleNamel = roleNamel;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleNamel() {
        return roleNamel;
    }

    public void setRoleNamel(String roleNamel) {
        this.roleNamel = roleNamel;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }

   
}
