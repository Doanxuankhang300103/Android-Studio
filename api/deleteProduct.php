<?php
include 'db.php'; // Kết nối cơ sở dữ liệu

if ($_SERVER['REQUEST_METHOD'] === 'DELETE') {
    // Lấy id từ query string
    if (isset($_GET['id'])) {
        $id = $_GET['id'];

        // Chuẩn bị câu lệnh SQL để xóa sản phẩm
        $stmt = $conn->prepare("DELETE FROM products WHERE id = ?");
        $stmt->bind_param("i", $id);

        if ($stmt->execute()) {
            // Kiểm tra xem sản phẩm có tồn tại trước khi xóa không
            if ($stmt->affected_rows > 0) {
                echo json_encode([
                    "success" => true,
                    "message" => "Product deleted successfully",
                    "product_id" => $id // Trả về ID sản phẩm đã xóa
                ]);
            } else {
                echo json_encode([
                    "success" => false,
                    "message" => "Product not found"
                ]);
            }
        } else {
            echo json_encode([
                "success" => false,
                "message" => "Error deleting product"
            ]);
        }
        $stmt->close();
    } else {
        echo json_encode([
            "success" => false,
            "message" => "Product ID is required"
        ]);
    }

    $conn->close();
} else {
    echo json_encode([
        "success" => false,
        "message" => "Invalid request method"
    ]);
}
?>
