package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.repsoitory.DummyData.PRODUCT_LIST
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

object DummyProductRepositoryImpl : ProductRepository {
    private val productList: MutableList<Product> = PRODUCT_LIST

    override fun findProductById(id: Int): Result<Product> =
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
