package woowacourse.shopping.data.recent

import woowacourse.shopping.data.recent.entity.RecentProduct
import java.lang.IllegalArgumentException

interface RecentProductRepository {
    fun findLastOrNull(): RecentProduct?

    fun findRecentProducts(): List<RecentProduct>

    fun save(productId: Long)

    companion object {
        private const val NOT_INITIALIZE_INSTANCE_MESSAGE = "초기화된 인스턴스가 없습니다."

        @Volatile
        private var instance: RecentProductRepository? = null

        fun setInstance(recentProductRepository: RecentProductRepository) {
            synchronized(this) {
                instance = recentProductRepository
            }
        }

        fun getInstance(): RecentProductRepository {
            return instance ?: throw IllegalArgumentException(NOT_INITIALIZE_INSTANCE_MESSAGE)
        }
    }
}
