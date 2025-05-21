package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product

class CartProductDTO(
    val product: Product,
    val count: Int = 0,
)
