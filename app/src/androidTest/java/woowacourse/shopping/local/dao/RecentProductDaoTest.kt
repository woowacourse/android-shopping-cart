package woowacourse.shopping.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.domain.Product
import woowacourse.shopping.local.AppDatabase
import woowacourse.shopping.local.entity.RecentProductEntity
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class RecentProductDaoTest {
    private lateinit var recentProductDao: RecentProductDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        recentProductDao = db.recentProductDao()
    }

    @Test
    fun `상품을_확인하면_최근_본_상품에_저장된다`() {
        // given
        val item = RecentProductEntity(0, Product(0, 1000, "상품", ""), viewTime = 10)
        val expectedTime = item.viewTime
        thread {
            recentProductDao.saveRecentProduct(item)
        }.join()
        // when
        val actualTime = recentProductDao.getRecentProduct()?.viewTime
        // then
        assertThat(actualTime).isNotNull()
        assertThat(actualTime).isEqualTo(expectedTime)
    }

    @Test
    fun `최근_본_상품에서_size값을_지정하여_값을_가져_올_수_있다`() {
        // given
        val item1 =
            RecentProductEntity(
                1,
                Product(0, 1000, "상품1", ""),
                viewTime = System.currentTimeMillis(),
            )
        val item2 =
            RecentProductEntity(
                2,
                Product(1, 1000, "상품2", ""),
                viewTime = System.currentTimeMillis(),
            )
        thread {
            recentProductDao.saveRecentProduct(item1)
            recentProductDao.saveRecentProduct(item2)
        }.join()
        // when
        val actual = recentProductDao.getRecentProductsByPaging(2)
        // then
        assertThat(actual.contains(item1)).isEqualTo(true)
        assertThat(actual.contains(item2)).isEqualTo(true)
    }
}
