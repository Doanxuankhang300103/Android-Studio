<?php
include 'db.php'; // Kết nối cơ sở dữ liệu

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    // Nhận product_id từ yêu cầu GET
    $product_id = isset($_GET['product_id']) ? intval($_GET['product_id']) : 0;

    // Kiểm tra nếu product_id hợp lệ
    if ($product_id <= 0) {
        echo json_encode(["success" => false, "message" => "Invalid product ID"]);
        exit();
    }

    // Chuẩn bị câu lệnh SQL để tìm kiếm sản phẩm
    $stmt = $conn->prepare("SELECT * FROM products WHERE id = ?");
    $stmt->bind_param("i", $product_id);
    $stmt->execute();
    $result = $stmt->get_result();

    // Kiểm tra xem có sản phẩm nào được trả về không
    if ($result->num_rows > 0) {
        $product = $result->fetch_assoc(); // Lấy thông tin sản phẩm

        echo json_encode([
            "success" => true,
            "message" => "Get Product Success",
            "product" => $product
        ]);
    } else {
        echo json_encode(["success" => false, "message" => "Product not found"]);
    }

    // Đóng kết nối
    $stmt->close();
} else {
    echo json_encode(["success" => false, "message" => "Invalid request method"]);
}

$conn->close();
?>
