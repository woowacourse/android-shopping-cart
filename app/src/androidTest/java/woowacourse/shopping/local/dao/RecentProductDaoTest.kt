package woowacourse.shopping.local.dao

import androidx.room.Room
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.shopping.fixtures.dateTime
import woowacourse.shopping.fixtures.recentProductEntities
import woowacourse.shopping.fixtures.recentProductEntity
import woowacourse.shopping.local.ShoppingDatabase
import woowacourse.shopping.util.testApplicationContext

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
        val product = recentProductEntity(id = 1L)
        // when
        val expectId = 1L
        val actualId = dao.saveProduct(product)
        // then
        actualId shouldBe expectId
    }

    @Test
    @DisplayName("최근 본 상품을 3개 저장 하고, 최근 상품 본 상품을 불러올 때, 최신 시간 순으로 정렬 된다.")
    fun `test2`() {
        // given & when
        dao.saveProduct(recentProductEntity(1L, dateTime().plusDays(1)))
        dao.saveProduct(recentProductEntity(2L, dateTime()))
        dao.saveProduct(recentProductEntity(3L, dateTime().plusDays(2)))
        val expect =
            recentProductEntities(
                recentProductEntity(3L, dateTime().plusDays(2)),
                recentProductEntity(1L, dateTime().plusDays(1)),
                recentProductEntity(2L, dateTime()),
            )
        val actual = dao.loadProducts(3)
        // then
        actual shouldBe expect
    }

    @Test
    @DisplayName("중복되는 id를 저장하면, 제일 나중에 저장한 값으로 덮어씌워진다")
    fun `save_duplicate`() {
        // given & when
        dao.saveProduct(recentProductEntity(1L, dateTime().plusDays(1)))
        dao.saveProduct(recentProductEntity(1L, dateTime()))
        dao.saveProduct(recentProductEntity(1L, dateTime().plusDays(2)))
        val expect =
            recentProductEntities(
                recentProductEntity(1L, dateTime().plusDays(2)),
            )
        val actual = dao.loadProducts(1)
        // then
        actual shouldBe expect
    }
}
