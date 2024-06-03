package woowacourse.shopping.productdetail

import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productdetail.uimodel.CountResultUiModel

fun ShoppingCartItem.toCountResult(): CountResultUiModel = CountResultUiModel(this.totalQuantity, this.product.price)
