package woowacourse.shopping.shoppingcart

import woowacourse.shopping.domain.ShoppingCartItem

fun ShoppingCartItem.toShoppingCartUiModel(): CartItemUiModel =
    CartItemUiModel(
        this.product.id,
        this.product.name,
        this.product.price.value,
        this.product.imageUrl.url,
        this.totalQuantity,
    )
