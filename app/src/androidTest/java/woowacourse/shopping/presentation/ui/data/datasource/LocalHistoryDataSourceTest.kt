package woowacourse.shopping.presentation.ui.data.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_1
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_2
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_3
import woowacourse.shopping.data.db.AppDatabase
import woowacourse.shopping.data.db.dao.HistoryDao
import woowacourse.shopping.data.db.mapper.toEntity
import woowacourse.shopping.data.db.mapper.toHistory
import woowacourse.shopping.data.db.model.HistoryEntity
import woowacourse.shopping.domain.model.History

class LocalHistoryDataSourceTest {
    private lateinit var dao: HistoryDao
    private lateinit var db: AppDatabase

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        dao = db.historyDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `history를_읽고_쓰기`() {
        val history = History(STUB_PRODUCT_1, 0L)
        dao.putHistory(history.toEntity())
        val actual = dao.getHistories(1).toHistory()
        val expected = listOf(history)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `최근에_조회한_순으로_history를_불러온다`() {
        val stubA = HistoryEntity(1, STUB_PRODUCT_1.toEntity(), 0L)
        val stubB = HistoryEntity(2, STUB_PRODUCT_2.toEntity(), 1L)
        val stubC = HistoryEntity(3, STUB_PRODUCT_3.toEntity(), 2L)
        dao.putHistory(stubA)
        dao.putHistory(stubB)
        dao.putHistory(stubC)

        val actual = dao.getHistories(3)
        val expected = listOf(stubC, stubB, stubA)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `같은_물품을_다시_조회할_경우_새로운_timestamp로_갱신된다`() {
        // given
        val oldHistory = History(STUB_PRODUCT_1, 0L).toEntity()
        val newHistory = History(STUB_PRODUCT_1, 1L).toEntity()
        dao.putHistory(oldHistory)

        // when
        dao.putHistory(newHistory)

        val actual = dao.getHistories(1)[0].timestamp
        val expected = 1L
        assertThat(actual).isEqualTo(expected)
    }
}
