package woowacourse.shopping.model

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product

fun CartProduct.toUiModel(isChecked: Boolean = false, product: Product): CartProductModel =
    CartProductModel(isChecked, id, product.name, product.imageUrl, count, product.price.price)

fun CartProductModel.toDomain(): CartProduct = CartProduct(id, count)
