<?php
header('Content-Type: application/json; charset=UTF-8');

// Kết nối cơ sở dữ liệu
include 'db.php'; // Đảm bảo file này chứa kết nối đến cơ sở dữ liệu

// Kiểm tra phương thức request
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Nhận dữ liệu từ yêu cầu POST
    $inputData = file_get_contents("php://input");
    $data = json_decode($inputData, true);

    // Kiểm tra dữ liệu đầu vào
    if (
        empty($data['tensp']) ||
        empty($data['giasp']) ||
        empty($data['loai'])
    ) {
        echo json_encode([
            "success" => false,
            "message" => "Missing required fields"
        ]);
        exit();
    }

    // Gán các giá trị từ dữ liệu nhận được
    $tensp = trim($data['tensp']);
    $giasp = trim($data['giasp']);
    $hinhanh = isset($data['hinhanh']) ? trim($data['hinhanh']) : null;
    $mota = isset($data['mota']) ? trim($data['mota']) : null;
    $loai = intval($data['loai']);

    // Chuẩn bị câu lệnh SQL
    $stmt = $conn->prepare("INSERT INTO products (tensp, giasp, hinhanh, mota, loai) VALUES (?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssi", $tensp, $giasp, $hinhanh, $mota, $loai);

    // Thực thi câu lệnh SQL
    if ($stmt->execute()) {
        echo json_encode([
            "success" => true,
            "message" => "Product added successfully",
            "product" => [
                "id" => $stmt->insert_id,
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
            "message" => "Failed to add product"
        ]);
    }

    // Đóng câu lệnh
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
