package woowacourse.shopping.providers

import android.util.Log
import woowacourse.shopping.data.ClothesStoreDatabase
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductDummy

object ClothesStoreDatabaseProvider {
    private lateinit var clothesStoreDatabase: ClothesStoreDatabase

    fun init(database: ClothesStoreDatabase) {
        clothesStoreDatabase = database
//        setDummyProducts()
    }

    fun provideProductDao(): ProductDao {
        return clothesStoreDatabase.productDao()
    }

    fun provideCartDao(): CartDao {
        return clothesStoreDatabase.cartDao()
    }

    fun terminate() {
        clothesStoreDatabase.close()
    }

    private fun setDummyProducts() {
        val productRepository = RepositoryProvider.provideProductOverViewRepository()
        productRepository.insertAll(*ProductDummy.products.toTypedArray()) { result ->
            result.onSuccess { Log.d("CN_Log", "더미 데이터 추가 성공") }
                .onFailure { Log.d("CN_Log", "더미 데이터 추가 실패") }
        }
    }
}
