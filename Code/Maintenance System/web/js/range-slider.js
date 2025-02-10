document.querySelectorAll(".slider-container").forEach(container => {
    const rangeInputs = container.querySelectorAll(".range-input input"),
            numberInputs = container.querySelectorAll(".price-input input"),
            progressBar = container.querySelector(".slider .progress"),
            dataType = container.dataset.type;

    let step = dataType === "price" ? 0.01 : 1; // Bước nhảy khác nhau cho price và quantity
    let minValue, maxValue;

    // Cập nhật giá trị từ input number sang range slider
    const updateSliderFromNumber = (e) => {
        minValue = parseFloat(numberInputs[0].value);
        maxValue = parseFloat(numberInputs[1].value);

        if ((maxValue - minValue >= step) && maxValue <= rangeInputs[1].max) {
            if (e.target.classList.contains("input-min")) {
                rangeInputs[0].value = minValue;
                progressBar.style.left = ((minValue / rangeInputs[0].max) * 100) + "%";
            } else {
                rangeInputs[1].value = maxValue;
                progressBar.style.right = 100 - (maxValue / rangeInputs[1].max) * 100 + "%";
            }
        }
    };

    // Cập nhật giá trị từ range slider sang input number
    const updateNumberFromSlider = (e) => {
        minValue = parseFloat(rangeInputs[0].value);
        maxValue = parseFloat(rangeInputs[1].value);

        if ((maxValue - minValue) < step) {
            if (e.target.classList.contains("range-min")) {
                rangeInputs[0].value = maxValue - step;
            } else {
                rangeInputs[1].value = minValue + step;
            }
        } else {
            numberInputs[0].value = minValue;
            numberInputs[1].value = maxValue;
            progressBar.style.left = ((minValue / rangeInputs[0].max) * 100) + "%";
            progressBar.style.right = 100 - (maxValue / rangeInputs[1].max) * 100 + "%";
        }
    };


    const formatInput = (e) => {

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
              document.querySelectorAll(".format-int").forEach(function (input) {
            if (input.value !== "") {
                input.value = input.value.replace(/\B(?=(\d{3})+(?!\d))/g, " ");
            }
        });

    };

    // Gán sự kiện cho từng slider container
    rangeInputs.forEach(input => {
        input.addEventListener("input", updateNumberFromSlider);
        input.addEventListener("input", formatInput);
        window.addEventListener("load", updateNumberFromSlider);
        window.addEventListener("load", formatInput);
    });

    numberInputs.forEach(input => {
        input.addEventListener("input", updateSliderFromNumber);
        window.addEventListener("load", updateSliderFromNumber);
    });
});
