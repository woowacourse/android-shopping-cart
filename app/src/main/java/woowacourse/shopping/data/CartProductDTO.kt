package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product

class CartProductDTO(
    val product: Product,
    var count: Int = 0,
)
