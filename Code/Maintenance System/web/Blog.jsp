<%-- 
    Document   : Blog
    Created on : Mar 17, 2025, 8:39:06 AM
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
        <style>
            body {
            font-family: 'Poppins', sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 0;
            color: #333;
        }
        .wrapper {
            display: flex;
            min-height: 100vh;
        }
        .left-frame {
            flex: 0 0 25%;
            padding: 20px;
            background: #fff;
            border-right: 1px solid #ddd;
        }
        .right-frame {
            flex: 0 0 75%;
            padding: 20px;
            background: #fff;
            margin-top: -80px;
        }
        h2 {
            font-size: 24px;
            font-weight: 600;
            color: #444;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .alert {
                padding: 10px;
                background-color: #009926; 
                color: white;
                margin-bottom: 15px;
                border-radius: 5px;
                display: block; 
            }
        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }
        button:hover {
            background-color: #0056b3;
        }
        .blog-post-detail {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            background: #fff;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .blog-title {
            font-size: 28px;
            font-weight: 600;
            margin-bottom: 10px;
        }
        .blog-meta {
            font-size: 14px;
            color: #777;
            margin-bottom: 20px;
        }
        .blog-content {
            font-size: 16px;
            line-height: 1.6;
            color: #333;
        }
        .inputTitle, .textareaContent {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            transition: background 0.3s;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        
        /* Tổng thể form */
        .form-container {
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
            margin: 15px 0;
        }

        /* Input và Select */
        .input-group, .form-select {
            transition: all 0.3s ease-in-out;
            border-radius: 8px;
        }

        .form-select:focus, 
        .form-control:focus {
            box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
            border-color: #007bff;
        }



        

    </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <main class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <c:if test="${not empty list}">
                    <form class="row g-3 align-items-center form-container" action="BlogController" method="get">
                            <!-- Search Section -->
                            <div class="col-md-6">
                                <div class="input-group shadow-sm">
                                    <input class="form-control" type="text" name="searchname" placeholder="🔍 Search..." value="${param.searchname}">
                                    <select id="search" class="form-select shadow-sm" name="search" required>
                                        <option value="Name" ${param.search == 'Name' ? 'selected' : ''}>Name</option>
                                    </select>
                                    <button class="btn btn-primary" type="submit">Search</button>
                                </div>
                            </div>

                            <!-- Sort Section -->
                            

                            <div class="col-md-3">
                                <select onchange="this.form.submit()" name="sortOrder" id="sortOrder" class="form-select shadow-sm">
                                    <option value="">Sort Order</option>
                                    <option value="asc" ${sortOrder=='asc' ? 'selected' : ''}>Ascending</option>
                                    <option value="desc" ${sortOrder=='desc' ? 'selected' : ''}>Descending</option>
                                </select>
                            </div>

                            <!-- Page Size -->
                            <div class="col-auto d-flex align-items-center">
                                <label class="form-label fw-bold me-2">Show</label>
                                <select name="page_size" class="form-select form-select-sm shadow-sm" onchange="this.form.submit()">
                                    <option value="5" ${page_size==5 ? "selected" : ""}>5</option>
                                    <option value="7" ${page_size==7 ? "selected" : ""}>7</option>
                                    <option value="10" ${page_size==10 ? "selected" : ""}>10</option>
                                    <option value="15" ${page_size==15 ? "selected" : ""}>15</option>
                                </select>
                                <label class="form-label ms-2">entries</label>
                            </div>
                        </form>  
                    </c:if>
                    <div class="left-frame blog-frame ">
                        <h2>Danh sách bài viết của Nhân Viên</h2>
                        <c:if test="${not empty Create}">
                            
                            <form action="BlogController" method="post">
                                <input type="hidden" name="action" value="Create">
                                <button type="submit">Create</button>
                            </form>
                        </c:if>
                        <c:if test="${not empty info or not empty mes}">
                            <form action="BlogController" method="get">
                                <button type="submit">Back</button>
                            </form>
                        </c:if>
                          
                        <c:if test="${not empty update}">
                            <div class="alert" style="margin-top: 20px">${update}</div>
                        </c:if>
                        
                
                        
                        <table>
                            <thead>
                                <c:if test="${not empty list}">
                                    <tr>
                                        <th>Tiltle</th>
                                        <th>Author</th>
                                        <th>Created</th>
                                        <th>Info</th>
                                    </tr>
                                 </c:if>
                            </thead>
                            <tbody>
                                <c:if test="${not empty list}">
                                    <c:forEach var="List" items="${list}">
                                        <tr>
                                            <td>${List.getTitle()}</td>                                                                                                            
                                            <td>${List.getStaff()}</td>
                                            <td>${List.getCreatedDate()}</td>
                                            <td>
                                                <form action="BlogController" method="post">
                                                    <input type="hidden" name="action" value="More">
                                                    <input type="hidden" name="blogID" value="${List.getBlogPostId()}">
                                                    <button type="submit">More</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>

                            </tbody>
                        </table>
                    </div>
                <c:forEach var="Info" items="${info}">
                    <div class="right-frame">
                        <div class="blog-post-detail">
                                    <h1 class="blog-title">${Info.getTitle()}</h1>

                                    <div class="blog-meta">
                                        <span class="blog-created">CreatedDate: ${Info.getCreatedDate()}</span>
                                        <c:choose>
                                            <c:when test="${not empty Info.getUpdatedDate()}">
                                                <span class="blog-updated">UpdatedDate: ${Info.getUpdatedDate()}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="blog-updated">UpdatedDate: Null</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="blog-content">
                                        <pre>${Info.getContent()}</pre>
                                    </div>

                        </div>  
                    <c:if test="${not empty info}">
                        <c:if test="${staffProfile.staffID == change}">
                            <form action="BlogController" method="post">
                                <input type="hidden" name="blogID" value="${Info.getBlogPostId()}">
                                <input type="hidden" name="action" value="Change">
                                <button type="submit">change</button>
                            </form> 
                        </c:if>
                    </c:if>

                    </div>
                </c:forEach> 
                <c:forEach var="Info" items="${changeBlog}">
                    <div class="right-frame">
                        <form action="BlogController" method="post">
                            <div class="blog-post-detail">

                                <h1 class="blog-title">${Info.getTitle()}</h1>
                                <div class="blog-meta">
                                    <span class="blog-created">CreatedDate: ${Info.getCreatedDate()}</span>
                                    <c:choose>
                                        <c:when test="${not empty Info.getUpdatedDate()}">
                                            <span class="blog-updated">UpdatedDate: ${Info.getUpdatedDate()}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="blog-updated">UpdatedDate: Null</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <textarea name="content" rows="5" cols="50">${Info.getContent()}</textarea>                          
                                <input type="hidden" name="blogID" value="${Info.getBlogPostId()}">
                                <input type="hidden" name="action" value="Save">   
                                <button type="submit" >Save</button>
                        </form> 
                            </div>
                            
                    </div>
                </c:forEach>
                
                <c:if test="${not empty mes}">
                    <form action="BlogController" method="post">
                        <input type="hidden" name="action" value="SubmitCreate">

                        <input class="inputTitle" type="text" id="title" name="title" placeholder="Nhập tiêu đề bài viết" required>

                        <textarea class="textareaContent" id="content" name="content" rows="6" placeholder="Nhập nội dung bài viết..." required></textarea>


                        <button type="submit">Save</button>
                    </form>
                </c:if>
                <c:if test="${not empty totalPageCount and totalPageCount != 0 and not empty list}">
                    <jsp:include page="/includes/pagination.jsp" />
                </c:if>                      
                    
            </main>
             <jsp:include page="/includes/footer.jsp" />

        </div>
        <script src="js/app.js"></script>
    </body>
</html>
