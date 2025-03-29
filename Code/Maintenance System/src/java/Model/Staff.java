package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private Set<String> permissions;
    private int count;
    private String str;
    
    public Staff(int staffID, String name, String gender, String image, int count, String str) {
        this.staffID = staffID;
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.count = count;
        this.str = str;
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
//    private String formatDate(String dateStr) {
//        if (dateStr == null || dateStr.isEmpty()) {
//            return "Null";
//        }
//        try {
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng gốc từ database
//            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng mong muốn
//            Date date = inputFormat.parse(dateStr);
//            return outputFormat.format(date);
//        } catch (Exception e) {
//            return "Invalid Date"; // Trả về lỗi nếu định dạng không đúng
//        }
//    }

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

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
    
    
    public boolean hasPermissions(String per){

        return permissions.contains(per);
    }

    @Override
    public String toString() {
        return "Staff{" + "staffID=" + staffID + ", usernameS=" + usernameS + ", passwordS=" + passwordS + ", role=" + role + ", name=" + name + ", gender=" + gender + ", date=" + date + ", email=" + email + ", phone=" + phone + ", address=" + address + ", image=" + image + ", permission=" + permissions + '}';
    }
    
}
