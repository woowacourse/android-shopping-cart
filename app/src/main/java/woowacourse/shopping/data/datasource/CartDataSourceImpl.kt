package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity
import kotlin.concurrent.thread

class CartDataSourceImpl(
    private val dao: CartDao,
) : CartDataSource {
    override fun getCartProductCount(onResult: (Result<Int?>) -> Unit) {
        runThread(
            block = { dao.getAllProductCount() },
            onResult = onResult,
        )
    }

    override fun getTotalQuantity(onResult: (Result<Int?>) -> Unit) {
        runThread(
            block = { dao.getTotalQuantity() },
            onResult = onResult,
        )
    }

    override fun getCartProducts(onResult: (Result<List<CartEntity>>) -> Unit) {
        runThread(
            block = { dao.getAllProducts() },
            onResult = onResult,
        )
    }

    override fun getCartItemById(
        productId: Long,
        onResult: (Result<CartEntity?>) -> Unit,
    ) {
        runThread(
            block = { dao.getCartItemById(productId) },
            onResult = onResult,
        )
    }

    override fun getPagedCartProducts(
        limit: Int,
        page: Int,
        onResult: (Result<List<CartEntity>>) -> Unit,
    ) {
        val offset = limit * page
        runThread(
            block = { dao.getPagedProducts(limit, offset) },
            onResult = onResult,
        )
    }

    override fun increaseQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { dao.increaseQuantity(productId, quantity) },
            onResult = onResult,
        )
    }

    override fun decreaseQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { dao.decreaseQuantity(productId) },
            onResult = onResult,
        )
    }

    override fun existsByProductId(
        productId: Long,
        onResult: (Result<Boolean>) -> Unit,
    ) {
        runThread(
            block = { dao.existsByProductId(productId) },
            onResult = onResult,
        )
    }

    override fun insertProduct(
        cartEntity: CartEntity,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { dao.insertProduct(cartEntity) },
            onResult = onResult,
        )
    }

    override fun deleteProduct(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { dao.deleteProductById(productId) },
            onResult = onResult,
        )
    }

    private inline fun <T> runThread(
        crossinline block: () -> T,
        crossinline onResult: (Result<T>) -> Unit,
    ) {
        thread {
            onResult(runCatching { block() })
        }
    }
}
