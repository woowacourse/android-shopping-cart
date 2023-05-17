package woowacourse.shopping.productcatalogue

import woowacourse.shopping.datas.ProductRepository
import woowacourse.shopping.uimodel.ProductUIModel

interface ProductCatalogueContract {
    interface View {
        fun showProductDetailPage(productUIModel: ProductUIModel)
        fun notifyDataChanged()
    }

    interface Presenter {
        fun productOnClick(): (ProductUIModel) -> Unit
        fun readMoreOnClick(): (ProductRepository, Int, Int) -> Unit
    }
}
