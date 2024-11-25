DROP DATABASE IF EXISTS `gaseomwohae`;
CREATE DATABASE `gaseomwohae`;
USE `gaseomwohae`;

CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `profile_image` VARCHAR(500) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `travel` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `destination` VARCHAR(100) NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `budget` INT NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `schedule` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `travel_id` BIGINT NOT NULL,
    `place_id` BIGINT NOT NULL,
    `date` DATE NOT NULL,
    `start_time` TIME NOT NULL,
    `end_time` TIME NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    CHECK (`start_time` < `end_time`)
);

CREATE TABLE `place` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `category` VARCHAR(100) NOT NULL,
    `address` VARCHAR(100) NOT NULL,
    `road_address` VARCHAR(100) NOT NULL,
    `thumbnail` VARCHAR(500) NULL,
    `phone` VARCHAR(15) NULL,
    `url` VARCHAR(500) NULL,
    `x` DECIMAL(9, 6) NOT NULL,
    `y` DECIMAL(9, 6) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `review` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `place_id` BIGINT NOT NULL,
    `rating` TINYINT NOT NULL,
    `content` TEXT NOT NULL,
    `image` VARCHAR(500) NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `invite` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `inviter_user_id` BIGINT NOT NULL,
    `invited_user_id` BIGINT NOT NULL,
    `travel_id` BIGINT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `participant` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `travel_id` BIGINT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE region (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    city VARCHAR(255),                  -- 시/도 (예: 서울특별시)
    district VARCHAR(255),              -- 구/군 (예: 종로구)
    x DECIMAL(9, 6),             -- 경도 
    y DECIMAL(9, 6)             -- 위도
);



INSERT INTO region (city, district, x, y) VALUES
('서울특별시', '종로구', 126.9794, 37.5729),
('서울특별시', '중구', 126.9970, 37.5641),
('서울특별시', '용산구', 126.9814, 37.5311),
('서울특별시', '성동구', 127.0407, 37.5509),
('서울특별시', '광진구', 127.0827, 37.5388),
('서울특별시', '동대문구', 127.0395, 37.5744),
('서울특별시', '중랑구', 127.0927, 37.6065),
('서울특별시', '성북구', 127.0273, 37.6109),
('서울특별시', '강북구', 127.0257, 37.6396),
('서울특별시', '도봉구', 127.0471, 37.6688),
('서울특별시', '노원구', 127.0778, 37.6550),
('서울특별시', '은평구', 126.9288, 37.6027),
('서울특별시', '서대문구', 126.9368, 37.5794),
('서울특별시', '마포구', 126.9018, 37.5662),
('서울특별시', '양천구', 126.8561, 37.5271),
('서울특별시', '강서구', 126.8497, 37.5509),
('서울특별시', '구로구', 126.8581, 37.4954),
('서울특별시', '금천구', 126.8956, 37.4574),
('서울특별시', '영등포구', 126.8962, 37.5265),
('서울특별시', '동작구', 126.9828, 37.4979),
('서울특별시', '관악구', 126.9436, 37.4654),
('서울특별시', '서초구', 127.0376, 37.4761),
('서울특별시', '강남구', 127.0473, 37.5184),
('서울특별시', '송파구', 127.1147, 37.5048),
('서울특별시', '강동구', 127.1465, 37.5499);


INSERT INTO region (city, district, x, y) VALUES
('부산광역시', '중구', 129.0312, 35.1068),
('부산광역시', '서구', 129.0225, 35.1159),
('부산광역시', '동구', 129.0490, 35.1299),
('부산광역시', '영도구', 129.0655, 35.0911),
('부산광역시', '부산진구', 129.0516, 35.1631),
('부산광역시', '동래구', 129.0835, 35.2048),
('부산광역시', '남구', 129.1037, 35.1324),
('부산광역시', '북구', 129.0087, 35.2164),
('부산광역시', '강서구', 128.9788, 35.2111),
('부산광역시', '해운대구', 129.1607, 35.1631),
('부산광역시', '사하구', 128.9733, 35.1042),
('부산광역시', '금정구', 129.0890, 35.2549),
('부산광역시', '연제구', 129.0814, 35.1802),
('부산광역시', '수영구', 129.1140, 35.1576),
('부산광역시', '사상구', 128.9783, 35.1627),
('부산광역시', '기장군', 129.2262, 35.3228),
('대구광역시', '중구', 128.5988, 35.8683),
('대구광역시', '동구', 128.6269, 35.8747),
('대구광역시', '서구', 128.5564, 35.8704),
('대구광역시', '남구', 128.6097, 35.8313),
('대구광역시', '북구', 128.5916, 35.8923),
('대구광역시', '수성구', 128.6336, 35.8572),
('대구광역시', '달서구', 128.5410, 35.8223),
('대구광역시', '달성군', 128.4989, 35.7989),
('대구광역시', '군위군', 128.5720, 36.2173);

INSERT INTO region (city, district, x, y) VALUES
('인천광역시', '중구', 126.5904, 37.4643),
('인천광역시', '동구', 126.6369, 37.4753),
('인천광역시', '미추홀구', 126.6883, 37.4419),
('인천광역시', '연수구', 126.6788, 37.4172),
('인천광역시', '남동구', 126.7337, 37.4020),
('인천광역시', '부평구', 126.7219, 37.5088),
('인천광역시', '계양구', 126.7271, 37.5393),
('인천광역시', '서구', 126.6766, 37.5461),
('인천광역시', '강화군', 126.5092, 37.7416),
('인천광역시', '옹진군', 126.3131, 37.4659),
('광주광역시', '동구', 126.9194, 35.1399),
('광주광역시', '서구', 126.8565, 35.1502),
('광주광역시', '남구', 126.9004, 35.1333),
('광주광역시', '북구', 126.9185, 35.1663),
('광주광역시', '광산구', 126.8118, 35.1729);

INSERT INTO region (city, district, x, y) VALUES
('대전광역시', '동구', 127.4239, 36.3515),
('대전광역시', '중구', 127.4266, 36.3256),
('대전광역시', '서구', 127.3662, 36.3503),
('대전광역시', '유성구', 127.3426, 36.3625),
('대전광역시', '대덕구', 127.4050, 36.3680),
('울산광역시', '중구', 129.3190, 35.5664),
('울산광역시', '남구', 129.3160, 35.5388),
('울산광역시', '동구', 129.4216, 35.5259),
('울산광역시', '북구', 129.3552, 35.5805),
('울산광역시', '울주군', 129.2165, 35.5745),
('세종특별자치시', '세종특별자치시', 127.2889, 36.4801);


INSERT INTO region (city, district, x, y) VALUES
('경기도', '수원시', 127.0286, 37.2636),
('경기도', '장안구', 127.1015, 37.3247),
('경기도', '권선구', 127.0276, 37.2489),
('경기도', '팔달구', 127.1794, 37.2701),
('경기도', '영통구', 127.0718, 37.2442),
('경기도', '성남시', 127.1388, 37.4232),
('경기도', '수정구', 127.1480, 37.4418),
('경기도', '중원구', 127.1536, 37.4371),
('경기도', '분당구', 127.1207, 37.3641),
('경기도', '의정부시', 127.0473, 37.7380),
('경기도', '안양시', 126.9568, 37.3943),
('경기도', '만안구', 126.9512, 37.4035),
('경기도', '동안구', 127.1310, 37.3929),
('경기도', '부천시', 126.7667, 37.5034),
('경기도', '광명시', 126.8647, 37.4782),
('경기도', '동두천시', 127.0600, 37.9018),
('경기도', '평택시', 127.0981, 36.9946),
('경기도', '안산시', 126.8290, 37.3155),
('경기도', '상록구', 126.8801, 37.2488),
('경기도', '단원구', 127.1356, 37.3209),
('경기도', '고양시', 126.8320, 37.6584),
('경기도', '덕양구', 126.8756, 37.6307),
('경기도', '일산동구', 126.7706, 37.6769),
('경기도', '일산서구', 126.7454, 37.6820),
('경기도', '과천시', 126.9893, 37.4274),
('경기도', '구리시', 127.1396, 37.6034),
('경기도', '남양주시', 127.1683, 37.6040),
('경기도', '오산시', 127.0770, 37.1499),
('경기도', '시흥시', 126.8069, 37.3807),
('경기도', '군포시', 126.9357, 37.3611),
('경기도', '의왕시', 126.9733, 37.3445),
('경기도', '하남시', 127.2179, 37.5404),
('경기도', '용인시', 127.1774, 37.2411),
('경기도', '처인구', 127.2130, 37.2349),
('경기도', '기흥구', 127.1527, 37.2742),
('경기도', '수지구', 127.0972, 37.3235),
('경기도', '파주시', 126.7759, 37.7599),
('경기도', '이천시', 127.4424, 37.2647),
('경기도', '안성시', 127.2646, 37.0050),
('경기도', '김포시', 126.7156, 37.6153),
('경기도', '화성시', 126.8312, 37.1995),
('경기도', '광주시', 127.2560, 37.4085),
('경기도', '양주시', 127.0451, 37.7854),
('경기도', '포천시', 127.2007, 37.8944),
('경기도', '여주시', 127.6281, 37.2973),
('경기도', '연천군', 127.0654, 38.0966),
('경기도', '가평군', 127.5096, 37.8317),
('경기도', '양평군', 127.4871, 37.4914);


INSERT INTO region (city, district, x, y) VALUES
('강원도', '춘천시', 127.7291, 37.8816),
('강원도', '원주시', 127.9201, 37.3418),
('강원도', '강릉시', 128.8764, 37.7510),
('강원도', '동해시', 129.1230, 37.5209),
('강원도', '태백시', 128.9850, 37.1598),
('강원도', '속초시', 128.5644, 38.2049),
('강원도', '삼척시', 129.1657, 37.4442),
('강원도', '홍천군', 127.8787, 37.6911),
('강원도', '횡성군', 127.9846, 37.4887),
('강원도', '영월군', 128.4655, 37.1849),
('강원도', '평창군', 128.3924, 37.3703),
('강원도', '정선군', 128.8183, 37.3791),
('강원도', '철원군', 127.2563, 38.2153),
('강원도', '화천군', 127.7067, 38.1052),
('강원도', '양구군', 127.9894, 38.1059),
('강원도', '인제군', 128.1710, 38.0701),
('강원도', '고성군', 128.4675, 38.3803),
('강원도', '양양군', 128.6281, 38.1061);

INSERT INTO region (city, district, x, y) VALUES
('충청북도', '청주시', 127.4898, 36.6419),
('충청북도', '상당구', 127.4889, 36.6065),
('충청북도', '흥덕구', 127.4429, 36.6583),
('충청북도', '서원구', 127.3541, 36.6416),
('충청북도', '청원구', 127.4216, 36.6358),
('충청북도', '충주시', 127.9522, 36.9705),
('충청북도', '제천시', 128.2066, 37.1316),
('충청북도', '보은군', 127.7697, 36.4886),
('충청북도', '옥천군', 127.5772, 36.3082),
('충청북도', '영동군', 127.7872, 36.1741),
('충청북도', '증평군', 127.5910, 36.7813),
('충청북도', '진천군', 127.4373, 36.8573),
('충청북도', '괴산군', 127.7952, 36.8109),
('충청북도', '음성군', 127.6893, 36.9425),
('충청북도', '단양군', 128.3572, 36.9831),
('충청남도', '천안시', 127.1469, 36.8145),
('충청남도', '동남구', 127.1516, 36.8075),
('충청남도', '서북구', 127.1120, 36.8326),
('충청남도', '공주시', 127.1205, 36.4509),
('충청남도', '보령시', 126.5849, 36.3510),
('충청남도', '아산시', 127.0039, 36.7923),
('충청남도', '서산시', 126.4521, 36.7780),
('충청남도', '논산시', 127.0986, 36.1985),
('충청남도', '계룡시', 127.2407, 36.2736),
('충청남도', '당진시', 126.6286, 36.8893),
('충청남도', '금산군', 127.4882, 36.1048),
('충청남도', '부여군', 126.9195, 36.2789),
('충청남도', '서천군', 126.6879, 36.0754),
('충청남도', '청양군', 126.7946, 36.4548),
('충청남도', '홍성군', 126.6718, 36.5973),
('충청남도', '예산군', 126.8524, 36.6827),
('충청남도', '태안군', 126.2937, 36.7487);


INSERT INTO region (city, district, x, y) VALUES
('전라북도', '전주시', 127.1489, 35.8242),
('전라북도', '완산구', 127.1335, 35.8205),
('전라북도', '덕진구', 127.1529, 35.8648),
('전라북도', '군산시', 126.7118, 35.9672),
('전라북도', '익산시', 126.9543, 35.9408),
('전라북도', '정읍시', 126.8514, 35.5662),
('전라북도', '남원시', 127.3855, 35.4173),
('전라북도', '김제시', 126.7164, 35.7996),
('전라북도', '완주군', 127.1896, 35.9192),
('전라북도', '진안군', 127.4278, 35.7970),
('전라북도', '무주군', 127.6678, 36.0117),
('전라북도', '장수군', 127.5096, 35.6465),
('전라북도', '임실군', 127.2891, 35.6071),
('전라북도', '순창군', 127.1429, 35.3723),
('전라북도', '고창군', 126.6766, 35.4355),
('전라북도', '부안군', 126.7292, 35.7319),
('전라남도', '목포시', 126.3917, 34.8120),
('전라남도', '여수시', 127.7434, 34.7519),
('전라남도', '순천시', 127.4889, 34.9507),
('전라남도', '나주시', 126.7095, 35.0150),
('전라남도', '광양시', 127.7164, 34.9409),
('전라남도', '담양군', 126.9850, 35.3200),
('전라남도', '곡성군', 127.2925, 35.2139),
('전라남도', '구례군', 127.4606, 35.2081),
('전라남도', '고흥군', 127.2794, 34.6177),
('전라남도', '보성군', 127.0903, 34.7684),
('전라남도', '화순군', 126.9940, 35.0675),
('전라남도', '장흥군', 126.9054, 34.6875),
('전라남도', '강진군', 126.7682, 34.6482),
('전라남도', '해남군', 126.5989, 34.5731),
('전라남도', '영암군', 126.7022, 34.7900),
('전라남도', '무안군', 126.4726, 34.9932),
('전라남도', '함평군', 126.5214, 35.0614),
('전라남도', '영광군', 126.5070, 35.2723),
('전라남도', '장성군', 126.7847, 35.3084),
('전라남도', '완도군', 126.7391, 34.3151),
('전라남도', '진도군', 126.2809, 34.4854),
('전라남도', '신안군', 126.2409, 34.8361);


INSERT INTO region (city, district, x, y) VALUES
('경상북도', '포항시', 129.3434, 36.0194),
('경상북도', '남구', 129.3870, 36.0277),
('경상북도', '북구', 129.3935, 36.0796),
('경상북도', '경주시', 129.2240, 35.8560),
('경상북도', '김천시', 128.1198, 36.1337),
('경상북도', '안동시', 128.7293, 36.5681),
('경상북도', '구미시', 128.3457, 36.1222),
('경상북도', '영주시', 128.6230, 36.8241),
('경상북도', '영천시', 128.9381, 35.9754),
('경상북도', '상주시', 128.1565, 36.4074),
('경상북도', '문경시', 128.1935, 36.5911),
('경상북도', '경산시', 128.7373, 35.8279),
('경상북도', '의성군', 128.6874, 36.3509),
('경상북도', '청송군', 129.0514, 36.4331),
('경상북도', '영양군', 129.1199, 36.6663),
('경상북도', '영덕군', 129.3742, 36.4187),
('경상북도', '청도군', 128.7189, 35.6516),
('경상북도', '고령군', 128.2435, 35.7159),
('경상북도', '성주군', 128.2753, 35.9192),
('경상북도', '칠곡군', 128.4077, 35.9891),
('경상북도', '예천군', 128.4405, 36.6412),
('경상북도', '봉화군', 128.7439, 36.8926),
('경상북도', '울진군', 129.4094, 37.0392),
('경상북도', '울릉군', 130.8899, 37.4835),
('경상남도', '창원시', 128.6811, 35.2372),
('경상남도', '의창구', 128.6628, 35.2286),
('경상남도', '성산구', 128.6687, 35.2191),
('경상남도', '마산합포구', 128.5700, 35.1330),
('경상남도', '마산회원구', 128.5536, 35.1804),
('경상남도', '진해구', 128.6866, 35.1414),
('경상남도', '진주시', 128.0841, 35.1998),
('경상남도', '통영시', 128.4310, 34.8497),
('경상남도', '사천시', 128.0691, 35.0035),
('경상남도', '김해시', 128.8738, 35.2279),
('경상남도', '밀양시', 128.7472, 35.4919),
('경상남도', '거제시', 128.6100, 34.8882),
('경상남도', '양산시', 129.0267, 35.3381),
('경상남도', '의령군', 128.2712, 35.3192),
('경상남도', '함안군', 128.4203, 35.2774),
('경상남도', '창녕군', 128.5007, 35.5431),
('경상남도', '고성군', 128.2337, 35.6674),
('경상남도', '남해군', 127.8947, 34.8339),
('경상남도', '하동군', 127.7556, 35.0671),
('경상남도', '산청군', 127.8785, 35.4116),
('경상남도', '함양군', 127.7369, 35.5199),
('경상남도', '거창군', 127.9192, 35.6825),
('경상남도', '합천군', 128.1679, 35.5658),
('제주도', '제주시', 126.5312, 33.4996),
('제주도', '서귀포시', 126.5093, 33.2479);

