package woowacourse.shopping.shopping.contract

import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.shopping.ProductsItemType

interface ShoppingContract {
    interface View {
        fun setProducts(data: List<ProductsItemType>)
        fun navigateToProductDetail(product: ProductUIModel)

        fun updateProducts(start: Int, products: List<ProductsItemType>)
        fun updateRecentProducts(products: List<ProductsItemType>)
        fun setUpCarts(count: Int)
    }

    interface Presenter {
        fun setUpProducts()
        fun updateRecentProducts()
        fun fetchMoreProducts()
        fun navigateToItemDetail(data: ProductUIModel)
        fun getOffset(): Int
        fun setUpCarts()
        fun addCart(product: ProductUIModel)
        fun addOneInCart(product: ProductUIModel)
        fun removeOneInCart(product: ProductUIModel)
        fun loadCartCount(id: Int): Int
    }
}
