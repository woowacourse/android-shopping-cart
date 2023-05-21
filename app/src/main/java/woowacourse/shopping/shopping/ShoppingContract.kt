package woowacourse.shopping.shopping

import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel
import woowacourse.shopping.shopping.navigator.ShoppingNavigator

interface ShoppingContract {

    interface View {

        val shoppingNavigator: ShoppingNavigator

        fun setUpShoppingView(
            products: List<ProductUiModel>,
            recentViewedProducts: List<RecentViewedProductUiModel>,
        )

        fun refreshRecentViewedProductsView(
            products: List<RecentViewedProductUiModel>,
        )

        fun refreshShoppingProductsView(
            toAdd: List<ProductUiModel>,
        )

        fun refreshProductCount(count: Int)
    }

    interface Presenter {

        fun loadProducts()

        fun loadCartProductsCount()

        fun loadProductDetail(product: ProductUiModel)

        fun addToRecentViewedProduct(id: Int)

        fun readMoreShoppingProducts()

        fun plusShoppingCartProductCount(product: ProductUiModel)

        fun minusShoppingCartProductCount(product: ProductUiModel)

        fun addProductToShoppingCart(product: ProductUiModel)
    }
}
