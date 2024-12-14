<?php
include "db.php";
$page = $_POST['page'];
$total = 4; // can lay 4 san pham tren 1 trang
$pos = ($page-1)*$total;   // 0,4   4,4 
$loai = $_POST['loai'];

$query = 'SELECT * FROM `products` WHERE `loai`= '.$loai.' LIMIT '.$pos.','.$total.'';
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
}
if (!empty($result)) {
	$arr = [
		'success' => true,
		'message' => "thanh cong",
		'result' => $result
	];
}else{
	$arr = [
		'success' => false,
		'message' => " khong thanh cong",
		'result' => $result
	];
}
print_r(json_encode($arr));

?>