package woowacourse.shopping.data.storage

import woowacourse.shopping.data.cartRepository.CartDao
import woowacourse.shopping.data.cartRepository.CartMapper.toEntity
import woowacourse.shopping.data.cartRepository.CartMapper.toUiModel
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class CartStorageImpl private constructor(
    private val cartDao: CartDao,
) : CartStorage {
    override fun getAllProductsSize(onResult: (Int) -> Unit) {
        thread {
            onResult(cartDao.getAllProductsSize())
        }
    }

    override fun getProducts(
        limit: Int,
        offset: Int,
        onResult: (List<CartItem>) -> Unit,
    ) {
        thread {
            val cartItem = cartDao.getLimitProducts(limit, offset).map { it.toUiModel() }
            onResult(cartItem)
        }
    }

    override fun addProduct(product: Product) {
        thread {
            cartDao.insert(product.toEntity())
        }
    }

    override fun deleteProduct(cartItemId: Long) {
        thread {
            cartDao.deleteById(cartItemId)
        }
    }

    companion object {
        private var instance: CartStorageImpl? = null

        fun initialize(cartDao: CartDao): CartStorageImpl = instance ?: CartStorageImpl(cartDao).also { instance = it }
    }
}
