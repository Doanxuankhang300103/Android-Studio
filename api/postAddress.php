<?php
// Thiết lập header trả về JSON
header('Content-Type: application/json; charset=UTF-8');

// Kết nối cơ sở dữ liệu
include 'db.php'; // Đảm bảo đường dẫn đúng

// Kiểm tra phương thức yêu cầu là POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Lấy dữ liệu JSON từ yêu cầu (POST body)
    $inputData = file_get_contents("php://input");
    $data = json_decode($inputData, true);

    // Kiểm tra xem dữ liệu JSON có hợp lệ không
    if ($data === null) {
        echo json_encode(["success" => false, "message" => "Invalid JSON"]);
        exit();
    }

    // Lấy các giá trị từ dữ liệu JSON
    $user_id = isset($data['user_id']) ? trim($data['user_id']) : '';
    $name = isset($data['name']) ? trim($data['name']) : '';
    $phone = isset($data['phone']) ? trim($data['phone']) : '';
    $address = isset($data['address']) ? trim($data['address']) : '';
    $ward = isset($data['ward']) ? trim($data['ward']) : '';
    $district = isset($data['district']) ? trim($data['district']) : '';
    $city = isset($data['city']) ? trim($data['city']) : '';

    // Kiểm tra xem các trường có bị trống không
    if (empty($user_id) || empty($name) || empty($phone) || empty($address) || empty($ward) || empty($district) || empty($city)) {
        echo json_encode(["success" => false, "message" => "All fields are required"]);
        exit();
    }

    // Kiểm tra xem user_id có tồn tại trong cơ sở dữ liệu không
    $stmt = $conn->prepare("SELECT id FROM users WHERE id = ?");
    $stmt->bind_param("i", $user_id);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows === 0) {
        echo json_encode(["success" => false, "message" => "User ID does not exist"]);
        $stmt->close();
        exit();
    }

    $stmt->close();

    // Chuẩn bị câu lệnh chèn địa chỉ mới vào cơ sở dữ liệu
    // Thêm trường is_active với giá trị mặc định là 0
    $stmt = $conn->prepare("INSERT INTO address (user_id, name, phone, address, ward, district, city, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, 0)");
    $stmt->bind_param("issssss", $user_id, $name, $phone, $address, $ward, $district, $city);

    // Thực thi câu lệnh chèn
    if ($stmt->execute()) {
        // Lấy ID của bản ghi vừa thêm
        $address_id = $stmt->insert_id;

        // Truy vấn lại dữ liệu vừa thêm để trả về
        $stmt = $conn->prepare("SELECT * FROM address WHERE id = ?");
        $stmt->bind_param("i", $address_id);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            $address_data = $result->fetch_assoc();
            echo json_encode([
                "success" => true,
                "message" => "Address added successfully",
                "data" => $address_data
            ]);
        } else {
            echo json_encode(["success" => false, "message" => "Error fetching inserted address"]);
        }
    } else {
        echo json_encode(["success" => false, "message" => "Error executing query"]);
    }

    // Đóng kết nối
    $stmt->close();
} else {
    echo json_encode(["success" => false, "message" => "Invalid request method"]);
}

// Đóng kết nối cơ sở dữ liệu
$conn->close();
?>
