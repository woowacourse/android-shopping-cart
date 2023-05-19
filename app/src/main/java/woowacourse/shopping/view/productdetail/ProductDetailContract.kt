package woowacourse.shopping.view.productdetail

import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel

interface ProductDetailContract {
    interface View {
        var presenter: Presenter
        fun setProductDetailView()
        fun hideLatestProduct()
        fun showLatestProduct()
        fun showDetailProduct(recentProductUIModel: RecentProductUIModel)
    }

    interface Presenter {
        val product: ProductUIModel
        fun saveRecentProduct()
        fun showDialog(dialog: CountSelectDialog)
        fun isRecentProductsEmpty(): Boolean
        fun isRecentProductExist(): Boolean
        fun setRecentProductView(product: ProductUIModel): RecentProductUIModel
    }
}
