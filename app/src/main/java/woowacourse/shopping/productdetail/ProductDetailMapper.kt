package woowacourse.shopping.productdetail

import woowacourse.shopping.domain.ShoppingCartItem

fun ShoppingCartItem.toCountResult(): CountResultUiModel = CountResultUiModel(this.totalQuantity, this.product.price)
