package woowacourse.shopping.fixture

import woowacourse.shopping.domain.product.Product

val PRODUCTS_COUNT_21: List<Product> = (1..21).map { Product(it.toLong(), "상품 $it", 1000, "") }

val PRODUCTS_COUNT_20: List<Product> = (1..20).map { Product(it.toLong(), "상품 $it", 1000, "") }
