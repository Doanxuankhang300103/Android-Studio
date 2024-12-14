<?php
include 'db.php'; // Kết nối cơ sở dữ liệu

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    // Lấy user_id từ tham số URL
    if (isset($_GET['user_id'])) {
        $user_id = $_GET['user_id'];

        // Kiểm tra xem user_id có tồn tại không
        $stmt = $conn->prepare("SELECT id, name, phone, address, ward, district, city, is_active FROM address WHERE user_id = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $result = $stmt->get_result();

        // Kiểm tra nếu có kết quả
        if ($result->num_rows > 0) {
            $addresses = [];
            while ($row = $result->fetch_assoc()) {
                $addresses[] = $row;
            }
            echo json_encode([
                "success" => true,
                "message" => "Addresses retrieved successfully",
                "data" => $addresses
            ]);
        } else {
            echo json_encode([
                "success" => false,
                "message" => "No addresses found for the user",
                "data" => []
            ]);
        }

        $stmt->close();
    } else {
        echo json_encode([
            "success" => false,
            "message" => "user_id parameter is missing",
            "data" => []
        ]);
    }
} else {
    echo json_encode([
        "status" => false,
        "message" => "Invalid request method",
        "data" => []
    ]);
}

$conn->close();
?>
