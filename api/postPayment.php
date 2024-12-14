<?php
// Thiết lập header để trả về JSON
header('Content-Type: application/json; charset=UTF-8');

// Include file kết nối cơ sở dữ liệu
include 'db.php'; // Đảm bảo đường dẫn đúng

// Lấy dữ liệu JSON từ yêu cầu (POST body)
$inputData = file_get_contents("php://input");
$data = json_decode($inputData, true);
error_log(print_r($data, true));

// Kiểm tra xem dữ liệu JSON có hợp lệ không
if ($data === null) {
    echo json_encode([
        "success" => false,
        "message" => "Invalid JSON"
    ]);
    exit;
}

// Lấy các giá trị từ JSON
$userId = $data['user_id'];
$status = $data['status'];
$paymentMethod = $data['payment_method']; // Thêm trường phương thức thanh toán
$orderItems = $data['order_item'];
$addressID = $data['address_id'];

// Kiểm tra phương thức thanh toán hợp lệ
$validPaymentMethods = ['online', 'cash_on_delivery'];
if (!in_array($paymentMethod, $validPaymentMethods)) {
    echo json_encode([
        "success" => false,
        "message" => "Invalid payment method. Allowed methods: online, cash_on_delivery."
    ]);
    exit;
}

// Tính tổng số tiền từ order_item
$totalAmount = 0;
foreach ($orderItems as $item) {
    $totalAmount += $item['price'] * $item['quantity'];
}

// Bắt đầu giao dịch
mysqli_begin_transaction($conn);

try {
    // Thêm đơn hàng vào bảng 'orders' với phương thức thanh toán
    $stmt = mysqli_prepare($conn, "INSERT INTO orders (user_id, total_amount, status, payment_method, address_id) VALUES (?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($stmt, "iidss", $userId, $totalAmount, $status, $paymentMethod, $addressID);
    // mysqli_stmt_bind_param($stmt, "iidis", $userId, $totalAmount, $status, $paymentMethod, $addressID);
    if (!mysqli_stmt_execute($stmt)) {
        echo json_encode([
            "success" => false,
            "message" => "Error executing query: " . mysqli_error($conn)
        ]);
        exit;
    }

    // Lấy ID của đơn hàng vừa tạo
    $orderId = mysqli_insert_id($conn);

    // Thêm các mục order_item vào bảng 'order_items' với order_id là ID của đơn hàng vừa tạo
    foreach ($orderItems as $item) {
        $stmt = mysqli_prepare($conn, "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)");
        mysqli_stmt_bind_param($stmt, "iiid", $orderId, $item['product_id'], $item['quantity'], $item['price']);
        mysqli_stmt_execute($stmt);
    }

    // Xác nhận giao dịch
    mysqli_commit($conn);

    // Trả về kết quả với định dạng mong muốn
    echo json_encode([
        "success" => true,
        "message" => "Order created successfully",
        "order" => [
            "user_id" => $userId,
            "total_amount" => $totalAmount, // Sử dụng total_amount đã tính toán
            "status" => $status,
            "payment_method" => $paymentMethod, // Phương thức thanh toán
            "order_item" => $orderItems, // Danh sách mục đã thêm
            "address_id" => $addressID // Địa chỉ giao hàng
        ]
    ]);
} catch (Exception $e) {
    // Rollback nếu có lỗi
    mysqli_rollback($conn);
    
    echo json_encode([
        "success" => false,
        "message" => "Failed to insert data: " . $e->getMessage()
    ]);
}

// Đóng kết nối
mysqli_close($conn);
?>
