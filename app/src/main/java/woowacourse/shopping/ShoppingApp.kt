package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.di.NetworkModule
import woowacourse.shopping.data.di.NetworkModule.productApi
import woowacourse.shopping.domain.usecase.AddSearchHistoryUseCase
import woowacourse.shopping.domain.usecase.DecreaseCartProductQuantityUseCase
import woowacourse.shopping.domain.usecase.GetCartProductsUseCase
import woowacourse.shopping.domain.usecase.GetCatalogProductUseCase
import woowacourse.shopping.domain.usecase.GetCatalogProductsByIdsUseCase
import woowacourse.shopping.domain.usecase.GetCatalogProductsUseCase
import woowacourse.shopping.domain.usecase.GetProductDetailUseCase
import woowacourse.shopping.domain.usecase.GetRecentSearchHistoryUseCase
import woowacourse.shopping.domain.usecase.GetSearchHistoryUseCase
import woowacourse.shopping.domain.usecase.IncreaseCartProductQuantityUseCase
import woowacourse.shopping.domain.usecase.RemoveCartProductUseCase
import woowacourse.shopping.domain.usecase.UpdateCartProductUseCase

class ShoppingApp : Application() {
    private val database: ShoppingDatabase by lazy { ShoppingDatabase.getInstance(this) }

    private val cartRepository: woowacourse.shopping.domain.repository.CartRepository by lazy {
        woowacourse.shopping.data.repository
            .CartRepository(database.cartDao())
    }

    private val productRepository: woowacourse.shopping.domain.repository.ProductRepository by lazy {
        woowacourse.shopping.data.repository
            .ProductRepository(database.productDao(), productApi)
    }

    private val historyRepository: woowacourse.shopping.domain.repository.HistoryRepository by lazy {
        woowacourse.shopping.data.repository
            .HistoryRepository(database.historyDao())
    }

    val getCartProductsUseCase by lazy {
        GetCartProductsUseCase(cartRepository)
    }

    val increaseCartProductQuantityUseCase by lazy {
        IncreaseCartProductQuantityUseCase(cartRepository)
    }

    val decreaseCartProductQuantityUseCase by lazy {
        DecreaseCartProductQuantityUseCase(cartRepository)
    }

    val removeCartProductUseCase by lazy {
        RemoveCartProductUseCase(cartRepository)
    }

    val updateCartProductUseCase by lazy {
        UpdateCartProductUseCase(cartRepository)
    }

    val getSearchHistoryUseCase by lazy {
        GetSearchHistoryUseCase(historyRepository)
    }

    val addSearchHistoryUseCase by lazy {
        AddSearchHistoryUseCase(historyRepository)
    }

    val getRecentSearchHistoryUseCase by lazy {
        GetRecentSearchHistoryUseCase(historyRepository)
    }

    val getCatalogProductsUseCase by lazy {
        GetCatalogProductsUseCase(productRepository)
    }

    val getCatalogProductUseCase by lazy {
        GetCatalogProductUseCase(productRepository)
    }

    val getProductDetailUseCase by lazy {
        GetProductDetailUseCase(productRepository)
    }

    val getCatalogProductsByIdsUseCase by lazy {
        GetCatalogProductsByIdsUseCase(productRepository)
    }

    override fun onCreate() {
        super.onCreate()
        NetworkModule.init(database.productDao())
    }
}
