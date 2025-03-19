<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Warranty Card</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            .chart-container {
                display: none;
                max-width: 600px;
                max-height: 400px;
                margin: 20px auto;
            }
            .button-container {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin: 20px 0;
                flex-wrap: wrap;
            }
            .btn {
                padding: 10px 15px;
                cursor: pointer;
                background: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
            }
            .btn:hover {
                background: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Report Warranty Card</h1>
                    <div class="button-container">
                        <button class="btn" onclick="showChart('statusChart')">Báo cáo theo trạng thái</button>
                        <button class="btn" onclick="showChart('productTypeChart')">Báo cáo theo loại sản phẩm</button>
                        <button class="btn" onclick="showChart('brandChart')">Báo cáo theo nhãn hiệu</button>
                        <button class="btn" onclick="showChart('repairCardChart')">Báo cáo Repair Card</button>
                    </div>

                    <div id="statusChart" class="chart-container">
                        <h3>Report Warranty Card By Status</h3>
                        <canvas id="warrantyChart"></canvas>
                    </div>

                    <div id="productTypeChart" class="chart-container">
                        <h3>Report Warranty Card By Product Type</h3>
                        <canvas id="productTypeCanvas"></canvas>
                    </div>

                    <div id="brandChart" class="chart-container">
                        <h3>Report Warranty Card By Brand</h3>
                        <canvas id="brandCanvas"></canvas>
                    </div>

                    <div id="repairCardChart" class="chart-container">
                        <h3>Report Repair Card</h3>
                        <canvas id="repairCardCanvas"></canvas>
                    </div>

                    <script>
                        let warrantyChart, productTypeChart, brandChart, repairCardChart;

                        function showChart(chartId) {
                            $(".chart-container").hide();
                            $("#" + chartId).show();

                            if (chartId === 'statusChart' && !warrantyChart)
                                loadWarrantyChart();
                            if (chartId === 'productTypeChart' && !productTypeChart)
                                loadProductTypeChart();
                            if (chartId === 'brandChart' && !brandChart)
                                loadBrandChart();
                            if (chartId === 'repairCardChart' && !repairCardChart)
                                loadRepairCardChart();
                        }

                        function loadWarrantyChart() {
                            $.getJSON("reportrepairstatus", function (data) {
                                let labels = data.map(item => item.status);
                                let values = data.map(item => item.total);

                                warrantyChart = new Chart($("#warrantyChart"), {
                                    type: "bar",
                                    data: {labels, datasets: [{label: "Số lượng", data: values, backgroundColor: "#36A2EB"}]},
                                    options: {responsive: true, scales: {y: {beginAtZero: true}}}
                                });
                            }).fail(err => console.error("Lỗi khi tải dữ liệu:", err));
                        }

                        function loadProductTypeChart() {
                            $.getJSON("reportwarrantycardbytypename", function (data) {
                                let labels = data.map(item => item.typeName || "Unknown");
                                let values = data.map(item => item.total);

                                productTypeChart = new Chart($("#productTypeCanvas"), {
                                    type: "bar",
                                    data: {labels, datasets: [{label: "Số lượng", data: values, backgroundColor: "#FF6384"}]},
                                    options: {responsive: true, scales: {y: {beginAtZero: true}}}
                                });
                            }).fail(err => console.error("Lỗi khi tải dữ liệu:", err));
                        }

                        function loadBrandChart() {
                            $.getJSON("reportwarrantybybrand", function (data) {
                                let labels = data.map(item => item.brandName);
                                let values = data.map(item => item.total);

                                brandChart = new Chart($("#brandCanvas"), {
                                    type: "bar",
                                    data: {labels, datasets: [{label: "Số lượng", data: values, backgroundColor: "#33FF57"}]},
                                    options: {responsive: true, scales: {y: {beginAtZero: true}}}
                                });
                            }).fail(err => console.error("Lỗi khi tải dữ liệu:", err));
                        }

                        function loadRepairCardChart() {
                            $.getJSON("reportrepairstatus", function (data) {
                                let labels = data.map(item => item.status);
                                let values = data.map(item => item.total);

                                repairCardChart = new Chart($("#repairCardCanvas"), {
                                    type: "bar",
                                    data: {labels, datasets: [{label: "Số lượng", data: values, backgroundColor: "#FFA500"}]},
                                    options: {responsive: true, scales: {y: {beginAtZero: true}}}
                                });
                            }).fail(err => console.error("Lỗi khi tải dữ liệu:", err));
                        }
                    </script>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
