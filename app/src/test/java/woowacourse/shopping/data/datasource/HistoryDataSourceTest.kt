package woowacourse.shopping.data.datasource

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.db.dao.HistoryDao
import woowacourse.shopping.data.db.entity.HistoryEntity

class HistoryDataSourceTest {
    private lateinit var dao: HistoryDao
    private lateinit var dataSource: HistoryDataSource

    @BeforeEach
    fun setUp() {
        dao = mockk()
        dataSource = HistoryDataSource(dao)
    }

    @Test
    fun `새로운_기록을_추가하고_추가된_기록을_가져온다`() {
        val entity = HistoryEntity(1L, 1L)

        every { dao.insert(any()) } just Runs
        every { dao.getLatestHistory() } returns listOf(entity)

        dataSource.insertHistory(entity)
        val result = dataSource.latestHistory()

        verify { dao.insert(entity) }
        assertThat(result).containsExactly(entity)
    }

    @Test
    fun `최근_기록을_가져온다`() {
        // given
        val expected =
            listOf(
                HistoryEntity(productId = 1L, createdAt = 100L),
                HistoryEntity(productId = 2L, createdAt = 200L),
            )
        every { dao.getLatestHistory() } returns expected

        // when
        val result = dataSource.latestHistory()

        // then
        verify(exactly = 1) { dao.getLatestHistory() }
        assertThat(result).isEqualTo(expected)
    }
}
