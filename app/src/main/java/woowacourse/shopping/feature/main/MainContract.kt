package woowacourse.shopping.feature.main

import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.list.item.ProductView.RecentProductsItem

interface MainContract {

    interface View {
        val presenter: Presenter

        fun setInitialProducts(products: List<CartProductItem>, recentProducts: RecentProductsItem)
        fun setProduct(product: CartProductItem)
        fun addProducts(products: List<CartProductItem>)
        fun startActivity(product: CartProductItem, lastProduct: CartProductItem?)
    }

    interface Presenter {
        fun loadInitialData()

        fun saveRecentProduct(product: CartProductItem)
        fun loadMoreProducts()
        fun updateProductCount(product: CartProductItem, isPlus: Boolean)
        fun addProduct(product: CartProductItem)
    }
}
