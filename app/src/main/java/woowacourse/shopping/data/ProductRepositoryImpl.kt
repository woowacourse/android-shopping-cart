package woowacourse.shopping.data

import woowacourse.shopping.data.CartMapper.toDomain
import woowacourse.shopping.data.CartMapper.toEntity
import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import kotlin.concurrent.thread

class ProductRepositoryImpl(
    private val dao: CartDao,
) : ProductRepository {
    override fun getProducts(): List<Product> = DummyProducts.values

    override fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, DummyProducts.values.size)
        if (fromIndex >= DummyProducts.values.size) return emptyList()
        return DummyProducts.values.subList(fromIndex, toIndex)
    }

    override fun getCartProductCount(onResult: (Result<Int>) -> Unit) {
        runThread(
            block = { dao.getCartProductCount() },
            onResult = onResult,
        )
    }

    override fun getCartProducts(onResult: (Result<List<Product>>) -> Unit) {
        runThread(
            block = { dao.getAllProduct().map { it.toDomain() } },
            onResult = onResult,
        )
    }

    override fun getPagedCartProducts(
        limit: Int,
        page: Int,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        val offset = limit * page
        runThread(
            block = { dao.getPagedProduct(limit, offset).map { it.toDomain() } },
            onResult = onResult,
        )
    }

    override fun insertProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { dao.insertProduct(product.toEntity()) },
            onResult = onResult,
        )
    }

    override fun deleteProduct(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    ) {
        runThread(
            block = {
                dao.deleteByProductId(productId)
                productId
            },
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
