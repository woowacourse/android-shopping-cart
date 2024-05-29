package woowacourse.shopping.data.product

import woowacourse.shopping.model.Product

private val products: MutableMap<Long, Product> = mutableMapOf()
private var id: Long = 0L

val dummy: List<Product> =
    listOf(
        Product(
            0L,
            "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
            "마리오 그린올리브 300g",
            4500,
        ),
        Product(1L, "https://images.emarteveryday.co.kr/images/product/8801392067167/vSYMPCA3qqbLJjhv.png", "비비고 통새우 만두 200g", 5980),
        Product(
            2L,
            "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/97/12/2500000351297_1.png",
            "스테비아 방울토마토 500g",
            7890,
        ),
        Product(
            3L,
            "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/85/00/8005121000085_1.png",
            "디벨라 스파게티면 500g",
            4280,
        ),
        Product(
            4L,
            "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/00/29/8809433792900_1.png",
            "생훈제연어 150g",
            11900,
        ),
        Product(
            5L,
            "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/32/30/8801392033032_1.png",
            "CJ 고메 소바바치킨 소이허니윙 300g",
            9980,
        ),
        Product(
            6L,
            "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/65/07/8003740660765_1.jpg",
            "아리기 바질패스토 190g",
            4080,
        ),
        Product(
            7L,
            "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/77/16/8809269671677_1.png",
            "피코크 초마짬뽕 1240g",
            9980,
        ),
    )

val dummyProducts: List<Product>
    get() {
        repeat(7) { count ->
            dummy.forEach {
                save(it.imageUrl, "$count ${it.title}", it.price)
            }
        }
        return products.map { it.value }
    }

fun save(
    imageUrl: String,
    title: String,
    price: Int,
): Long {
    products[id] = Product(id, imageUrl, title, price)
    return id++
}
