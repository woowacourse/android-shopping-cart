package woowacourse.shopping.presentation.shopping.product

import woowacourse.shopping.presentation.base.CountHandler

interface ProductAction : CountHandler {
    fun onClickItem(id: Long)
    fun moreItems()
}
