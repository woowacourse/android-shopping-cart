package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType

interface ShoppingContract {
    interface View {
        fun setProducts(data: List<ProductsItemType>)
        fun navigateToProductDetail(product: ProductUIModel)
        fun updateProducts(data: List<ProductsItemType>)
        fun updateToolbar(count: Int)
    }

    interface Presenter {
        fun setUpProducts()
        fun fetchMoreProducts()
        fun navigateToItemDetail(productId: Int)
        fun updateItem(productId: Int, count: Int): Int
        fun updateProducts()
        fun updateCartProducts()
    }
}
