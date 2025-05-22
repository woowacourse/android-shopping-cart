package woowacourse.shopping.data.shoppingCart.entity

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.entity.toEntity
import woowacourse.shopping.domain.shoppingCart.ShoppingCartProduct

data class ShoppingCartProductEntity(
    val product: ProductEntity,
    val quantity: Int,
)

fun ShoppingCartProduct.toEntity(): ShoppingCartProductEntity =
    ShoppingCartProductEntity(
        product = product.toEntity(),
        quantity = quantity,
    )
