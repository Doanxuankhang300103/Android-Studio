SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` mediumtext NOT NULL,
  `ward` varchar(100) NOT NULL,
  `district` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `is_active` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



INSERT INTO `address` (`id`, `user_id`, `name`, `phone`, `address`, `ward`, `district`, `city`, `is_active`) VALUES
(16, 2, 'sdfsdf', '121323', '12312', 'Xã Mỹ Hội Đông', 'Huyện Chợ Mới', 'Tỉnh An Giang', 1);



CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `status` enum('PENDING','COMPLETED') NOT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



INSERT INTO `orders` (`id`, `user_id`, `total_amount`, `status`, `payment_method`, `address_id`) VALUES
(40, 2, 27380000.00, 'PENDING', 'online', 16);


CREATE TABLE `order_items` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



INSERT INTO `order_items` (`id`, `order_id`, `product_id`, `quantity`, `price`) VALUES
(43, 40, 27, 2, 13690000.00);



CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `tensp` varchar(250) NOT NULL,
  `giasp` varchar(110) NOT NULL,
  `hinhanh` text NOT NULL,
  `mota` text NOT NULL,
  `loai` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



INSERT INTO `products` (`id`, `tensp`, `giasp`, `hinhanh`, `mota`, `loai`) VALUES
(1, 'iPhone 16 Pro Max 256GB', '32.999.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:300:0/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max-titan-tu-nhien_2.png', 'iPhone 16 mang đến nhiều cải tiến đáng chú ý về hiệu năng với chip A18 và A18 Pro, hỗ trợ xử lý, tính toán mượt mà và tiết kiệm năng lượng. Kích thước màn hình của hai phiên bản Pro và Pro Max lớn hơn, lần lượt là 6.3 inch và 6.9 inch, viền mỏng hơn, mang lại trải nghiệm hình ảnh tối ưu.', 1),
(2, 'iPhone 16 Plus 128GB', '28.998.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:300:0/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-plus-xanh-luu-ly.png', 'iPhone 16 Plus thu hút được sự chú ý của người dùng nhờ thiết kế hiện đại, sang trọng. Cùng với đó là cấu hình vượt trội từ con chip A18 cực kỳ mạnh mẽ. Sở hữu màn hình Super Retina XDR 6.7 inch hiện đại kết hợp cùng vỏ nhôm chuẩn hàng không vũ trụ tạo nên thiết kế nổi bật. Cụm camera kép cảm biến chất lượng với camera Fusion độ phân giải 48MP, camera Ultra Wide 12MP đem tới cho người dùng chất lượng sử dụng tốt hơn.', 1),
(3, 'Samsung Galaxy S24 Ultra 12GB 256GB', '24.555.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/g/a/galaxy-s24-ultra-den-1_1_3.png', 'Trải nghiệm đỉnh cao với hiệu năng mạnh mẽ từ vi xử lý tân tiến, kết hợp cùng RAM 12GB cho khả năng đa nhiệm mượt mà.\r\nLưu trữ thoải mái mọi ứng dụng, hình ảnh và video với bộ nhớ trong 256GB.\r\nThiết kế sang trọng, đẳng cấp, khẳng định phong cách thời thượng.', 1),
(4, 'Samsung Galaxy Z Flip6', '26.450.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/f/l/flip-vang.jpg', 'Samsung Z Flip 6 là chiếc điện thoại gập vỏ sò chạy chip Snapdragon 8 Gen 3 for Galaxy mạnh mẽ bậc nhất hiện nay. Máy có cụm camera kép độ phân giải 50MP sắc nét.  Màn hình ngoài Z Flip6 tăng kích thước lên 3.4 inch cho khả năng đa nhiệm vượt trội. Sản phẩm chính thức được ra mắt ngày 10/7/2024 tại sự kiện Galaxy Unpacked diễn ra ở thủ đô Paris nước Pháp.', 1),
(5, 'iPhone 14 Pro Max 1TB | Chính hãng VN/A', '23.550.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-14-pro-256gb_2.png', 'iPhone 14 Pro Max có sự cải thiện lớn màn hình so với iPhone 13 Pro Max. Sự khác biệt giữ phiên bản iPhone 14 Pro Max 1TB và bản tiêu chuẩn 128GB chỉ là bộ nhớ trong. Dưới đây là một số cải tiến nổi bật trên iPhone 14 Pro Max mà có thể bạn chưa biết!', 1),
(6, 'iPhone 15 128GB | Chính hãng VN/A\r\n', '27.899.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-15-plus_1__1.png', 'iPhone 15 128GB được trang bị màn hình Dynamic Island kích thước 6.1 inch với công nghệ hiển thị Super Retina XDR. 5 phiên bản màu sắc lựa chọn: Hồng, Vàng, Xanh lá, Xanh dương và đen. Camera trên iPhone 15 series cũng được nâng cấp lên cảm biến 48MP cùng tính năng chụp zoom quang học tới 2x. Cùng với thiết kế cổng sạc thay đổi từ lightning sang USB-C vô cùng ấn tượng.', 1),
(7, 'Điện thoại Sony Xperia 1VI 12GB 256GB\r\n', '28.199.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/i/m/image_1184_3.png', 'Sony Xperia 1 VI 12GB 256GB là flagship mới của nhà Sony với chipset Snapdragon 8 Gen 3 tiến trình 4nm, bộ nhớ RAM 12GB và dung lượng lưu trữ 256GB. Màn hình 6.5 inch chất lượng FHD+, tần số quét 120Hz cùng tấm nền OLED chất lượng. Camera chính 48MP với cảm biến Exmor T cùng camera selfie 12mP. Pin lớn 5000mAh và 30W công nghệ sạc nhanh Qi cho trải nghiệm thú vị.', 1),
(8, 'Xiaomi POCO X6 Pro 5G 8GB 256GB', '11.200.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/t/_/t_i_xu_ng_22__6_1.png', 'Điện thoại Xiaomi Poco X6 Pro được trang bị con chip Dimensity 8300 Ultra 8 nhân, tiến trình 4nm, tích hợp với GPU Mali-G615 cùng RAM 8GB, bộ nhớ trong 256GB. Máy sở hữu 3 camera sau với camera chính 64MP và 1 camera selfie 16MP. Ngoài ra máy X6 Pro được trang bị màn hình AMOLED 6.67 inch, độ phân giải 1220 x 2712 pixels cùng tần số quét 120Hz.', 1),
(9, 'OPPO Reno10 5G 8GB 256GB', '10.550.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/o/p/oppo-reno10._1.png', '- Chuyên gia chân dung thế hệ thứ 10 - Camera chân dung 32MP siêu nét, chụp xa từ 2X-5X không lo biến dạng khung hình\r\n- Thiết kế nổi bật, dẫn đầu xu hướng - Cạnh viền cong 3D, các phiên bản màu sắc phù hợp đa cá tính, thu hút mọi ánh nhìn\r\n- Pin bất tận, sạc siêu tốc - pin 5000mAh và sạc nhanh 67W cùng chế độ tiết kiệm pin siêu tiện ích', 1),
(10, 'OPPO Reno11 F 5G (8GB 256GB)\r\n', '13.280.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-oppo-reno-11-f-2.png', 'OPPO Reno11 F 5G cung cấp trải nghiệm hiển thị, xử lý siêu mượt mà với màn hình AMOLED 6.7 inch hiện đại cùng chipset MediaTek Dimensity 7050 mạnh mẽ. Hệ thống quay chụp được cải thiện hơn thông qua cụm 3 camera với độ phân giải lần lượt là 64MP, 8MP và 2MP. Ngoài ra, cung cấp năng lượng cho thế hệ OPPO smartphone là viên pin 5000mAh cùng sạc nhanh 67W, mang tới trải nghiệm liền mạch suốt ngày dài.', 1),
(11, 'OPPO Reno7 Z (5G)\r\n', '10.090.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/o/p/oppo-reno7-z_1.png', 'Điện thoại OPPO Reno 7Z 5G được ra mắt thuộc dòng Reno7 series mới đây của hãng OPPO. Vì thuộc cùng \"họ hàng\" Reno7, nên máy có ngoại hình khá giống với Reno 7 (bản Trung Quốc) trước đó, với khung viền vuông dát phẳng cùng thân hình vừa tay cầm giúp làm nên chất lượng toàn bộ thân máy. Và chất liệu nguyên khối làm nên khung viền máy cũng góp phần gia tăng độ bền cho sản phẩm.', 1),
(12, 'Điện thoại Sony Xperia 1V 12GB 256GB\r\n', '13.450.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/s/o/sony-xperia-1-v.png', 'Sony Xperia 1 V sở hữu hệ thống camera vô cùng ấn tượng với 3 cảm biến có độ phân giải 52MP, 12MP và 12MP. Mẫu điện thoại Sony này sở hữu bộ vi xử lý Snapdragon 8 Gen 2 mạnh mẽ, RAM đạt đến 12GB giúp cải tiến hiệu suất hoạt động trên máy đáng kể. Màn hình Xperia 1V trang bị tấm nền OLED 4K với kích thước 6.5 inch cùng tần số quét 120Hz.', 1),
(13, 'Xiaomi Redmi A3 (4GB 128GB)\r\n', '4.500.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/r/e/redmi-a3-den.png', 'Đắm chìm cùng Redmi A3 với những thước phim mượt mà với màn ảnh rộng 6.71 inch cùng tốc độ làm mới 90Hz.\r\nDung lượng pin 5000 mAh - Thoải mái sử dụng suốt ngày dài mà không lo hết pin.\r\nHiệu năng Xiaomi Redmi A3 mượt mà và hiệu quả với chip MediaTek Helio G36.\r\nHạn chế hao mòn với tính năng mở khóa an toàn bằng vân tay và khuôn mặt.', 1),
(14, 'Samsung Galaxy M55 (12GB 256GB)\r\n', '6.780.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-samsung-galaxy-m55.png', 'Samsung M55 là sản phẩm mới thuộc dòng Galaxy M, với màn hình 6.7 inch Super AMOLED Plus cung cấp khả năng hiển thị Full HD+ siêu thực cùng tần số quét lên đến 120Hz. Kèm theo đó là các thông số cấu hình đỉnh cao tới từ chipset Qualcomm Snapdragon 7 Gen 1. Ngoài ra Galaxy M55 còn sở hữu viên pin dung lượng 5000mAh, hỗ trợ trải nghiệm dài lâu chỉ sau 1 lần nạp pin.', 1),
(15, 'Samsung Galaxy S23 FE 5G 8GB 128GB\r\n', '14.550.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/s/a/samsung-s23-fe_1__1.png', 'Samsung S23 FE sở hữu màn hình Dynamic AMOLED 2X 6.4 inch, tần số quét 120Hz đi cùng chip Exynos 2200 8 nhân tạo độ mượt mà, thoải mái khi sử dụng. Bên cạnh đó, với mức pin 4.500 mAh, giúp người dùng có thể tha hồ đọc báo, lướt web cả ngày dài, kết hợp sạc nhanh 25W, tiết kiệm thời gian sạc. Khả năng chụp ảnh của S23 FE 5G cũng được đánh giá cao với camera chính 50MP, quay video lên đến 8K 4320p@24fps.', 1),
(16, 'iPhone 11 256GB I Chính hãng VN/A\r\n', '8.990.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/4/_/4_187_2.jpg', 'iPhone 11 256GB giá bao nhiêu hiện nay đang được quan tâm không kém bên cạnh điểm nổi bật về mặt hiệu năng, thiết kế, camera hay các tính năng khác. Vậy giá thời điểm hiện tại giảm còn bao nhiêu so với thời điểm ra mắt và nên mua không?', 1),
(17, 'iPad Pro M4 11 inch Wifi 256GB\r\n', '27.690.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/f/r/frame_100_1_2__1.png', 'Thiết kế mỏng nhẹ không tưởng. Mạnh mẽ, gọn nhẹ không ngờ.Màn hình Ultra Retina XDR với công nghệ OLED hai lớp đột phá. Chip Apple M4. Hiệu năng như mơ, đồ họa thay đổi cuộc chơi và năng lực AI mạnh mẽ. Xử lý gọn ghẽ những luồng công việc pro với iPadOS và các ứng dụng tuyệt vời.\r\n\r\n', 2),
(18, 'iPad Air 6 M2 11 inch Wifi 128GB\r\n', '16.990.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/i/p/ipad-air-6-m2-11-inch_2_.png', 'Nay có phiên bản 11 inch và 13 inch hoàn toàn mới với màn hình Liquid Retina lộng lẫy. Pin dùng cả ngày để làm việc, học tập, sáng tạo và giải trí. Siêu mạnh mẽ với chip Apple M2, mang lại hiệu năng đột phá cho CPU, đồ họa, Neural Engine và năng lực AI mạnh mẽ.', 2),
(19, 'iPad Pro 11 inch 2022 M2 Wifi + 5G 256GB I Chính hãng Apple Việt Nam\r\n', '28.490.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/ipad-pro-2022-11-inch-m2_3.png', '- Thiết kế phẳng mạnh mẽ - Gia công từ kim loại bền bỉ, phong cách hiện đại, sang trọng\r\n- Hiệu năng mạnh mẽ với CPU thế hệ mới - chip Apple M2- 8 lõi cùng RAM 8 GB\r\n- Màn hình sáng hơn, hỗ trợ nội dung HDR tốt hơn - 11 inch LCD, 600 nits\r\nThoải mái sáng tạo và thiết kế - Nhận diện bút Apple Pencil 2 siêu nhanh và nhạy.', 2),
(20, 'iPad Air 5 10.9 inch (2022) 5G 256GB', '19.990.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/3/_/3_226_3.jpg', '- Thiết kế sang trọng - Thiết kế phẳng ở 4 cạnh, màu sắc tươi trẻ.\r\n- Màn hình cho trải nghiệm chân thực - Tấm nền Retina IPS LCD 10.9 inches, chân thực và sắc nét.\r\n- Dễ dàng kết nối Magic Keyboard và Apple Pencil biến iPad của bạn thành chiếc Laptop hoàn hảo.\r\n- Khả năng xử lý tác vụ đáng kinh ngạc với Con chip M1 vô cùng mạnh mẽ.', 2),
(21, 'Xiaomi Redmi Pad Pro wifi 8GB 128GB\r\n', '6.890.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/d/i/dien-thoai-xiaomi-poco-f6_23__1.png', 'Máy tính bảng Xiaomi Redmi Pad Pro sở hữu màn hình 2,5K kích thước lên đến 12,1 inch hiển thị tốt mọi chi tiết với độ sắc nét cao. Tablet sở hữu một phần cứng mạnh với con chip Snapdragon® 7s Gen 2 cùng bộ nhớ 128GB cùng RAM 8GB. Cùng với đó thì mẫu máy tính bảng Xiaomi này còn sở hữu viên pin dung lượng lên đến 10000 mAh sử dụng vượt trội.', 2),
(22, 'Xiaomi Pad 6 6GB 128GB \r\n', '7.599.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/s/_/s_1_3_1_1.png', '- Thiết kế kim loại nguyên khối - Nhỏ gọn, sang trọng với hai gam màu hiện đại.\r\n- Snapdragon 870 - Hiệu suất cao hàng đầu trong phân khúc.\r\n- Dung lượng pin lớn 8840 mAh - Đáp ứng tốt được nhu cầu làm việc cả ngày dài.\r\n- Màn hình hiển thị sắc nét độ phân giải 2,8K; mượt mà với tốc độ làm mới 144Hz.\r\n- Tận hưởng âm thanh thực sự đắm chìm với Quad Speakers.', 2),
(23, 'Huawei MatePad Pro 13.2 inch\r\n', '8.500.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/m/a/may-tinh-bang-huawei-matepad-se-11-inchmay-tinh-bang-huawei-matepad-pro-13-2-inch_3_.png', 'Máy tính bảng Huawei MatePad Pro 13.2 inch được trang bị chip xử lý HiSilicon Kirin 9000W gồm 8 nhân CPU, RAM 12GB và bộ nhớ trong 256GB. Tablet sử dụng màn hình OLED 2880x1920 pixels, cùng độ sáng 1000 nits. Huawei MatePad Pro 13.2 inch còn trang bị pin 10100 mAh, sạc nhanh 88W cùng 2 camera 13MP + 8MP cùng camera trước 16MP.', 2),
(24, 'Huawei MatePad 11\r\n', '8.980.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/h/u/huawei-tablet-matepad-11-wi-fi-6_128-.jpg', 'Mới đây, hãng điện tử Huawei vừa cho ra mắt mẫu máy tính bảng mới mang tên Huawei MatePad 11. MatePad 11 là chiếc tablet đầu tiên của Huawei hỗ trợ tần số quét 120Hz, hứa hẹn mang lại một trải nghiệm dùng ấn tượng.', 2),
(25, 'Máy Tính Bảng Lenovo Tab M8\r\n', 'Sắp về hàng !!', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/l/e/lenovo-tab-m8.png', 'Máy tính bảng ngày càng trở nên phổ biến đối với nhiều người dùng và trở thành một thiết bị phục vụ cho nhu cầu giải trí và làm việc của người dùng. Bên cạnh những dòng máy tính bảng đắt tiền thì máy tính bảng Lenovo Tab M8 thuộc phân khúc giá rẻ cũng là một sự lựa chọn hấp dẫn, phù hợp với nhiều đối tượng người dùng.', 2),
(26, 'Máy Tính Bảng Lenovo Tab M9 4GB 64GB ZAC50137VN\r\n', '4.390.000', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/m/a/may-tinh-bang-lenovo-tab-m9-4gb-64gb_1_.png', '- Màn hình 9 inch HD cho hình ảnh sắc nét, rõ ràng và màu sắc trung thực, mang đến trải nghiệm hình ảnh tuyệt vời khi xem phim.\r\n- Bộ vi xử lý MediaTek Helio G80.\r\n- Pin dung lượng 5100mAh cho thời gian sử dụng lâu dài, thoải mái sử dụng máy tính bảng cả ngày dài mà không lo hết pin.\r\n- Thiết kế nhỏ gọn, trọng lượng nhẹ và viền màn hình mỏng cho phép bạn dễ dàng cầm nắm và mang theo bên mình.', 2),
(27, 'Samsung Galaxy Tab S9 FE Plus WIFI 8GB 128GB\r\n', '13.690.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/t/a/tab-s9-fe-plus-xanh_1.png', 'Samsung Galaxy Tab S9 FE Plus Wifi sở hữu màn hình lớn với kích thước 12.4 inches siêu ấn tượng. Trang bị Chipset Exynos 1380 mạnh mẽ, hỗ trợ xử lý tác vụ mượt mà, kết hợp với bút S-pen thế hệ mới. Bộ nhớ trong 128GB với dung lượng RAM 8GB cho bạn thỏa sức lưu trữ. ', 2),
(28, 'Samsung Galaxy Tab S9 5G 8GB 128GB\r\n', '20.000.000', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/s/s/ss-tab-s9_1__6.png', 'Samsung Galaxy Tab S9 8GB 128GB với màn hình kích thước 11 inch tấm nền Dynamic AMOLED 2X cùng bút SPEN, người dùng có thể thỏa sức học tập và sáng tạo trên màn hình. Bên cạnh đó, sản phẩm máy tính bảng Samsung này còn sở hữu một thiết kế siêu mỏng chỉ 5.9mm cùng với vi xử lý Snapdragon 8 Gen 2 thế hệ mới mang lại trải nghiệm dùng ổn định và mượt mà.', 2);


CREATE TABLE `users` (
  `id` int(11) UNSIGNED NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `role` varchar(50) NOT NULL DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;


INSERT INTO `users` (`id`, `username`, `password`, `email`, `created_at`, `role`) VALUES
(1, 'admin', '1', 'user1@example.com', '2024-09-23 03:23:03', 'admin'),
(2, 'user2', '2', 'user2@example.com', '2024-09-23 03:23:03', 'user'),
(3, 'user3', '3', 'user3@example.com', '2024-09-23 03:23:03', 'user'),
(4, 'user4', '4', 'user4@example.com', '2024-09-23 03:23:03', 'user'),
(5, 'user5', '5', 'user5@example.com', '2024-09-23 03:23:03', 'user');


ALTER TABLE `address`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);


ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_address` (`address_id`);


ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`);

ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);


ALTER TABLE `address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;


ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

ALTER TABLE `order_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;


ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;


ALTER TABLE `users`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;


ALTER TABLE `address`
  ADD CONSTRAINT `address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE `orders`
  ADD CONSTRAINT `fk_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`);


ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
