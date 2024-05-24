package woowacourse.shopping.data.product

import woowacourse.shopping.data.product.entity.Product
import java.lang.IllegalArgumentException

interface ProductRepository {
    fun find(id: Long): Product

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun totalProductCount(): Int

    companion object {
        private const val NOT_INITIALIZE_INSTANCE_MESSAGE = "초기화된 인스턴스가 없습니다."

        @Volatile
        private var instance: ProductRepository? = null

        fun setInstance(productRepository: ProductRepository) {
            synchronized(this) {
                instance = productRepository
            }
        }

        fun getInstance(): ProductRepository {
            return instance ?: throw IllegalArgumentException(NOT_INITIALIZE_INSTANCE_MESSAGE)
        }
    }
}
