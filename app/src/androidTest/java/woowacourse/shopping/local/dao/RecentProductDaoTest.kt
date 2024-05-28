package woowacourse.shopping.local.dao

import androidx.room.Room
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.shopping.fixtures.dateTime
import woowacourse.shopping.fixtures.fakeRecentProductEntities
import woowacourse.shopping.fixtures.fakeRecentProductEntity
import woowacourse.shopping.local.ShoppingDatabase
import woowacourse.shopping.util.testApplicationContext
import java.time.LocalDateTime

class RecentProductDaoTest {
    private lateinit var dao: RecentProductDao

    @Before
    fun setUp() {
        val db =
            Room.inMemoryDatabaseBuilder(
                testApplicationContext,
                ShoppingDatabase::class.java,
            ).build()
        dao = db.recentProductDao()
    }

    @Test
    @DisplayName("최근 본 상품을 저장하고, id를 반환한다.")
    fun `test`() {
        // given
        val product = fakeRecentProductEntity(id = 1L)
        // when
        val actualId = dao.saveProduct(product)
        // then
        val expectId = 1L
        actualId shouldBe expectId
    }

    @Test
    @DisplayName("최근 본 상품을 3개 저장 하고, 최근 상품 본 상품을 불러올 때, 최신 시간 순으로 정렬 된다.")
    fun `test2`() {
        // given: 최근 본 상품 3개 저장
        val given =
            fakeRecentProductEntities(
                fakeRecentProductEntity(1L, dateTime().plusDays(1)),
                fakeRecentProductEntity(2L, dateTime()),
                fakeRecentProductEntity(3L, dateTime().plusDays(2)),
            )
        // when : 상품을 저장
        given.forEach { dao.saveProduct(it) }
        // then : 상품은 최신 시간순
        val expect = given.sortedByDescending { it.createdTime }
        val actual = dao.loadProducts(3)
        actual shouldBe expect
    }

    @Test
    @DisplayName("중복되는 id를 저장하면, 제일 나중에 저장한 값으로 덮어씌워진다")
    fun `save_duplicate`() {
        // given & when
        saveProduct(1L, dateTime().plusDays(1))
        saveProduct(1L, dateTime())
        saveProduct(1L, dateTime().plusDays(2))
        // then
        val expect =
            fakeRecentProductEntities(
                fakeRecentProductEntity(1L, dateTime().plusDays(2)),
            )
        val actual = dao.loadProducts(1)
        actual shouldBe expect
    }

    private fun saveProduct(
        id: Long,
        createdTime: LocalDateTime,
    ) {
        dao.saveProduct(fakeRecentProductEntity(id, createdTime))
    }
}
