package woowacourse.shopping.view.productdetail

import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel

interface ProductDetailContract {
    interface View {
        var presenter: Presenter
        fun setProductDetailView(productUIModel: ProductUIModel)
        fun hideLatestProduct()
        fun showLatestProduct()
        fun showDetailProduct(productUIModel: ProductUIModel)
        fun setRecentProductView(productUIModel: ProductUIModel)
    }

    interface Presenter {
        fun showDialog(dialog: CountSelectDialog)
        fun isRecentProductExist(): Boolean
        fun setRecentProductView(product: ProductUIModel): RecentProductUIModel
        fun loadProduct(productId: Int)
    }
}
