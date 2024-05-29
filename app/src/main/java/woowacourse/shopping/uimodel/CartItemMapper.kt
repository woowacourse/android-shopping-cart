package woowacourse.shopping.uimodel

import woowacourse.shopping.domain.ShoppingCartItem

fun ShoppingCartItem.toCartItemUiModel(): CartItemUiModel =
    CartItemUiModel(
        id = this.product.id,
        name = this.product.name,
        quantity = quantity,
        totalPrice = this.totalPrice.value,
        imageUrl = this.product.imageUrl.url
    )
