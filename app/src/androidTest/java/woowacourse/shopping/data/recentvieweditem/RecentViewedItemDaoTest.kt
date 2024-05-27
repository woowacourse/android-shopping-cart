package woowacourse.shopping.data.recentvieweditem

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.TestFixture.deleteAll
import woowacourse.shopping.TestFixture.makeRecentViewedEntity
import woowacourse.shopping.domain.model.Product
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class RecentViewedItemDaoTest {
    private lateinit var database: RecentViewedItemDatabase
    private lateinit var dao: RecentViewedItemDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = RecentViewedItemDatabase.getInstance(context)
        dao = database.recentViewedItemDao()
    }

    @After
    fun tearDown() {
        database.deleteAll()
    }

    @Test
    fun `최근_본_상품을_저장할_수_있다`() {
        val entity = makeRecentViewedEntity(Product(0L, "상품1", 1_000, ""))
        val entityId = dao.saveRecentViewedItem(entity)

        val inserted = entity.copy(id = entityId)

        assertThat(dao.findAllItemsByMostRecent()).isEqualTo(listOf(inserted))
    }

    @Test
    fun `최근_본_상품들을_최신순으로_정렬하여_반환한다`() {
        val entities =
            List(4) {
                makeRecentViewedEntity(
                    Product(it.toLong(), "상품$it", 1_000, ""),
                    LocalDateTime.of(
                        LocalDate.of(2024, 5, 25),
                        LocalTime.of(it + 5, 0),
                    ),
                )
            }
        val inserted =
            entities.map {
                val newId = dao.saveRecentViewedItem(it)
                it.copy(id = newId)
            }

        val actual = dao.findAllItemsByMostRecent()
        val expected = inserted.reversed()

        assertThat(actual).isEqualTo(expected)
    }
}
