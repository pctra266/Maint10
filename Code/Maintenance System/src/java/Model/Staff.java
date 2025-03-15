package Model;

import java.util.List;

/**
 *
 * @author PC
 */
public class Staff {

    private int staffID;
    private String usernameS;
    private String passwordS;
    private int role;
    private String name;
    private String gender;
    private String date;
    private String email;
    private String phone;
    private String address;
    private String image;
    private List<String> permissions;

    public List<String> getPermission() {
        return permissions;
    }

    public void setPermission(List<String> permissions) {
        this.permissions = permissions;
    }

    public Staff() {
    }

    public Staff(int staffID, String usernameS, String passwordS, int role, String name, String gender, String date, String email, String phone, String address, String image) {
        this.staffID = staffID;
        this.usernameS = usernameS;
        this.passwordS = passwordS;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.date = date;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

      public Staff(int staffID, String name, String gender, String date, String email, String phone, String address) {
        this.staffID = staffID;
        this.name = name;
        this.gender = gender;
        this.date = date;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getUsernameS() {
        return usernameS;
    }

    public void setUsernameS(String usernameS) {
        this.usernameS = usernameS;
    }

    public String getPasswordS() {
        return passwordS;
    }

    public void setPasswordS(String passwordS) {
        this.passwordS = passwordS;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    } 

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    
    
    public boolean hasPermissions(String per){
        for (String p : permissions) {
            if(per.equals(p)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Staff{" + "staffID=" + staffID + ", usernameS=" + usernameS + ", passwordS=" + passwordS + ", role=" + role + ", name=" + name + ", gender=" + gender + ", date=" + date + ", email=" + email + ", phone=" + phone + ", address=" + address + ", image=" + image + ", permission=" + permissions + '}';
    }
    
}
