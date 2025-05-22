package woowacourse.shopping.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.entity.HistoryProductEntity
import java.util.concurrent.TimeUnit

@Suppress("ktlint:standard:function-naming")
class HistoryDaoTest {
    private lateinit var database: ShoppingDatabase
    private lateinit var dao: HistoryDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room
                .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        dao = database.historyDao()
    }

    @Test
    fun 검색_기록을_저장하고_조회한다() {
        // given
        val historyProduct = HistoryProductEntity(productId = 1, timestamp = System.currentTimeMillis())
        dao.insertHistory(historyProduct)

        // when
        val result = dao.getHistoryProducts()

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].historyProduct.productId).isEqualTo(1)
    }

    @Test
    fun 검색_기록을_최신순으로_조회한다() {
        // given
        val now = System.currentTimeMillis()
        dao.insertHistory(HistoryProductEntity(productId = 1, timestamp = now - TimeUnit.MINUTES.toMillis(5)))
        dao.insertHistory(HistoryProductEntity(productId = 2, timestamp = now))

        // when
        val result = dao.getHistoryProducts()

        // then
        assertThat(result.map { it.historyProduct.productId }).containsExactly(2, 1).inOrder()
    }

    @Test
    fun 검색_기록이_제한을_초과하면_오래된_기록부터_삭제된다() {
        // given
        val base = System.currentTimeMillis()
        repeat(5) { index ->
            dao.insertHistory(HistoryProductEntity(productId = index + 1, timestamp = base + index))
        }

        // when
        dao.insertHistoryWithLimit(HistoryProductEntity(productId = 100, timestamp = base + 100), limit = 5)

        // then
        val result = dao.getHistoryProducts()
        assertThat(result).hasSize(5)
        assertThat(result.any { it.historyProduct.productId == 1 }).isFalse()
        assertThat(result.first().historyProduct.productId).isEqualTo(100)
    }

    @Test
    fun 최근_검색_기록을_조회한다() {
        // given
        val now = System.currentTimeMillis()
        dao.insertHistory(HistoryProductEntity(productId = 10, timestamp = now))
        dao.insertHistory(HistoryProductEntity(productId = 11, timestamp = now + 500))

        // when
        val result = dao.getRecentHistoryProduct()

        // then
        assertThat(result?.historyProduct?.productId).isEqualTo(11)
    }

    @Test
    fun 총_검색_기록_개수를_조회한다() {
        // given
        dao.insertHistory(HistoryProductEntity(productId = 1))
        dao.insertHistory(HistoryProductEntity(productId = 2))

        // when
        val count = dao.getHistoryCount()

        // then
        assertThat(count).isEqualTo(2)
    }

    @Test
    fun 오래된_검색_기록을_원하는_개수만큼_삭제한다() {
        // given
        val base = System.currentTimeMillis()
        repeat(5) { index ->
            dao.insertHistory(HistoryProductEntity(productId = index + 1, timestamp = base + index))
        }

        // when
        dao.deleteOldestHistories(2)

        // then
        val result = dao.getHistoryProducts()
        assertThat(result).hasSize(3)
        assertThat(result.map { it.historyProduct.productId }).doesNotContain(1)
        assertThat(result.map { it.historyProduct.productId }).doesNotContain(2)
    }

    @After
    fun tearDown() {
        database.close()
    }
}
