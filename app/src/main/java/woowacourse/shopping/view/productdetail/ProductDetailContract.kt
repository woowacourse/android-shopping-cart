package woowacourse.shopping.view.productdetail

import android.view.MenuItem
import woowacourse.shopping.model.ProductModel

interface ProductDetailContract {
    interface View {
        fun getData()
        fun startCartActivity()
        fun startRecentViewedDetail(product: ProductModel)
        fun onOptionsItemSelected(item: MenuItem): Boolean
        fun handleBackButtonClicked()
    }

    interface Presenter {
        fun setProductData(productModel: ProductModel)
        fun getProductData(): ProductModel
        fun setFlag(flag: Boolean)
        fun getFlag(): Boolean
        fun updateLatestViewedProducts()
        fun getLatestViewedProductData(): ProductModel
        fun putInCart(product: ProductModel)
        fun navigateRecentViewedDetail()
        fun navigateNextStep(itemId: Int)
        fun compareNowAndRecent()
    }
}
