package woowacourse.shopping.data

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository

class ProductRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
) : ProductRepository {
    override fun getProducts(): List<Product> = productDataSource.getProducts()

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
        cartDataSource.insertProduct(product) { result ->
            result
                .onSuccess {
                    onResult(Result.success(Unit))
                }.onFailure { e ->
                    onResult(Result.failure(e))
                }
        }
    }

    override fun deleteProduct(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    ) {
        cartDataSource.deleteProduct(productId) { result ->
            result
                .onSuccess { id ->
                    onResult(Result.success(id))
                }.onFailure { e ->
                    onResult(Result.failure(e))
                }
        }
    }
}
