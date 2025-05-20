package woowacourse.shopping.fixture

import woowacourse.shopping.domain.product.Product

fun getProducts(count: Int): List<Product> = (1..count).map { Product(it.toLong(), "상품 $it", 1000, "") }
