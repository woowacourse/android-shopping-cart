package woowacourse.shopping.data.repsoitory.remote

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

class ProductRepositoryImpl(private val productList: MutableList<Product>) : ProductRepository {
    override fun findProductById(id: Long): Result<Product> =
        runCatching {
            productList.find { it.id == id } ?: throw NoSuchElementException()
        }

    override fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<Product>> =
        runCatching {
            val fromIndex = page * pageSize
            val toIndex = min(fromIndex + pageSize, productList.size)
            productList.subList(fromIndex, toIndex)
        }
}
