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

    override fun getCartProductCount(onComplete: (Int) -> Unit) {
        thread {
            val totalCartCount = dao.getCartProductCount()
            onComplete(totalCartCount)
        }
    }

    override fun getCartProducts(onComplete: (List<Product>) -> Unit) {
        thread {
            val products = dao.getAllProduct().map { productEntity -> productEntity.toDomain() }
            onComplete(products)
        }
    }

    override fun getPagedCartProducts(
        limit: Int,
        page: Int,
        onComplete: (List<Product>) -> Unit,
    ) {
        val offset = limit * page
        thread {
            val products = dao.getPagedProduct(limit, offset).map { it.toDomain() }
            onComplete(products)
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
        onComplete: () -> Unit,
    ) {
        thread {
            dao.deleteByProductId(productId = productId)
            onComplete()
        }
    }
}
