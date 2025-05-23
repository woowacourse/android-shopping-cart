package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.data.entity.ShoppingCartItemEntity
import woowacourse.shopping.data.repository.ProductsRepository
import woowacourse.shopping.data.repository.RoomProductsRepositoryImpl
import woowacourse.shopping.data.repository.RoomShoppingCartRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepository

class ShoppingCartApplication : Application() {
    val productsRepository: ProductsRepository by lazy {
        RoomProductsRepositoryImpl(
            db.productDao(),
        )
    }
    val shoppingCartRepository: ShoppingCartRepository by lazy {
        RoomShoppingCartRepositoryImpl(
            db.shoppingCartDao(),
        )
    }

    val db: ShoppingCartDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            ShoppingCartDatabase::class.java,
            DATABASE_NAME,
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        DummyProducts.products.forEach {
            db.productDao().insert(
                ProductEntity(
                    name = it.name,
                    price = it.price,
                    imageUrl = it.imageUrl,
                ),
            )
        }

        DummyShoppingCart.items.forEach {
            db.shoppingCartDao().insert(
                ShoppingCartItemEntity(
                    productId = it.product.id,
                    quantity = it.quantity,
                ),
            )
        }
    }

    companion object {
        private const val DATABASE_NAME = "shopping-cart-database"
    }
}
