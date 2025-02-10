<%-- 
    Document   : Staff
    Created on : Jan 17, 2025, 8:39:04 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link href="css/light.css" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center ">History of staff</h1>                   
                    <h2>All staff</h2>  
                    <form class="" action="seeMoreController" method="get">
                        <div class="col-md-6" style="width: 500px">
                            <input class="form-control" type="searchname" name="searchname" placeholder="Search"  value="${param.searchname}" style="margin-bottom:5px">
                            <select
                            id="search"
                            type="search"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="search"
                            required
                            >
                            <option></option>
                            <option value="Name" ${param.search == 'Name' ? 'selected' : ''}>Name</option>
                            <option value="Role"${param.search == 'Role' ? 'selected' : ''}>Role</option>
                            </select>
                            <button class="btn btn-primary" style="margin: 2px; color: white;background-color: #007bff" type="submit">Search</button>        
                                                                                                               
                        </div> 
                        <div class="col-md-6" style="width: 500px">
                            <div>
                                <select onchange="this.form.submit;()" name="column" id="column" class="form-select">
                                    <option value="">Sort By</option>
                                    <option ${(column=='Name')?"selected":""} value="Name">Name</option>
                                    <option ${(column=='Role')?"selected":""} value="Role">Role</option>
                                </select>
                            </div>
                            <div>
                                    <select onchange="this.form.submit()" name="sortOrder" id="sortOrder" style="margin-top: 15px" class="form-select">
                                    <option value="">Sort Order</option>
                                    <option ${(sortOrder=='asc')?"selected":""} value="asc">Ascending</option>
                                    <option ${(sortOrder=='desc')?"selected":""} value="desc" >Descending</option>
                                </select>
                            </div>
                        </div>
                                <label>Show 
                                <select name="page_size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <option value="5" ${page_size==5?"selected":""}>5</option>
                                    <option value="7" ${page_size==7?"selected":""}>7</option>
                                    <option value="10" ${page_size==10?"selected":""}>10</option>
                                    <option value="15" ${page_size==15?"selected":""}>15</option>
                                </select> 
                                entries
                            </label>
                    </form>    
                    <table class="table table-hover my-0">                       
                        <thead>
                            <tr>
                                <th>ID #</th>
                                <th>UseNameS</th>
                                <th>PasswordS</th>
                                <th>Role</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                                <th>Imgage</th>
                                <th>time</th>
                                <th>Status</th>
                                
                            </tr>   
                        </thead>
                        <tbody>
                            <c:forEach var="List" items="${list}">
                                <tr>
                                    <td># ${List.getStaffId()}</td>
                                    <td>${List.getUseNameS()}</td>
                                    <td>${List.getPasswordS()}</td>
                                    <td>${List.getRole()}</td>
                                    <td>${List.getName()}</td>
                                    <td>${List.getEmail()}</td>
                                    <td>${List.getPhone()}</td>
                                    <td>${List.getAddress()}</td>
                                    <td>${List.getImgage()}</td>
                                    <td>${List.getTime()}</td>
                                    <td>
                                        <span class="status confirmed">
                                            <i class="fas fa-circle"> ${List.getStatus()}</i>
                                        </span>
                                    </td>
                                    
                                    
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                    <div style="text-align: center; margin-top: 20px;">
                        <c:forEach begin="1" end="${totalPageCount}" var="index">
                            <a href="seeMoreController?index=${index}&searchname=${param.searchname}&search=${param.search}&column=${param.column}&sortOrder=${param.sortOrder}&page_size=${param.page_size}" style="margin: 0 5px;">${index}</a>
                        </c:forEach>
                    </div>           
                     
                
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>