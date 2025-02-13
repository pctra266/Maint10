/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Pagination {
    private int pageSize; //So luong hang muon hien thi
    private int currentPage; //Hien tai dang o trang may
    private String[] searchFields; //Truyen vao cac truong can search
    private String[] searchValues; //Truyen vao gia tri theo thu tu can search voi cac truong
    private String[] rangeFields; //Truyen giong nhu cua search
    private Object[] rangeValues;
    private String sort;        // Sort theo truong nao
    private String order;       //Oder asc/desc
    private int totalPagesToShow; //So luong trang co the di chuyen (Vi du 5 trang thi thanh phan trang se co toi da 5)
    private int totalPages;     // So luong Page tinh bang so luong phan tu lay duoc tu truy xuat chia cho pageSize
                                //    int totalPages = (int) Math.ceil((double) totalComponents / pageSize);
                                //        if (page > totalPages) {
                                //            page = totalPages;
                                //        }
                                //        page = page < 1 ? 1 : page;
    private String urlPattern;  //Dang o trang nao thi truyen urlPattern trang day
    private List<Integer> listPageSize; //Show so luong entity động, cái này không includes được
    public List<Integer> getListPageSize() {
        return listPageSize;
    }

    public void setListPageSize(List<Integer> listPageSize) {
        this.listPageSize = listPageSize;
    }

    public Pagination() {
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String[] getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(String[] searchFields) {
        this.searchFields = searchFields;
    }

    public String[] getSearchValues() {
        return searchValues;
    }

    public void setSearchValues(String[] searchValues) {
        this.searchValues = searchValues;
    }

    public String[] getRangeFields() {
        return rangeFields;
    }

    public void setRangeFields(String[] rangeFields) {
        this.rangeFields = rangeFields;
    }

    public Object[] getRangeValues() {
        return rangeValues;
    }

    public void setRangeValues(Object[] rangeValue) {
        this.rangeValues = rangeValue;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getTotalPagesToShow() {
        return totalPagesToShow;
    }

    public void setTotalPagesToShow(int totalPagesToShow) {
        this.totalPagesToShow = totalPagesToShow;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }
    
     public void setListPageSize(int total) {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(30);
        list.add(total/5);
        list.add(total/4);
        list.add(total/3);
        listPageSize = list;
    }
     
    @Override
    public String toString() {
        return "Pagination{" + "pageSize=" + pageSize + ", currentPage=" + currentPage + ", searchFields=" + Arrays.toString(searchFields) + ", searchValues=" + Arrays.toString(searchValues) + ", rangeFields=" + Arrays.toString(rangeFields) + ", rangeValue=" + Arrays.toString(rangeValues) + ", sort=" + sort + ", order=" + order + ", totalPagesToShow=" + totalPagesToShow + ", totalPages=" + totalPages + ", urlPattern=" + urlPattern + '}';
    }
    
}
