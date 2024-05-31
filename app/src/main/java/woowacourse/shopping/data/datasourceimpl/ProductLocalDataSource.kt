package woowacourse.shopping.data.datasourceimpl

import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.ProductWithQuantity

class ProductLocalDataSource(private val productDao: ProductDao) : ProductDataSource {
    override fun insertProducts(products: List<ProductEntity>) {
        threadAction {
            productDao.insertProducts(products)
        }
    }

    override fun productWithQuantityItem(productId: Long): Result<ProductWithQuantity> {
        return runCatching {
            var productItem: ProductWithQuantity? = null
            threadAction {
                productItem = productDao.getProductWithQuantityById(productId)
            }
            productItem ?: throw Exception(ERROR_MESSAGE)
        }
    }

    override fun findProductWithQuantityItemsByPage(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductWithQuantity>> {
        return runCatching {
            var productWithQuantities = emptyList<ProductWithQuantity>()
            val offset = page * pageSize

            threadAction {
                productWithQuantities = productDao.getProductWithQuantityByPage(limit = pageSize, offset = offset)
            }

            productWithQuantities
        }
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }

    companion object {
        private const val ERROR_MESSAGE = "상품을 찾을 수 없습니다."

        @Volatile private var instance: ProductLocalDataSource? = null

        fun getInstance(productDao: ProductDao): ProductLocalDataSource =
            instance ?: synchronized(this) {
                instance ?: ProductLocalDataSource(productDao).also {
                    instance = it
                }
            }
    }
}
