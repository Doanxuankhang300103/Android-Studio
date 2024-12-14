<?php
$host = "localhost";
$user = "root";
$pass = "";
$database = "databandienthoai";

$conn = mysqli_connect($host, $user, $pass, $database);
mysqli_set_charset($conn, "utf8");

// // Kiểm tra kết nối
// if ($mysqli->connect_error) {
//     die("Kết nối thất bại: " . $mysqli->connect_error);
// }
?>