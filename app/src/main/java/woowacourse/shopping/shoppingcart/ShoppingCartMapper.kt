package woowacourse.shopping.shoppingcart

import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.shoppingcart.uimodel.CartItemUiModel

fun ShoppingCartItem.toShoppingCartUiModel(): CartItemUiModel =
    CartItemUiModel(
        this.product.id,
        this.product.name,
        this.product.price.value,
        this.product.imageUrl.url,
        this.totalQuantity,
    )
