package woowacourse.shopping.feature.goods.adapter

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.History

interface GoodsClickListener {
    fun onClickGoods(cart: Cart)

    fun onClickHistory(history: History)

    fun insertToCart(cart: Cart)

    fun removeFromCart(cart: Cart)

    fun loadMore()
}
