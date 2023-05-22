package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.Product
import woowacourse.shopping.util.SafeLiveData

interface ProductDetailContract {
    interface Presenter {
        val productInfo: SafeLiveData<CartProductInfo>
        val mostRecentProduct: SafeLiveData<Product>
        fun saveProductInRepository(count: Int)
        fun updateProductCount(count: Int)
        fun saveRecentProduct()
        fun checkCurrentProductIsMostRecent()
    }

    interface View {
        fun showCompleteMessage(productName: String)
        fun hideMostRecentProduct()
    }
}
