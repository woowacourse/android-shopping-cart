package woowacourse.shopping.shopping.contract

import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.shopping.ProductsItemType

interface ShoppingContract {
    interface View {
        fun setProducts(data: List<ProductsItemType>)
        fun navigateToProductDetail(product: ProductUIModel)

        fun addProducts(data: List<ProductsItemType>)
    }

    interface Presenter {
        fun setUpProducts()
        fun updateProducts()
        fun fetchMoreProducts()
        fun navigateToItemDetail(data: ProductUIModel)
    }
}
