package woowacourse.shopping.local.dao

import androidx.room.Room
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.shopping.fixtures.fakeCartEntities
import woowacourse.shopping.fixtures.fakeCartEntity
import woowacourse.shopping.local.ShoppingDatabase
import woowacourse.shopping.util.testApplicationContext

class CartDaoTest {
    private lateinit var dao: CartDao

    @Before
    fun setUp() {
        val db =
            Room.inMemoryDatabaseBuilder(
                testApplicationContext,
                ShoppingDatabase::class.java,
            ).build()
        dao = db.cartDao()
    }

    @Test
    @DisplayName("카트 상품을 저장하고, id 를 반환한다")
    fun insert() {
        // given
        val cart = fakeCartEntity()
        val expect = 1L
        // when
        val actual = dao.saveCart(cart)
        // then
        actual shouldBe expect
    }

    @Test
    @DisplayName(
        "3개의 상품을 저장한 후," +
            "1개의 상품 만큼 건너 뛰고, 2개의 상품을 조회 한다.",
    )
    fun insert_and_load() {
        // given & when
        val expect = fakeCartEntities(2L, 3L)
        // when
        saveCarts(1L, 2L, 3L)
        val actual = dao.loadCart(offset = 1, size = 2)
        // then
        actual shouldBe expect
    }

    @Test
    @DisplayName("동일한 id를 가진 상품을 저장 하면, 덮어 씌어진다.")
    fun insert_and_load2() {
        // given & when
        dao.saveCart(fakeCartEntity(count = 1))
        dao.saveCart(fakeCartEntity(count = 3))
        dao.saveCart(fakeCartEntity(count = 2))
        val actual = dao.loadCart(offset = 0, size = 2)
        // then
        val expectSize = 1
        val expect = listOf(fakeCartEntity(count = 2))
        actual shouldHaveSize expectSize
        actual shouldBe expect
    }

    @Test
    @DisplayName("product 의 id 에 해당 하는 상품을 삭제한다.")
    fun insert_and_delete() {
        // given
        val expectSize = 0
        // when
        dao.saveCart(fakeCartEntity())
        dao.deleteCart(id = 1L)
        val actual = dao.loadCart(offset = 0, size = 1)
        // then
        actual shouldHaveSize expectSize
    }

    @Test
    @DisplayName("카트 상품을 3개 저장한 후, 모두 삭제한다.")
    fun insert_and_delete_all() {
        // given & when
        saveCarts(1L, 2L, 3L)
        dao.deleteAllCarts()
        val actual = dao.loadCart(offset = 0, size = 3)
        // then
        val expectSize = 0
        actual shouldHaveSize expectSize
    }

    @Test
    @DisplayName("카트에 상품이 없으면, 로드할 수 없다.")
    fun loadCart_empty() {
        // when
        val actual = dao.canLoadMore(size = 20)
        // then
        actual.shouldBeFalse()
    }

    private fun saveCarts(vararg id: Long) {
        id.forEach {
            dao.saveCart(fakeCartEntity(id = it))
        }
    }
}
