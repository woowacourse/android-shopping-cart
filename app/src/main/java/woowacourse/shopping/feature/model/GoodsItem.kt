package woowacourse.shopping.feature.model

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.History

sealed class GoodsItem {
    data class Recent(
        val histories: List<History>,
    ) : GoodsItem()

    data class Product(
        val cart: Cart,
    ) : GoodsItem()

    object LoadMore : GoodsItem()
}
