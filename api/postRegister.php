<?php
include 'db.php'; // Kết nối cơ sở dữ liệu

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Nhận thông tin từ yêu cầu POST
    $username = isset($_POST['username']) ? trim($_POST['username']) : '';
    $password = isset($_POST['password']) ? trim($_POST['password']) : '';
    $email = isset($_POST['email']) ? trim($_POST['email']) : '';

    // Kiểm tra xem các trường có bị trống không
    if (empty($username) || empty($password) || empty($email)) {
        echo json_encode(["success" => false, "message" => "All fields are required"]);
        exit();
    }

    // Kiểm tra xem username đã tồn tại chưa
    $stmt = $conn->prepare("SELECT * FROM users WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        echo json_encode(["success" => false, "message" => "Username already exists"]);
    } else {
        // Kiểm tra xem email đã tồn tại chưa
        $stmt = $conn->prepare("SELECT * FROM users WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            echo json_encode(["success" => false, "message" => "Email already exists"]);
        } else {
            // Chuẩn bị câu lệnh chèn người dùng mới
            $stmt = $conn->prepare("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
            $stmt->bind_param("sss", $username, $password, $email); // "sss" là kiểu dữ liệu chuỗi cho cả ba trường

            // Thực thi câu lệnh chèn
            if ($stmt->execute()) {
                echo json_encode(["success" => true, "message" => "Registration successful"]);
            } else {
                echo json_encode(["success" => false, "message" => "Error executing query"]);
            }
        }
    }

    // Đóng kết nối
    $stmt->close();
} else {
    echo json_encode(["success" => false, "message" => "Invalid request method"]);
}

$conn->close();
?>
