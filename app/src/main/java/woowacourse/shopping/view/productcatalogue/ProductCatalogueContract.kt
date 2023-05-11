package woowacourse.shopping.view.productcatalogue

import woowacourse.shopping.uimodel.ProductUIModel

interface ProductCatalogueContract {
    interface View {
        var presenter: Presenter

        fun showProductDetailPage(): (ProductUIModel) -> Unit
    }

    interface Presenter {
        fun setProductOnClick()
    }
}
