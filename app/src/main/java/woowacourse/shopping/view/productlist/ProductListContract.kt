package woowacourse.shopping.view.productlist

import android.view.MenuItem
import woowacourse.shopping.model.ProductModel

interface ProductListContract {
    interface View {
        fun showProducts(recentViewedProducts: List<ProductModel>, products: List<ProductModel>)
        fun notifyAddProducts(position: Int, size: Int)
        fun onOptionsItemSelected(item: MenuItem): Boolean
        fun handleCartMenuClicked()
    }

    interface Presenter {
        fun fetchProducts()
        fun showMoreProducts()
        fun handleNextStep(itemId: Int)
    }
}
