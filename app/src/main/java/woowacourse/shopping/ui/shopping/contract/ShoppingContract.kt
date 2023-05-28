package woowacourse.shopping.ui.shopping.contract

import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.shopping.ProductsItemType

interface ShoppingContract {
    interface View {
        fun setProducts(data: List<ProductsItemType>)
        fun navigateToProductDetail(product: ProductUIModel, latestProduct: ProductUIModel?)

        fun addProducts(data: List<ProductsItemType>)
        fun showCountSize(size: Int)
        fun updateItem(id: Long, count: Int)
    }

    interface Presenter {
        fun setUpProducts()
        fun updateProducts()
        fun fetchMoreProducts()
        fun navigateToItemDetail(id: Long)
        fun updateItemCount(id: Long, count: Int)
        fun increaseCount(id: Long)
        fun decreaseCount(id: Long)
        fun updateCountSize()
    }
}
