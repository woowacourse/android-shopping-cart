package woowacourse.shopping.feature.product.detail.dialog

import woowacourse.shopping.feature.list.item.ProductView.CartProductItem

interface CounterDialogContract {
    interface View {
        val presenter: Presenter

        fun setCountState(count: Int)
        fun exit()
    }

    interface Presenter {
        val product: CartProductItem
        val count: Int

        fun loadInitialData()
        fun updateCount(changeWidth: Int)
        fun addCart()
    }
}
