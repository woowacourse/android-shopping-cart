package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.datasource.history.LocalHistoryDataSource
import woowacourse.shopping.data.datasource.order.LocalOrderDataSource
import woowacourse.shopping.data.db.AppDatabase
import woowacourse.shopping.data.repsoitory.DefaultHistoryRepository
import woowacourse.shopping.data.repsoitory.DefaultOrderRepository
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailViewModelFactory
import woowacourse.shopping.presentation.ui.productlist.ProductListViewModelFactory
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartViewModelFactory

class DefaultShoppingApplication : Application(), ShoppingApplication {
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val localHistoryDataSource by lazy { LocalHistoryDataSource(db.historyDao()) }
    private val localOrderDataSource by lazy { LocalOrderDataSource(db.orderDao()) }

    private val productListViewModelFactory by lazy {
        val localHistoryDataSource = localHistoryDataSource
        val localOrderDataSource = localOrderDataSource
        ProductListViewModelFactory(
            DummyProductList,
            DefaultOrderRepository(localOrderDataSource),
            DefaultHistoryRepository(localHistoryDataSource),
        )
    }
    private val productDetailViewModelFactory by lazy {
        val historyRepository = DefaultHistoryRepository(localHistoryDataSource)
        val orderRepository = DefaultOrderRepository(localOrderDataSource)
        ProductDetailViewModelFactory(
            DummyProductList,
            orderRepository,
            historyRepository,
        )
    }
    private val shoppingCartViewModelFactory by lazy {
        ShoppingCartViewModelFactory(
            DefaultOrderRepository(localOrderDataSource),
        )
    }

    override fun productListViewModelFactory(): ProductListViewModelFactory = productListViewModelFactory

    override fun productDetailViewModelFactory(): ProductDetailViewModelFactory = productDetailViewModelFactory

    override fun shoppingCartViewModelFactory(): ShoppingCartViewModelFactory = shoppingCartViewModelFactory
}
