<?php
header('Content-Type: application/json; charset=UTF-8');

// Bao gồm tệp kết nối cơ sở dữ liệu
include 'db.php'; // Đảm bảo rằng tệp db.php chứa kết nối cơ sở dữ liệu

// Kiểm tra phương thức PUT
if ($_SERVER['REQUEST_METHOD'] === 'PUT') {
    // Lấy dữ liệu từ PUT request
    $data = json_decode(file_get_contents('php://input'), true);

    // Kiểm tra xem dữ liệu có đầy đủ không
    if (!isset($data['id']) || !isset($data['tensp']) || !isset($data['giasp']) || !isset($data['hinhanh']) || !isset($data['mota']) || !isset($data['loai'])) {
        echo json_encode(["success" => false, "message" => "Missing required fields"]);
        exit();
    }

    // Gán dữ liệu vào các biến
    $id = $data['id'];
    $tensp = $data['tensp'];
    $giasp = $data['giasp'];
    $hinhanh = $data['hinhanh'];
    $mota = $data['mota'];
    $loai = $data['loai'];

    // Câu lệnh SQL để cập nhật sản phẩm
    $stmt = $conn->prepare("UPDATE products SET tensp = ?, giasp = ?, hinhanh = ?, mota = ?, loai = ? WHERE id = ?");
    $stmt->bind_param("ssssii", $tensp, $giasp, $hinhanh, $mota, $loai, $id); // gán tham số vào câu lệnh

    // Thực thi câu lệnh SQL
    if ($stmt->execute()) {
        // Trả về dữ liệu đã sửa
        echo json_encode([
            "success" => true,
            "message" => "Product updated successfully",
            "product" => [
                "id" => $id,
                "tensp" => $tensp,
                "giasp" => $giasp,
                "hinhanh" => $hinhanh,
                "mota" => $mota,
                "loai" => $loai
            ]
        ]);
    } else {
        echo json_encode([
            "success" => false,
            "message" => "Failed to update product"
        ]);
    }

    // Đóng kết nối
    $stmt->close();
} else {
    echo json_encode([
        "success" => false,
        "message" => "Invalid request method"
    ]);
}

// Đóng kết nối
$conn->close();
?>
