package woowacourse.shopping.model

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product

fun CartProduct.toUiModel(product: Product): CartProductModel =
    CartProductModel(id, product.name, product.imageUrl, count, product.price.price * count)
