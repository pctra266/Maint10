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

    // Gán sự kiện cho từng slider container
    rangeInputs.forEach(input => {
        input.addEventListener("input", updateNumberFromSlider);
        window.addEventListener("load", updateNumberFromSlider);
    });

    numberInputs.forEach(input => {
        input.addEventListener("input", updateSliderFromNumber);
        window.addEventListener("load", updateSliderFromNumber);
    });
});
