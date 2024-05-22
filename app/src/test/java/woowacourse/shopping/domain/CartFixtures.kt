package woowacourse.shopping.domain

import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.entity.Product

fun cartProduct(
    product: Product = product(),
    count: Int = 1,
) = CartProduct(product, count)
