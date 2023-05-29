package woowacourse.shopping.presentation.ui.productDetail.presenter

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.presentation.ui.common.BaseView

interface ProductDetailContract {
    interface View : BaseView<Presenter> {

        fun setProduct(product: Product)
        fun setLastViewedProduct(result: WoowaResult<Product>)
    }

    interface Presenter {
        fun fetchProduct(id: Long)
        fun fetchLastViewedProduct()
        fun addRecentlyViewedProduct(id: Long, unit: Int)
        fun addProductInCart(product: Product)
    }
}
