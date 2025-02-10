/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Pagination {
    public static List<Integer> listPageSize(int total) {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(30);
        list.add(total/10);
        list.add(total/5);
        list.add(total/4);
        list.add(total/3);
        return list;
    }
}
