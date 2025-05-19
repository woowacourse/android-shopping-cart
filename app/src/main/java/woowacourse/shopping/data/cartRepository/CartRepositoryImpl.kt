package woowacourse.shopping.data.cartRepository

import woowacourse.shopping.data.cartRepository.CartMapper.toDomain
import woowacourse.shopping.data.cartRepository.CartMapper.toEntity
import woowacourse.shopping.data.cartRepository.CartMapper.toUiModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.uimodel.CartItem
import kotlin.concurrent.thread

class CartRepositoryImpl private constructor(
    database: CartDatabase,
) : CartRepository {
    private val cartDao = database.cartDao()

    override fun getAllProductsSize(onResult: (Int) -> Unit) {
        thread {
            val products = cartDao.getAllProducts().map { it.toDomain() }
            onResult(products.size)
        }
    }

    override fun getProducts(
        limit: Int,
        onResult: (List<CartItem>) -> Unit,
    ) {
        thread {
            val products = cartDao.getLimitProducts(limit).map { product -> product.toUiModel() }
            onResult(products)
        }
    }

    override fun addProduct(product: Product) {
        thread {
            val cartEntity = product.toEntity()
            cartDao.insert(cartEntity)
        }
    }

    override fun deleteProduct(cartItemId: Long) {
        thread {
            cartDao.deleteById(cartItemId)
        }
    }

    companion object {
        private var instance: CartRepositoryImpl? = null

        fun initialize(database: CartDatabase): CartRepositoryImpl = instance ?: CartRepositoryImpl(database).also { instance = it }
    }
}
