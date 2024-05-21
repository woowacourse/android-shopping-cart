package woowacourse.shopping.uimodel

import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.uimodel.CartItemUiModel

fun ShoppingCartItem.toCartItemUiModel(): CartItemUiModel =
    CartItemUiModel(
        id = this.product.id,
        name = this.product.name,
        quantity = totalQuantity,
        totalPrice = this.totalPrice.value,
        imageUrl = this.product.imageUrl.url
    )
