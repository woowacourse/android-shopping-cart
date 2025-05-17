package woowacourse.shopping.data.repository

import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.domain.CartResult
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(private val storage: CartStorage) : CartRepository {
    override fun insert(item: Product) = storage.insertProduct(item)

    override fun delete(id: Long) = storage.deleteProduct(id)

    override fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): CartResult {
        val fromIndex = page * pageSize
        val toIndex = fromIndex + pageSize
        val result = storage.singlePage(fromIndex, toIndex)

        return result
    }
}
