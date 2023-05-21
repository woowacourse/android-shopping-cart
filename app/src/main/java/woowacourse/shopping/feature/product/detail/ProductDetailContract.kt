package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.feature.model.ProductState

interface ProductDetailContract {
    interface View {
        val presenter: ProductDetailPresenter
    }

    interface Presenter {
        fun addColumn(product: ProductState)
    }
}
