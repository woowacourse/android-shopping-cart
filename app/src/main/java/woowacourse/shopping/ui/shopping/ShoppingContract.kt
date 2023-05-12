package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType

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
