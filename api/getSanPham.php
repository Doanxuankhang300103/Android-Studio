<?php
include "db.php";
$query = "SELECT * FROM `products` ORDER BY id DESC ";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);

}

if (!empty($result)) {
    $arr = [
         'success' => true,
         'message' => 'Thanh cong roi !!',
         'result' => $result
    ];
}else {
    $arr = [
        'success' => false,
        'message' => ' KHONG Thanh cong roi ...',
        'result' => $result
   ];

}

print_r(json_encode($arr));

?>