package woowacourse.shopping

import woowacourse.shopping.presentation.ui.productdetail.ProductDetailViewModelFactory
import woowacourse.shopping.presentation.ui.productlist.ProductListViewModelFactory
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartViewModelFactory

interface ShoppingApplication {
    fun shoppingCartViewModelFactory(): ShoppingCartViewModelFactory

    fun productListViewModelFactory(): ProductListViewModelFactory

    fun productDetailViewModelFactory(): ProductDetailViewModelFactory
}
