package woowacourse.shopping.presentation.goods.list

import woowacourse.shopping.domain.model.ShoppingCartItem

fun interface GoodsClickListener {
    fun onGoodsClick(item: ShoppingCartItem)
}
