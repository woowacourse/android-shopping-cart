package woowacourse.shopping.datas

import com.shopping.domain.Price
import com.shopping.domain.Product
import com.shopping.domain.ProductRepository

object ProductRepositoryImpl : ProductRepository {
    private val baseProducts = listOf(
        Product(
            0,
            "http://modooing.co.kr/shopimages/modooin/0010040000113.jpg?1608527683",
            "PET 보틀 정사각 420",
            Price(14_000)
        ),
        Product(
            1,
            "http://modooing.co.kr/shopimages/modooin/0010040000103.jpg?1608527588",
            "PET 보틀 정사각 370",
            Price(13_000)
        ),
        Product(
            2,
            "http://modooing.co.kr/shopimages/modooin/0010040000093.jpg?1608527758",
            "PET 보틀 밀크티 370",
            Price(13_000)
        ),
        Product(
            3,
            "http://modooing.co.kr/shopimages/modooin/0010040000083.jpg?1608190606",
            "PET 보틀 단지 480",
            Price(14_000)
        ),
        Product(
            4,
            "http://modooing.co.kr/shopimages/modooin/0010040000073.jpg?1608190510",
            "PET 보틀 삼각 530",
            Price(15_000)
        ),
        Product(
            5,
            "http://modooing.co.kr/shopimages/modooin/0010040000063.jpg?1608190422",
            "PET 보틀 삼각 330",
            Price(13_000)
        ),
        Product(
            6,
            "http://modooing.co.kr/shopimages/modooin/0010040000053.jpg?1608190250",
            "PET 보틀 원형 600",
            Price(16_000)
        ),
        Product(
            7,
            "http://modooing.co.kr/shopimages/modooin/0010040000043.jpg?1608190202",
            "PET 보틀 원형 500",
            Price(15_000)
        ),
        Product(
            8,
            "http://modooing.co.kr/shopimages/modooin/0010040000033.jpg?1608190144",
            "PET 보틀 원형 300",
            Price(13_000)
        ),
        Product(
            9,
            "http://modooing.co.kr/shopimages/modooin/0010040000023.jpg?1608190024",
            "PET 보틀 납작 450",
            Price(14_000)
        ),
        Product(
            10,
            "http://modooing.co.kr/shopimages/modooin/0010040000013.jpg?1608188560",
            "PET 보틀 납작 260",
            Price(12_000)
        ),
        Product(
            11,
            "http://modooing.co.kr/shopimages/modooin/0210010000203.jpg?1669862090",
            "PET 원형 공캔 420",
            Price(14_000)
        ),
        Product(
            12,
            "http://modooing.co.kr/shopimages/modooin/0210010000223.jpg?1673316943",
            "PET 식품용 페트캔 600",
            Price(16_000)
        ),
        Product(
            13,
            "http://modooing.co.kr/shopimages/modooin/0210010000213.jpg?1673316805",
            "PET 식품용 페트캔 200",
            Price(12_000)
        ),
        Product(
            14,
            "http://modooing.co.kr/shopimages/modooin/0210010000163.jpg?1670293458",
            "AL 공캔 실버 355",
            Price(13_000)
        ),
        Product(
            15,
            "http://modooing.co.kr/shopimages/modooin/0210010000153.jpg?1670309150",
            "PET 캔시머용 페트캔 큐브 500",
            Price(15_000)
        ),
        Product(
            16,
            "http://modooing.co.kr/shopimages/modooin/0210010000143.jpg?1670314117",
            "PET 캔시머용 페트캔 항아리 500",
            Price(15_000)
        ),
        Product(
            17,
            "http://modooing.co.kr/shopimages/modooin/0210010000113.jpg?1606710101",
            "AL 공캔 실버 500",
            Price(15_000)
        ),
        Product(
            18,
            "http://modooing.co.kr/shopimages/modooin/0210010000073.jpg?1606709934",
            "PET 원형 공캔 650",
            Price(16_000)
        ),
        Product(
            19,
            "http://modooing.co.kr/shopimages/modooin/0210010000063.jpg?1606709896",
            "PET 크리스탈 공캔 500",
            Price(15_000)
        ),
    )
    private val _products = mutableListOf<Product>()
    val products: List<Product>
        get() = _products.toList()

    var productCataloguePageNumber = 1
        get() = ++field
        private set

    override fun getUnitData(unitSize: Int, pageNumber: Int): List<Product> {
        val tempUnit = mutableListOf<Product>()

        for (index in unitSize * (pageNumber - 1) until unitSize * pageNumber) {
            val originalIndex = index % unitSize
            tempUnit.add(
                Product(
                    id = index,
                    imageUrl = baseProducts[originalIndex].imageUrl,
                    name = baseProducts[originalIndex].name + "-$index",
                    price = Price(baseProducts[originalIndex].price.value),
                )
            )
        }
        _products.addAll(tempUnit)
        return tempUnit.toList()
    }
}
