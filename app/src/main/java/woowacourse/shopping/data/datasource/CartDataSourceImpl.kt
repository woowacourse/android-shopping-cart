package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.Product
import kotlin.concurrent.thread

class CartDataSourceImpl(
    private val dao: CartDao,
) : CartDataSource {
    override fun getCartProductCount(onResult: (Result<Int?>) -> Unit) {
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

    override fun getPagedCartProductIds(
        limit: Int,
        page: Int,
        onResult: (Result<List<Long>>) -> Unit,
    ) {
        val offset = limit * page
        runThread(
            block = { dao.getPagedProductIds(limit, offset) },
            onResult = onResult,
        )
    }

    override fun increaseQuantity(productId: Long) {
        thread {
            dao.increaseQuantity(productId)
        }
    }

    override fun decreaseQuantity(productId: Long) {
        thread {
            dao.decreaseQuantity(productId)
        }
    }

    override fun insertProduct(product: Product) {
//        runThread(
//            block = { dao.insertProduct(product.toEntity()) },
//        )
    }

    override fun deleteProduct(productId: Long) {
//        runThread(
//            block = {
//                dao.deleteByProductId(productId)
//                productId
//            },
//        )
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
