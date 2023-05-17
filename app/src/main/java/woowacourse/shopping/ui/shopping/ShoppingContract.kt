package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.model.RecentProductUIModel

interface ShoppingContract {
    interface View {
        fun setProducts(
            products: List<ProductUIModel>,
            recentProducts: List<RecentProductUIModel>,
            cartProducts: List<CartProductUIModel>
        )
        fun addMoreProducts(products: List<ProductUIModel>)
        fun refreshProducts(
            recentProducts: List<RecentProductUIModel>,
            cartProducts: List<CartProductUIModel>
        )
        fun updateToolbar(count: Int)
        fun navigateToProductDetail(product: ProductUIModel)
    }

    interface Presenter {
        fun setUpProducts()
        fun addMoreProducts()
        fun updateProducts()
        fun updateItem(productId: Int, count: Int): Int
        fun updateToolbar()
        fun navigateToItemDetail(productId: Int)
    }
}
