package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class RecentProductDaoTest {
    private lateinit var recentProductDao: RecentProductDao

    @Before
    fun setup() {
        val fakeContext = ApplicationProvider.getApplicationContext<Context>()
        val database =
            Room
                .inMemoryDatabaseBuilder(fakeContext, ShoppingDatabase::class.java)
                .build()

        recentProductDao = database.recentProductDao()
    }

    @Test
    fun `최근_본_상품_목록에서_동일_상품_중_viewedAt이_가장_최신인_것만_조회된다`() {
        val now = System.currentTimeMillis()

        recentProductDao.insert(RecentProductEntity(1L, productId = 1L, viewedAt = now - 1000))
        recentProductDao.insert(RecentProductEntity(2L, productId = 1L, viewedAt = now))
        recentProductDao.insert(RecentProductEntity(3L, productId = 2L, viewedAt = now - 500))
        recentProductDao.insert(RecentProductEntity(4L, productId = 3L, viewedAt = now - 2000))

        val result = recentProductDao.getRecentProducts(limit = 10)

        assertThat(result).hasSize(3)
        assertThat(result[0].productId).isEqualTo(1L)
        assertThat(result[1].productId).isEqualTo(2L)
        assertThat(result[2].productId).isEqualTo(3L)
    }

    @Test
    fun `동일한_id로_insert_시_기존_데이터가_REPLACE_된다`() {
        val entity = RecentProductEntity(id = 1L, productId = 10L, viewedAt = 1000L)
        recentProductDao.insert(entity)

        val updatedEntity = entity.copy(viewedAt = 9999L)
        recentProductDao.insert(updatedEntity)

        val result = recentProductDao.getRecentProducts(1).first()
        assertThat(result.viewedAt).isEqualTo(9999L)
    }

    @Test
    fun `현재_상품을_제외한_가장_마지막으로_본_상품을_조회한다`() {
        val now = System.currentTimeMillis()
        recentProductDao.insert(RecentProductEntity(1L, productId = 1L, viewedAt = now - 1000))
        recentProductDao.insert(RecentProductEntity(2L, productId = 2L, viewedAt = now - 500))
        recentProductDao.insert(RecentProductEntity(3L, productId = 3L, viewedAt = now))

        val result = recentProductDao.getLastViewedProduct(currentProductId = 3L)

        assertThat(result?.productId).isEqualTo(2L)
    }
}
