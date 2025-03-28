/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author PC
 */
public class Permissions {
    private int permissionID;
    private String permissionName;
    private String description;
    private String link;

    public Permissions() {
    }

    public Permissions(int permissionID, String permissionName, String description, String link) {
        this.permissionID = permissionID;
        this.permissionName = permissionName;
        this.description = description;
        this.link = link;
    }

    public Permissions(String permissionName, String description, String link) {
        this.permissionName = permissionName;
        this.description = description;
        this.link = link;
    }
    

    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

 
}
