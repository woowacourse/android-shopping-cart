package woowacourse.shopping.view.productdetail

import android.view.MenuItem
import woowacourse.shopping.model.ProductModel

interface ProductDetailContract {
    interface View {
        fun getData()
        fun startCartActivity()
        fun onOptionsItemSelected(item: MenuItem): Boolean
        fun handleBackButtonClicked()
        fun showProductRecentViewedDetail(product: ProductModel)
    }

    interface Presenter {
        fun putInCart(product: ProductModel)
        fun updateRecentViewedProducts()
        fun handleNextStep(itemId: Int)
        fun setProductData(productModel: ProductModel)
        fun getProductData(): ProductModel
        fun getRecentViewedProductData(): ProductModel
        fun navigateRecentViewedDetail()
    }
}
