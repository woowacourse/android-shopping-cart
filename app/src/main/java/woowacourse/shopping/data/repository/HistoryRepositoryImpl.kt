package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.local.HistoryDao
import woowacourse.shopping.data.local.HistoryEntity
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.utils.toProduct

class HistoryRepositoryImpl private constructor(
    private val dao: HistoryDao,
    private val cartDao: CartDao,
) : HistoryRepository {
    override fun insert(id: Long) {
        dao.insert(HistoryEntity(id))
    }

    override fun findMostRecentProduct(): Product? {
        val historyEntity = dao.findMostRecentProduct() ?: return null
        return createProductWith(historyEntity)
    }

    override fun getRecentProducts(limit: Int): List<Product> {
        return dao.findRecentProduct(limit).map { createProductWith(it) ?: return emptyList() }
    }

    private fun createProductWith(historyEntity: HistoryEntity): Product? {
        val productEntity = cartDao.findById(historyEntity.id) ?: return null
        return productEntity.toProduct()
    }

    companion object {
        private var INSTANCE: HistoryRepositoryImpl? = null

        fun initialize(
            dao: HistoryDao,
            cartDao: CartDao,
        ) {
            if (INSTANCE == null) {
                INSTANCE = HistoryRepositoryImpl(dao, cartDao)
            }
        }

        fun get(): HistoryRepositoryImpl {
            return INSTANCE
                ?: throw IllegalStateException("HistoryRepositoryImpl가 초기화되지 않았습니다.")
        }
    }
}
