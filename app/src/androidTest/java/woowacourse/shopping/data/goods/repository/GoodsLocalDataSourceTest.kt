package woowacourse.shopping.data.goods.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.domain.model.Goods
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Suppress("ktlint:standard:function-naming")
@RunWith(AndroidJUnit4::class)
class GoodsLocalDataSourceTest {
    private lateinit var database: ShoppingDatabase
    private lateinit var localDataSource: GoodsLocalDataSourceImpl

    val goods1 = Goods("상품1", 10000, "url1", 1)
    val goods2 = Goods("상품2", 20000, "url2", 2)

    @Before
    fun setup() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    ShoppingDatabase::class.java,
                ).allowMainThreadQueries()
                .build()

        localDataSource = GoodsLocalDataSourceImpl(database)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun 최근_본_상품_저장_및_조회() {
        // When
        saveGoods(goods1)
        val result = fetchRecentIds()

        // Then
        assertThat(result).hasSize(1)
        assertThat(result[0]).isEqualTo(goods1.id.toString())
    }

    @Test
    fun 여러_상품_순차_저장_및_조회() {
        // When
        saveGoods(goods1)
        saveGoods(goods2)
        val result = fetchRecentIds()

        // Then
        assertThat(result).hasSize(2)
        assertThat(result[0]).isEqualTo("2") // 최근 순
        assertThat(result[1]).isEqualTo("1")
    }

    @Test
    fun 빈_상태에서_빈_리스트_반환() {
        // When
        val result = fetchRecentIds()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun 중복_상품_저장시_최신으로_이동() {
        saveGoods(goods1)
        saveGoods(goods2)
        saveGoods(goods1)

        val result = fetchRecentIds()

        // Then
        assertThat(result).hasSize(2)
        assertThat(result[0]).isEqualTo("1")
        assertThat(result[1]).isEqualTo("2")
    }

    private fun saveGoods(goods: Goods) {
        val latch = CountDownLatch(1)
        localDataSource.loggingRecentGoods(goods) {
            latch.countDown()
        }
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue()
    }

    private fun fetchRecentIds(): List<String> {
        val latch = CountDownLatch(1)
        var result: List<String>? = null

        localDataSource.fetchRecentGoodsIds { recentIds ->
            result = recentIds
            latch.countDown()
        }
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue()

        return result!!
    }
}
