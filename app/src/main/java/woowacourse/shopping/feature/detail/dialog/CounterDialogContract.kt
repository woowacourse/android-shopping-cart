package woowacourse.shopping.feature.detail.dialog

import woowacourse.shopping.model.ProductUiModel

interface CounterDialogContract {
    interface View {
        fun setCountState(count: Int)
        fun notifyChangeApplyCount(changeApplyCount: Int)
        fun exit()
    }

    interface Presenter {
        val product: ProductUiModel
        val changeCount: Int
        fun initPresenter()
        fun changeCount(count: Int)
        fun addCart()
    }
}
