package woowacourse.shopping.shoppingcart

import woowacourse.shopping.domain.ShoppingCartItem

fun ShoppingCartItem.toCartItemUiModel(): CartItemUiModel =
    CartItemUiModel(
        id = this.product.id,
        name = this.product.name,
        quantity = totalQuantity,
        totalPrice = this.totalPrice.value,
        imageUrl = this.product.imageUrl.url
    )
