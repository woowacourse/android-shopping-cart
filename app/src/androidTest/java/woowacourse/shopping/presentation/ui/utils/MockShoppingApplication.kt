package woowacourse.shopping.presentation.ui.utils

import android.app.Application
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repsoitory.DummyHistory
import woowacourse.shopping.data.repsoitory.DummyOrder
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailViewModelFactory
import woowacourse.shopping.presentation.ui.productlist.ProductListViewModelFactory
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartViewModelFactory

class MockShoppingApplication : Application(), ShoppingApplication {
    private val productDetailViewModelFactory by lazy {
        ProductDetailViewModelFactory(
            DummyProductList,
            DummyOrder,
            DummyHistory,
        )
    }

    private val productListViewModelFactory by lazy {
        ProductListViewModelFactory(
            DummyProductList,
            DummyOrder,
            DummyHistory,
        )
    }
    private val shoppingCartViewModelFactory by lazy {
        ShoppingCartViewModelFactory(
            DummyOrder,
        )
    }

    override fun shoppingCartViewModelFactory(): ShoppingCartViewModelFactory = shoppingCartViewModelFactory

    override fun productListViewModelFactory(): ProductListViewModelFactory = productListViewModelFactory

    override fun productDetailViewModelFactory(): ProductDetailViewModelFactory = productDetailViewModelFactory
}
