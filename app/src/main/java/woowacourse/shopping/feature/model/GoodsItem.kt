package woowacourse.shopping.feature.model

import woowacourse.shopping.domain.model.Cart

sealed class GoodsItem {
    data class History(
        val histories: List<Cart>,
    ) : GoodsItem()

    data class Product(
        val cart: Cart,
    ) : GoodsItem()

    object LoadMore : GoodsItem()
}
