package woowacourse.shopping.data

import com.shopping.domain.Product
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.mapper.toUIModel

object ProductMockData {
    private val products = listOf(
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
    )

    val mainProductMockData: List<ProductUIModel> =
        (products.subList(0, 19)).toUIModel()
}
