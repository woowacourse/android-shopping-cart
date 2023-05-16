package woowacourse.shopping.feature.detail

import woowacourse.shopping.model.ProductUiModel

interface DetailContract {
    interface View {
        fun showCartScreen()
    }

    interface Presenter {
        val product: ProductUiModel
        fun addCart()
    }
}
