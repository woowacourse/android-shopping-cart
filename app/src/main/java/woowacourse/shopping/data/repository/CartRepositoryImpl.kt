package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.utils.toProduct
import woowacourse.shopping.utils.toProductEntity

class CartRepositoryImpl private constructor(
    private val dao: CartDao,
) : CartRepository {
    override fun findProductById(id: Long): Product? {
        return dao.findById(id)?.toProduct()
    }

    override fun insert(product: Product) {
        dao.insert(product.toProductEntity())
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override fun getSize(): Int {
        return dao.size()
    }

    override fun getPagedProduct(limit: Int, offset: Int): List<Product> {
        return dao.findPagedItems(limit, offset).map { it.toProduct() }
    }

    companion object {
        private var INSTANCE: CartRepositoryImpl? = null

        fun initialize(
            dao: CartDao,
        ) {
            if (INSTANCE == null) {
                INSTANCE = CartRepositoryImpl(dao)
            }
        }

        fun get(): CartRepositoryImpl {
            return INSTANCE
                ?: throw IllegalStateException("CartRepository가 초기화되지 않았습니다.")
        }
    }
}
