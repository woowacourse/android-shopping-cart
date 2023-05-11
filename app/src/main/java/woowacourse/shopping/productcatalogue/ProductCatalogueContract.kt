package woowacourse.shopping.productcatalogue

import woowacourse.shopping.ProductUIModel

interface ProductCatalogueContract {
    interface View {
        var presenter: Presenter

        fun showProductDetailPage(): (ProductUIModel) -> Unit
    }

    interface Presenter {
        fun setProductOnClick()
    }
}
