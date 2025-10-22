console.log("swagger-custom.js loaded");

window.onload = function() {
    const hideSwaggerElements = () => {
        // Ẩn Curl và Request URL
        document.querySelectorAll('.curl-command, .request-url').forEach(el => {
            el.style.display = 'none';
        });

        // Ẩn phần Response Headers
        document.querySelectorAll('.response-headers').forEach(el => {
            el.style.display = 'none';
        });

        // (Tùy chọn) Ẩn luôn nhãn tiêu đề "Response headers" nếu có
        document.querySelectorAll('h5').forEach(el => {
            if (el.textContent.trim().toLowerCase() === 'response headers') {
                el.style.display = 'none';
            }
        });
    };

    hideSwaggerElements();

    // Theo dõi DOM, nếu Swagger load lại phần tử → ẩn tiếp
    const observer = new MutationObserver(hideSwaggerElements);
    observer.observe(document.body, { childList: true, subtree: true });
};
