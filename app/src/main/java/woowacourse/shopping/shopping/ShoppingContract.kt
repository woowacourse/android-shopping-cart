package woowacourse.shopping.shopping

import model.RecentViewedProducts
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel

interface ShoppingContract {

    interface View {

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

        fun navigateToProductDetailView(
            product: ProductUiModel,
            latestViewedProduct: ProductUiModel?,
        )
    }

    interface Presenter {

        val recentViewedProducts: RecentViewedProducts

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
