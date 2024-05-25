package woowacourse.shopping.presentation.ui.data.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_A
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_B
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_C
import woowacourse.shopping.data.db.AppDatabase
import woowacourse.shopping.data.db.dao.HistoryDao
import woowacourse.shopping.data.db.mapper.toEntity
import woowacourse.shopping.data.db.mapper.toHistory
import woowacourse.shopping.domain.model.History

class LocalHistoryDataSourceTest {
    private lateinit var dao: HistoryDao
    private lateinit var db: AppDatabase

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(context, AppDatabase::class.java, "testDb").build()
        dao = db.historyDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `history를_읽고_쓰기`() {
        val history = History(1, STUB_PRODUCT_A, 0L)
        dao.putHistory(history.toEntity())
        val actual = dao.getHistories(1).toHistory()
        val expected = listOf(history)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `최근에_조회한_순으로_history를_불러온다`() {
        val stubA = History(1, STUB_PRODUCT_A, 0L).toEntity()
        val stubB = History(2, STUB_PRODUCT_B, 1L).toEntity()
        val stubC = History(3, STUB_PRODUCT_C, 2L).toEntity()
        dao.putHistory(stubA)
        dao.putHistory(stubB)
        dao.putHistory(stubC)

        val actual = dao.getHistories(3)
        val expected = listOf(stubC, stubB, stubA)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `같은_물품을_다시_조회할_경우_새로운_history로_갱신된다`() {
        // given
        val oldHistory = History(1, STUB_PRODUCT_A, 0L).toEntity()
        val newHistory = History(1, STUB_PRODUCT_A, 1L).toEntity()
        dao.putHistory(oldHistory)

        // when
        dao.putHistory(newHistory)

        val actual = dao.getHistories(1)
        val expected = listOf(newHistory)
        assertThat(actual).isEqualTo(expected)
    }
}
