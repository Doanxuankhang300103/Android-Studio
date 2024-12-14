<?php
include 'db.php'; // Kết nối cơ sở dữ liệu

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Nhận thông tin từ yêu cầu POST
    $username = isset($_POST['username']) ? trim($_POST['username']) : '';
    $password = isset($_POST['password']) ? trim($_POST['password']) : '';

    // Kiểm tra xem các trường có bị trống không
    if (empty($username) || empty($password)) {
        echo json_encode(["success" => false, "message" => "All fields are required"]);
        exit();
    }

    // Chuẩn bị câu lệnh SQL
    $stmt = $conn->prepare("SELECT id, username, password, email, role FROM users WHERE username = ?");
    $stmt->bind_param("s", $username); // "s" là kiểu dữ liệu chuỗi

    // Thực thi câu lệnh
    if ($stmt->execute()) {
        $stmt->store_result(); // Lưu trữ kết quả

        // Kiểm tra xem có hàng nào được trả về không
        if ($stmt->num_rows > 0) {
            // Liên kết các cột kết quả với biến
            $stmt->bind_result($stored_id, $stored_username, $stored_password, $stored_email, $stored_role);
            $stmt->fetch(); // Lấy kết quả

            // Kiểm tra mật khẩu (không mã hóa, so sánh trực tiếp)
            if ($password === $stored_password) {
                echo json_encode([
                    "success" => true,
                    "message" => "Login successful",
                    "user" => [
                        "id" => $stored_id,
                        "username" => $stored_username,
                        "email" => $stored_email,
                        "role" => $stored_role, // Thêm role vào phản hồi
                    ]
                ]);
            } else {
                echo json_encode([
                    "success" => false,
                    "message" => "Invalid username or password"
                ]);
            }
        } else {
            echo json_encode([
                "success" => false,
                "message" => "Invalid username or password"
            ]);
        }
    } else {
        echo json_encode([
            "success" => false,
            "message" => "Error executing query"
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

$conn->close();
?>
