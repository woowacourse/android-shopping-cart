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
        thread {
            onResult(kotlin.runCatching { dao.getCartProductCount() })
        }
    }

    override fun getCartProducts(onResult: (Result<List<Product>>) -> Unit) {
        thread {
            onResult(runCatching { dao.getAllProduct().map { it.toDomain() } })
        }
    }

    override fun getPagedCartProducts(
        limit: Int,
        page: Int,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        val offset = limit * page
        thread {
            onResult(runCatching { dao.getPagedProduct(limit, offset).map { it.toDomain() } })
        }
    }

    override fun insertProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        thread {
            onResult(runCatching { dao.insertProduct(product.toEntity()) })
        }
    }

    override fun deleteProduct(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    ) {
        thread {
            onResult(runCatching { dao.deleteByProductId(productId).let { productId } })
        }
    }
}
