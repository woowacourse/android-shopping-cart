package woowacourse.shopping.ui.mapper

import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.ui.model.UiBasketProduct

fun UiBasketProduct.toDomain(): BasketProduct =
    BasketProduct(
        id = id,
        count = count.toDomain(),
        product = product.toDomain(),
        checked = checked
    )

fun BasketProduct.toUi(): UiBasketProduct =
    UiBasketProduct(id = id, count = count.toUi(), product = product.toUi(), checked = checked)
