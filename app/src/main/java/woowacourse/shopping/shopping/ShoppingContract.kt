package woowacourse.shopping.shopping

import woowacourse.shopping.model.ProductUiModel

interface ShoppingContract {

    interface View {

        fun setUpShoppingView(
            products: List<ProductUiModel>,
            recentViewedProducts: List<ProductUiModel>,
            showMoreShoppingProducts: () -> Unit,
        )

        fun refreshRecentViewedProductsView(
            toReplace: List<ProductUiModel>,
        )

        fun refreshMoreShoppingProductsView(
            toAdd: List<ProductUiModel>,
        )

        fun refreshShoppingProductsView(products: List<ProductUiModel>)

        fun updateToolbar(count: Int)
    }

    interface Presenter {

        fun loadProducts()

        fun addToRecentViewedProduct(id: Int)

        fun readMoreShoppingProducts()

        fun updateRecentViewedProducts()

        fun changeShoppingCartProductCount(id: Int, isAdd: Boolean)

        fun updateToolbar()

        fun refreshView()
    }
}
