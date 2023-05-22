package woowacourse.shopping.data.repository

val mockProducts = """
    [
    {
        "id": 1,
        "name": "PET 보틀 정사각 420",
        "url": "http://modooing.co.kr/shopimages/modooin/0010040000113.jpg?1608527683",
        "price": 14000
    },
    {
        "id": 2,
        "name": "PET 보틀 정사각 370",
        "url": "http://modooing.co.kr/shopimages/modooin/0010040000103.jpg?1608527588",
        "price": 13000
    },
    {
        "id": 3,
        "name": "PET 보틀 밀크티 370",
        "url": "http://modooing.co.kr/shopimages/modooin/0010040000093.jpg?1608527758",
        "price": 13000
    },
    {
        "id": 4,
        "name": "PET 보틀 단지 480",
        "url": "http://modooing.co.kr/shopimages/modooin/0010040000083.jpg?1608190606",
        "price": 14000
    },
    {
        "id": 5,
        "name": "PET 보틀 삼각 530",
        "url": "http://modooing.co.kr/shopimages/modooin/0010040000073.jpg?1608190510",
        "price": 15000
    },
    {
        "id": 6,
        "name": "PET 보틀 삼각 330",
        "url": "http://modooing.co.kr/shopimages/modooin/0010040000063.jpg?1608190422",
        "price": 13000
    },
    {
        "id": 7,
        "name": "PET 보틀 원형 600",
        "url": "http://modooing.co.kr/shopimages/modooin/0010040000053.jpg?1608190250",
        "price": 16000
    },
    {
        "id": 8,
        "name": "PET 보틀 원형 500",
        "url": "http://modooing.co.kr/shopimages/modooin/0010040000043.jpg?1608190202",
        "price": 15000
    },
    {
        "id": 9,
        "name": "PET 보틀 원형 300",
        "url": "http://modooing.co.kr/shopimages/modooin/0010040000033.jpg?1608190144",
        "price": 13000
    },
    {
    "id": 10,
    "name": "PET 보틀 납작 450",
    "url": "http://modooing.co.kr/shopimages/modooin/0010040000023.jpg?1608190024",
    "price": 14000
    },
    {
    "id": 11,
    "name": "PET 보틀 납작 260",
    "url": "http://modooing.co.kr/shopimages/modooin/0010040000013.jpg?1608188560",
    "price": 12000
    },
    {
        "id": 12,
        "name": "PET 원형 공캔 420",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000203.jpg?1669862090",
        "price": 14000
    },
    {
        "id": 13,
        "name": "PET 식품용 페트캔 600",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000223.jpg?1673316943",
        "price": 16000
    },
    {
        "id": 14,
        "name": "PET 식품용 페트캔 200",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000213.jpg?1673316805",
        "price": 12000
    },
    {
        "id": 15,
        "name": "AL 공캔 실버 355",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000163.jpg?1670293458",
        "price": 13000
    },
    {
        "id": 16,
        "name": "PET 캔시머용 페트캔 큐브 500",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000153.jpg?1670309150",
        "price": 15000
    },
    {
        "id": 17,
        "name": "PET 캔시머용 페트캔 항아리 500",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000143.jpg?1670314117",
        "price": 15000
    },
    {
        "id": 18,
        "name": "AL 공캔 실버 500",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000113.jpg?1606710101",
        "price": 15000
    },
    {
        "id": 19,
        "name": "PET 원형 공캔 650",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000073.jpg?1606709934",
        "price": 16000
    },
    {
        "id": 20,
        "name": "PET 크리스탈 공캔 500",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000063.jpg?1606709896",
        "price": 15000
    },
    {
        "id": 21,
        "name": "PET 원형 공캔 500",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000053.jpg?1606709846",
        "price": 15000
    },
         {
        "id": 22,
        "name": "PET 사각 공캔 350",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000033.jpg?1606709783",
        "price": 13000
    },
        {
        "id": 23,
        "name": "PET 원형 공캔 300",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000023.jpg?1606709725",
        "price": 13000
    },
    {
        "id": 24,
        "name": "AL 공캔 블랙 330",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000083.jpg?1606710038",
        "price": 13000
    },
    {
        "id": 25,
        "name": "AL 공캔 실버 330",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000093.jpg?1606709985",
        "price": 13000
    },
    {
        "id": 26,
        "name": "PET 원형 공캔 250",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0210010000013.jpg?1606710232",
        "price": 12000
    },
    {
        "id": 27,
        "name": "감자탕 용기 소 1000",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030070001073.jpg?1673252508",
        "price": 1000
    },
    {
        "id": 28,
        "name": "감자탕 용기 중 2500",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030070001063.jpg?1673252508",
        "price": 2500
    },
    {
        "id": 29,
        "name": "감자탕 용기 대 3000",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030070001053.jpg?1673252506",
        "price": 3000
    },
    {
        "id": 30,
        "name": "종이용기 무지 350",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002392.jpg?1673252436",
        "price": 3000
    },
    {
        "id": 31,
        "name": "종이용기 무지 380",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002382.jpg?1673252436",
        "price": 3000
    },
    {
        "id": 32,
        "name": "종이용기 무지 750",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002362.jpg?1673252429",
        "price": 7000
    },
    {
        "id": 33,
        "name": "종이용기 무지 1000",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002342.jpg?1673252452",
        "price": 10000
    },
    {
        "id": 34,
        "name": "종이 용기 무지 1200",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002332.jpg?1673252453",
        "price": 12000
    },
    {
        "id": 35,
        "name": "종이 용기 무지 1500",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002322.jpg?1673252442",
        "price": 15000
    },
    {
        "id": 36,
        "name": "종이 용기 아이스크림 150",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002302.jpg?1673252442",
        "price": 1000
    },
    {
        "id": 37,
        "name": "종이 용기 꽃 350",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002272.jpg?1673252436",
        "price": 3000
    },
    {
        "id": 38,
        "name": "종이 용기 블루 꽃 380",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002262.jpg?1673252436",
        "price": 3000
    },
    {
        "id": 39,
        "name": "종이 용기 별 520",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002252.jpg?1673252436",
        "price": 5000
    },
    {
        "id": 40,
        "name": "종이 용기 다이아 750",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002242.jpg?1673252437",
        "price": 7000
    },
    {
        "id": 41,
        "name": "종이 용기 동글 1000",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002222.jpg?1673252452",
        "price": 10000
    },
    {
        "id": 42,
        "name": "종이 용기 블랙 꽃 1300",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002192.jpg?1673252442",
        "price": 13000
    },
    {
        "id": 43,
        "name": "종이 용기 블랙 꽃 1500",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080002182.jpg?1673252442",
        "price": 15000
    },
    {
        "id": 44,
        "name": "직사각 2칸 도시락 검정 소",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080000772.jpg?1673252480",
        "price": 12000
    },
    {
        "id": 45,
        "name": "직사각 2칸 도시락 검정 대",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080000762.jpg?1673252480",
        "price": 14000
    },
    {
        "id": 46,
        "name": "직사각 2칸 도시락 투명 소",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080000752.jpg?1673252480",
        "price": 12000
    },
    {
        "id": 47,
        "name": "직사각 2칸 도시락 투명 대",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080000742.jpg?1673252480",
        "price": 14000
    },
    {
        "id": 48,
        "name": "정사각 3칸 도시락 검정",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080000732.jpg?1673252487",
        "price": 10000
    },
    {
        "id": 49,
        "name": "정사각 3칸 도시락 투명",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030080000722.jpg?1673252479",
        "price": 10000
    },
    {
        "id": 50,
        "name": "반찬 용기 검정",
        "image_url": "http://modooing.co.kr/shopimages/modooin/0030110000152.jpg?1671437852",
        "price": 9000
    }

    ]
    
""".trimIndent()