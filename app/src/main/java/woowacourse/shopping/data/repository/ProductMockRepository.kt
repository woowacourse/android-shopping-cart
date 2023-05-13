package woowacourse.shopping.data.repository

import com.shopping.domain.Product
import com.shopping.repository.ProductRepository

object ProductMockRepository : ProductRepository {
    private val mockProducts = listOf(
        Product(
            id = 1,
            name = "PET 보틀 정사각 420",
            url = "http://modooing.co.kr/shopimages/modooin/0010040000113.jpg?1608527683",
            price = 14_000
        ),
        Product(
            2,
            "PET 보틀 정사각 370",
            "http://modooing.co.kr/shopimages/modooin/0010040000103.jpg?1608527588",
            13_000
        ),
        Product(
            3,
            "PET 보틀 밀크티 370",
            "http://modooing.co.kr/shopimages/modooin/0010040000093.jpg?1608527758",
            13_000
        ),
        Product(
            4,
            "PET 보틀 단지 480",
            "http://modooing.co.kr/shopimages/modooin/0010040000083.jpg?1608190606",
            14_000
        ),
        Product(
            5,
            "PET 보틀 삼각 530",
            "http://modooing.co.kr/shopimages/modooin/0010040000073.jpg?1608190510",
            15_000
        ),
        Product(
            6,
            "PET 보틀 삼각 330",
            "http://modooing.co.kr/shopimages/modooin/0010040000063.jpg?1608190422",
            13_000
        ),
        Product(
            7,
            "PET 보틀 원형 600",
            "http://modooing.co.kr/shopimages/modooin/0010040000053.jpg?1608190250",
            16_000
        ),
        Product(
            8,
            "PET 보틀 원형 500",
            "http://modooing.co.kr/shopimages/modooin/0010040000043.jpg?1608190202",
            15_000
        ),
        Product(
            9,
            "PET 보틀 원형 300",
            "http://modooing.co.kr/shopimages/modooin/0010040000033.jpg?1608190144",
            13_000
        ),
        Product(
            10,
            "PET 보틀 납작 450",
            "http://modooing.co.kr/shopimages/modooin/0010040000023.jpg?1608190024",
            14_000
        ),
        Product(
            11,
            "PET 보틀 납작 260",
            "http://modooing.co.kr/shopimages/modooin/0010040000013.jpg?1608188560",
            12_000
        ),
        Product(
            12,
            "PET 원형 공캔 420",
            "http://modooing.co.kr/shopimages/modooin/0210010000203.jpg?1669862090",
            14_000
        ),
        Product(
            13,
            "PET 식품용 페트캔 600",
            "http://modooing.co.kr/shopimages/modooin/0210010000223.jpg?1673316943",
            16_000
        ),
        Product(
            14,
            "PET 식품용 페트캔 200",
            "http://modooing.co.kr/shopimages/modooin/0210010000213.jpg?1673316805",
            12_000
        ),
        Product(
            15,
            "AL 공캔 실버 355",
            "http://modooing.co.kr/shopimages/modooin/0210010000163.jpg?1670293458",
            13_000
        ),
        Product(
            16,
            "PET 캔시머용 페트캔 큐브 500",
            "http://modooing.co.kr/shopimages/modooin/0210010000153.jpg?1670309150",
            15_000
        ),
        Product(
            17,
            "PET 캔시머용 페트캔 항아리 500",
            "http://modooing.co.kr/shopimages/modooin/0210010000143.jpg?1670314117",
            15_000
        ),
        Product(
            18,
            "AL 공캔 실버 500",
            "http://modooing.co.kr/shopimages/modooin/0210010000113.jpg?1606710101",
            15_000
        ),
        Product(
            19,
            "PET 원형 공캔 650",
            "http://modooing.co.kr/shopimages/modooin/0210010000073.jpg?1606709934",
            16_000
        ),
        Product(
            20,
            "PET 크리스탈 공캔 500",
            "http://modooing.co.kr/shopimages/modooin/0210010000063.jpg?1606709896",
            15_000
        ),
        Product(
            21,
            "PET 원형 공캔 500",
            "http://modooing.co.kr/shopimages/modooin/0210010000053.jpg?1606709846",
            15_000
        ),
        Product(
            22,
            "PET 사각 공캔 350",
            "http://modooing.co.kr/shopimages/modooin/0210010000033.jpg?1606709783",
            13_000
        ),
        Product(
            23,
            "PET 원형 공캔 300",
            "http://modooing.co.kr/shopimages/modooin/0210010000023.jpg?1606709725",
            13_000
        ),
        Product(
            24,
            "AL 공캔 블랙 330",
            "http://modooing.co.kr/shopimages/modooin/0210010000083.jpg?1606710038",
            13_000
        ),
        Product(
            25,
            "AL 공캔 실버 330",
            "http://modooing.co.kr/shopimages/modooin/0210010000093.jpg?1606709985",
            13_000
        ),
        Product(
            26,
            "PET 원형 공캔 250",
            "http://modooing.co.kr/shopimages/modooin/0210010000013.jpg?1606710232",
            12_000
        ),
        Product(
            27,
            "감자탕 용기 소 1000",
            "http://modooing.co.kr/shopimages/modooin/0030070001073.jpg?1673252508",
            1_000
        ),
        Product(
            28,
            "감자탕 용기 중 2500",
            "http://modooing.co.kr/shopimages/modooin/0030070001063.jpg?1673252508",
            2_500
        ),
        Product(
            29,
            "감자탕 용기 대 3000",
            "http://modooing.co.kr/shopimages/modooin/0030070001053.jpg?1673252506",
            3_000
        ),
        Product(
            30,
            "종이용기 무지 350",
            "http://modooing.co.kr/shopimages/modooin/0030080002392.jpg?1673252436",
            3_000
        ),
        Product(
            31,
            "종이용기 무지 380",
            "http://modooing.co.kr/shopimages/modooin/0030080002382.jpg?1673252436",
            3_000
        ),
        Product(
            32,
            "종이용기 무지 750",
            "http://modooing.co.kr/shopimages/modooin/0030080002362.jpg?1673252429",
            7_000
        ),
        Product(
            33,
            "종이용기 무지 1000",
            "http://modooing.co.kr/shopimages/modooin/0030080002342.jpg?1673252452",
            10_000
        ),
        Product(
            34,
            "종이 용기 무지 1200",
            "http://modooing.co.kr/shopimages/modooin/0030080002332.jpg?1673252453",
            12_000
        ),
        Product(
            35,
            "종이 용기 무지 1500",
            "http://modooing.co.kr/shopimages/modooin/0030080002322.jpg?1673252442",
            15_000
        ),
        Product(
            36,
            "종이 용기 아이스크림 150",
            "http://modooing.co.kr/shopimages/modooin/0030080002302.jpg?1673252442",
            1_000
        ),
        Product(
            37,
            "종이 용기 꽃 350",
            "http://modooing.co.kr/shopimages/modooin/0030080002272.jpg?1673252436",
            3_000
        ),
        Product(
            38,
            "종이 용기 블루 꽃 380",
            "http://modooing.co.kr/shopimages/modooin/0030080002262.jpg?1673252436",
            3_000
        ),
        Product(
            39,
            "종이 용기 별 520",
            "http://modooing.co.kr/shopimages/modooin/0030080002252.jpg?1673252436",
            5_000
        ),
        Product(
            40,
            "종이 용기 다이아 750",
            "http://modooing.co.kr/shopimages/modooin/0030080002242.jpg?1673252437",
            7_000
        ),
        Product(
            41,
            "종이 용기 동글 1000",
            "http://modooing.co.kr/shopimages/modooin/0030080002222.jpg?1673252452",
            10_000
        ),
        Product(
            42,
            "종이 용기 블랙 꽃 1300",
            "http://modooing.co.kr/shopimages/modooin/0030080002192.jpg?1673252442",
            13_000
        ),
        Product(
            43,
            "종이 용기 블랙 꽃 1500",
            "http://modooing.co.kr/shopimages/modooin/0030080002182.jpg?1673252442",
            15_000
        ),
        Product(
            44,
            "직사각 2칸 도시락 검정 소",
            "http://modooing.co.kr/shopimages/modooin/0030080000772.jpg?1673252480",
            12_000
        ),
        Product(
            45,
            "직사각 2칸 도시락 검정 대",
            "http://modooing.co.kr/shopimages/modooin/0030080000762.jpg?1673252480",
            14_000
        ),
        Product(
            46,
            "직사각 2칸 도시락 투명 소",
            "http://modooing.co.kr/shopimages/modooin/0030080000752.jpg?1673252480",
            12_000

        ),
        Product(
            47,
            "직사각 2칸 도시락 투명 대",
            "http://modooing.co.kr/shopimages/modooin/0030080000742.jpg?1673252480",
            14_000
        ),
        Product(
            48,
            "정사각 3칸 도시락 검정",
            "http://modooing.co.kr/shopimages/modooin/0030080000732.jpg?1673252487",
            10_000
        ),
        Product(
            49,
            "정사각 3칸 도시락 투명",
            "http://modooing.co.kr/shopimages/modooin/0030080000722.jpg?1673252479",
            10_000
        ),
        Product(
            50,
            "반찬 용기 검정",
            "http://modooing.co.kr/shopimages/modooin/0030110000152.jpg?1671437852",
            9_000
        )
    )

    override val products: List<Product> = mockProducts.subList(0, 20)
}
