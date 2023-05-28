package woowacourse.shopping.shopping

import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel
import woowacourse.shopping.shopping.navigator.ShoppingNavigator

interface ShoppingContract {

    interface View {

        val shoppingNavigator: ShoppingNavigator

        fun setUpShoppingView(
            products: List<CartProductUiModel>,
            recentViewedProducts: List<RecentViewedProductUiModel>
        )

        fun refreshRecentViewedProductsView(
            products: List<RecentViewedProductUiModel>,
        )

        fun refreshShoppingProductsView(products: List<CartProductUiModel>)

        fun showMoreProducts(toAdd: List<CartProductUiModel>)

        fun refreshProductCount(count: Int)
    }

    interface Presenter {

        fun setUpProducts()

        fun loadProducts()

        fun loadCartProductsCount()

        fun loadProductDetail(productId: Int)

        fun addToRecentViewedProduct(id: Int)

        fun readMoreShoppingProducts()

        fun plusShoppingCartProductCount(product: CartProductUiModel)

        fun minusShoppingCartProductCount(product: CartProductUiModel)

        fun addProductToShoppingCart(product: CartProductUiModel)
    }
}
