package woowacourse.shopping.fixture

import woowacourse.shopping.domain.product.Product

fun getProducts(count: Int): List<Product> = (1..count).map { Product(it.toLong(), "상품 $it", 1000, "") }

val PRODUCT_LUCKY =
    Product(
        id = 1,
        name = "럭키",
        price = 4000,
        imageUrl = "https://i.namu.wiki/i/ExNTyOB5363wFnhGLSfRPOSj9G5VwSQiISkjICuIVI-8S8djFN8cJLB44Mb7jzqQMu-8OJxtuPTmE3FLkq4ebg.webp",
    )
