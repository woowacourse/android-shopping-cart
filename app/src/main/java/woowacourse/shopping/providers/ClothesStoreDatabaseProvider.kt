package woowacourse.shopping.providers

import woowacourse.shopping.data.ClothesStoreDatabase
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductDummy
import woowacourse.shopping.data.product.ProductRepositoryImpl

object ClothesStoreDatabaseProvider {
    private lateinit var clothesStoreDatabase: ClothesStoreDatabase

    fun init(database: ClothesStoreDatabase) {
        clothesStoreDatabase = database
    }

    fun provideProductDao(): ProductDao {
        return clothesStoreDatabase.productDao()
    }

    fun provideCartDao(): CartDao {
        return clothesStoreDatabase.cartDao()
    }

    private fun setDummyProducts() {
        val productRepository = ProductRepositoryImpl(provideProductDao())
        productRepository.insertAll(*ProductDummy.products.toTypedArray())
    }
}
