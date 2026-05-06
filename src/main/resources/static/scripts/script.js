/**
 * Hàm xóa dùng chung cho cả Job và các đối tượng khác
 * @param {string} url - Đường dẫn API (ví dụ: /api/jobs/1)
 */
function deleteItem(url) {
    if (confirm("Bạn có chắc chắn muốn xóa không? Thao tác này không thể hoàn tác.") === true) {
        // Hiển thị trạng thái đang xử lý (tùy chọn)
        fetch(url, {
            method: 'DELETE', // Viết hoa DELETE cho đúng chuẩn HTTP
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.status === 204) {
                // Xóa thành công, tải lại trang
                location.reload();
            } else if (res.status === 403) {
                alert("Bạn không có quyền thực hiện thao tác này!");
            } else {
                alert("Hệ thống có lỗi! Vui lòng quay lại sau.");
            }
        }).catch(err => {
            console.error("Lỗi kết nối:", err);
            alert("Không thể kết nối đến máy chủ!");
        });
    }
}