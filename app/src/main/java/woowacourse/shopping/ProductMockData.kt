package woowacourse.shopping

import com.shopping.domain.MainProductCatalogue
import com.shopping.domain.Product
import com.shopping.domain.RecentProductCatalogue
import woowacourse.shopping.mapper.toUIModel

object ProductMockData {
    val products = listOf(
        Product("http://modooing.co.kr/shopimages/modooin/0010040000113.jpg?1608527683", "PET 보틀 정사각 420", 14_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000103.jpg?1608527588", "PET 보틀 정사각 370", 13_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000093.jpg?1608527758", "PET 보틀 밀크티 370", 13_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000083.jpg?1608190606", "PET 보틀 단지 480", 14_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000073.jpg?1608190510", "PET 보틀 삼각 530", 15_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000063.jpg?1608190422", "PET 보틀 삼각 330", 13_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000053.jpg?1608190250", "PET 보틀 원형 600", 16_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000043.jpg?1608190202", "PET 보틀 원형 500", 15_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000033.jpg?1608190144", "PET 보틀 원형 300", 13_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000023.jpg?1608190024", "PET 보틀 납작 450", 14_000),
        Product("http://modooing.co.kr/shopimages/modooin/0010040000013.jpg?1608188560", "PET 보틀 납작 260", 12_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000203.jpg?1669862090", "PET 원형 공캔 420", 14_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000223.jpg?1673316943", "PET 식품용 페트캔 600", 16_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000213.jpg?1673316805", "PET 식품용 페트캔 200", 12_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000163.jpg?1670293458", "AL 공캔 실버 355", 13_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000153.jpg?1670309150", "PET 캔시머용 페트캔 큐브 500", 15_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000143.jpg?1670314117", "PET 캔시머용 페트캔 항아리 500", 15_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000113.jpg?1606710101", "AL 공캔 실버 500", 15_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000073.jpg?1606709934", "PET 원형 공캔 650", 16_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000063.jpg?1606709896", "PET 크리스탈 공캔 500", 15_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000053.jpg?1606709846", "PET 원형 공캔 500", 15_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000033.jpg?1606709783", "PET 사각 공캔 350", 13_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000023.jpg?1606709725", "PET 원형 공캔 300", 13_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000083.jpg?1606710038", "AL 공캔 블랙 330", 13_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000093.jpg?1606709985", "AL 공캔 실버 330", 13_000),
        Product("http://modooing.co.kr/shopimages/modooin/0210010000013.jpg?1606710232", "PET 원형 공캔 250", 12_000),
        Product("http://modooing.co.kr/shopimages/modooin/0030070001073.jpg?1673252508", "감자탕 용기 소 1000", 1_000),
        Product("http://modooing.co.kr/shopimages/modooin/0030070001063.jpg?1673252508", "감자탕 용기 중 2500", 2_500),
        Product("http://modooing.co.kr/shopimages/modooin/0030070001053.jpg?1673252506", "감자탕 용기 대 3000", 3_000),
    )

    val mainProductMockData = MainProductCatalogue(
        products.subList(0, 19)
    ).toUIModel()

    val recentProductMockData = RecentProductCatalogue(
        MainProductCatalogue(products.subList(0, 9))
    ).toUIModel()
}
