package woowacourse.shopping.shopping.contract

import woowacourse.shopping.shopping.ProductsItemType

interface ShoppingContract {
    interface View {
        fun setProducts(data: List<ProductsItemType>)
        fun navigateToProductDetail(data: ProductsItemType)

        fun addProducts(data: List<ProductsItemType>)
    }

    interface Presenter {
        fun setUpProducts()
        fun fetchMoreProducts()
        fun navigateToItemDetail(data: ProductsItemType)
    }
}
