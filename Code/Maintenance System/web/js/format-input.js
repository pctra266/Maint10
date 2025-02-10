document.addEventListener("DOMContentLoaded", function () {
    // Định dạng ngay khi trang tải xong
    document.querySelectorAll(".format-int").forEach(function (input) {
        if (input.value !== "") {
            input.value = input.value.replace(/\B(?=(\d{3})+(?!\d))/g, " ");
        }
    });

    document.querySelectorAll(".format-float").forEach(function (input) {
        if (input.value !== "") {
            let parts = input.value.replace(/\s/g, "").replace(".", ",").split(",");
            let integerPart = parts[0].replace(/\D/g, "");
            if (integerPart !== "") {
                integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, " ");
            }
            input.value = parts.length > 1 ? integerPart + "," + parts[1].replace(/\D/g, "") : integerPart;
        }
    });

    // Xử lý nhập liệu cho input số nguyên
    document.querySelectorAll(".format-int").forEach(function (input) {
        input.addEventListener("input", function (event) {
            let value = event.target.value.replace(/[^\d]/g, ""); // Chỉ giữ số
            event.target.value = value ? value.replace(/\B(?=(\d{3})+(?!\d))/g, " ") : "";
        });

        input.addEventListener("blur", function () {
            this.dataset.rawValue = this.value.replace(/\s/g, "");
        });
    });

    // Xử lý nhập liệu cho input số thực
    document.querySelectorAll(".format-float").forEach(function (input) {
        input.addEventListener("input", function (event) {
            let value = event.target.value.replace(/[^0-9,]/g, ""); // Chỉ giữ số và dấu `,`
            if (value.startsWith(",")) value = "0" + value;

            let parts = value.split(",");
            if (parts.length > 2) {
                value = parts[0] + "," + parts.slice(1).join("").replace(/,/g, "");
            }

            let integerPart = parts[0].replace(/\D/g, "");
            if (integerPart !== "") {
                integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, " ");
            }

            event.target.value = parts.length > 1 ? integerPart + "," + parts[1].replace(/\D/g, "") : integerPart;
        });

        input.addEventListener("blur", function () {
            this.dataset.rawValue = this.value.replace(/\s/g, "").replace(",", ".");
        });
    });

    // Xóa khoảng trắng trước khi submit form
    document.querySelectorAll("form").forEach(function (form) {
        form.addEventListener("submit", function () {
            form.querySelectorAll(".format-int, .format-float").forEach(function (input) {
                input.dataset.rawValue = input.value.replace(/\s/g, "").replace(",", ".");
                input.value = input.dataset.rawValue;
            });
        });
    });
});
