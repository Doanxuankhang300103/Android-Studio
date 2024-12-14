<?php
// Thiết lập header để trả về JSON
header('Content-Type: application/json; charset=UTF-8');

// Include file kết nối cơ sở dữ liệu
include 'db.php'; // Đảm bảo đường dẫn đúng

// Lấy dữ liệu JSON từ yêu cầu (POST body)
$inputData = file_get_contents("php://input");
$data = json_decode($inputData, true);

// Kiểm tra xem dữ liệu JSON có hợp lệ không
if ($data === null) {
    echo json_encode([
        "success" => false,
        "message" => "Invalid JSON"
    ]);
    exit;
}

// Lấy ID địa chỉ từ dữ liệu JSON
$selectedId = $data['id'];

// Kiểm tra xem ID có hợp lệ không
if (!isset($selectedId)) {
    echo json_encode([
        "success" => false,
        "message" => "Address ID is required"
    ]);
    exit;
}

try {
    // Bắt đầu giao dịch
    mysqli_begin_transaction($conn);

    // Cập nhật tất cả các địa chỉ thành `is_active = 0`
    $stmt = mysqli_prepare($conn, "UPDATE address SET is_active = 0 WHERE is_active = 1");
    mysqli_stmt_execute($stmt);

    // Cập nhật địa chỉ được chọn thành `is_active = 1`
    $stmt = mysqli_prepare($conn, "UPDATE address SET is_active = 1 WHERE id = ?");
    mysqli_stmt_bind_param($stmt, "i", $selectedId);
    mysqli_stmt_execute($stmt);

    // Lấy thông tin của địa chỉ vừa được cập nhật
    $stmt = mysqli_prepare($conn, "SELECT * FROM address WHERE id = ?");
    mysqli_stmt_bind_param($stmt, "i", $selectedId);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);
    $updatedAddress = mysqli_fetch_assoc($result);

    // Xác nhận giao dịch
    mysqli_commit($conn);

    // Trả về thông tin địa chỉ đã được cập nhật
    echo json_encode([
        "success" => true,
        "message" => "Address updated successfully",
        "address" => $updatedAddress
    ]);
} catch (Exception $e) {
    // Rollback nếu có lỗi
    mysqli_rollback($conn);

    echo json_encode([
        "success" => false,
        "message" => "Error updating address: " . $e->getMessage()
    ]);
}

// Đóng kết nối
mysqli_close($conn);
?>
