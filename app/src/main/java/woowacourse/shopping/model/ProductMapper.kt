package woowacourse.shopping.model

import woowacourse.shopping.domain.Product

fun Product.toUiModel(count: Int = 0) = ProductModel(id, name, imageUrl, price.price, count)
