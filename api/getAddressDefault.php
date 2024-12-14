<?php
// Kết nối cơ sở dữ liệu
include 'db.php'; // Đảm bảo đường dẫn đúng

// Kiểm tra phương thức yêu cầu là GET
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    // Kiểm tra xem có tham số user_id không
    if (isset($_GET['user_id'])) {
        $user_id = trim($_GET['user_id']);
        
        // Kiểm tra nếu user_id trống
        if (empty($user_id)) {
            echo json_encode(["success" => false, "message" => "User ID is required"]);
            exit();
        }

        // Chuẩn bị câu lệnh truy vấn lấy dữ liệu địa chỉ theo user_id và is_active = 1
        $stmt = $conn->prepare("SELECT * FROM address WHERE user_id = ? AND is_active = 1");
        $stmt->bind_param("i", $user_id);

        // Thực thi câu lệnh truy vấn
        $stmt->execute();
        $result = $stmt->get_result();

        // Kiểm tra nếu có dữ liệu trả về
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
            echo json_encode(["success" => false, "message" => "No active addresses found for this user"]);
        }

        // Đóng kết nối
        $stmt->close();
    } else {
        echo json_encode(["success" => false, "message" => "User ID is required"]);
    }
} else {
    echo json_encode(["success" => false, "message" => "Invalid request method"]);
}

// Đóng kết nối cơ sở dữ liệu
$conn->close();
?>
