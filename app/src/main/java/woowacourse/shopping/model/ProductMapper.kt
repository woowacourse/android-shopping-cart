package woowacourse.shopping.model

import woowacourse.shopping.domain.Product

fun Product.toUiModel() = ProductModel(id, name, imageUrl, price.price)
