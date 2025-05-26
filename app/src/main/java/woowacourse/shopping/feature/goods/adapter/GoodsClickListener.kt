package woowacourse.shopping.feature.goods.adapter

import woowacourse.shopping.domain.model.Cart

interface GoodsClickListener {
    fun onClickGoods(cart: Cart)

    fun onClickHistory(cart: Cart)

    fun insertToCart(cart: Cart)

    fun removeFromCart(cart: Cart)

    fun loadMore()
}
