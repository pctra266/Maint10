/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author PC
 */
public class Format {
    /**
     * Check format password 
     * @param password
     * @return 
     */
    public boolean checkPassword(String password) {
        boolean checkPassword = false;
       if(password.length() >= 8 && password.length() <=16 ) {
           checkPassword = true;
       }
        return checkPassword;
    }
    
    /*
    public boolean checkPhoneNumber(String phone) {
        boolean checkPhone = false;
       if(phone.length() == 10) {
           if(phone.startsWith(09))
       }
    }
*/
}
