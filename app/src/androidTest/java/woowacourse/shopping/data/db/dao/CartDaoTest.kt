package woowacourse.shopping.data.db.dao

import androidx.room.Room
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.fixtures.cartEntities
import woowacourse.shopping.fixtures.cartEntity
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
        val cart = cartEntity()
        val expect = 1L
        // when
        val actual = dao.saveCart(cart)
        // then
        actual shouldBe expect
    }

    @Test
    @DisplayName(
        "3개의 상품을 저장한 후," +
                "1개의 상품 만큼 건너 뛰고, 2개의 상품을 조회 한다."
    )
    fun insert_and_load() {
        // given & when
        val expect = cartEntities(2L, 3L)
        // when
        dao.saveCart(cartEntity(1L))
        dao.saveCart(cartEntity(2L))
        dao.saveCart(cartEntity(3L))
        val actual = dao.loadCart(offset = 1, size = 2)
        // then
        actual shouldBe expect
    }

    @Test
    @DisplayName("동일한 id를 가진 상품을 저장 하면, 덮어 씌어진다.")
    fun insert_and_load2() {
        // given
        val expectSize = 1
        val expect = listOf(cartEntity(count = 2))
        // when
        dao.saveCart(cartEntity(count = 1))
        dao.saveCart(cartEntity(count = 3))
        dao.saveCart(cartEntity(count = 2))
        val actual = dao.loadCart(offset = 0, size = 2)
        // then
        actual shouldHaveSize expectSize
        actual shouldBe expect
    }


    @Test
    @DisplayName("product 의 id 에 해당 하는 상품을 삭제한다.")
    fun insert_and_delete() {
        // given
        val expectSize = 0
        // when
        dao.saveCart(cartEntity())
        dao.deleteCart(id = 1L)
        val actual = dao.loadCart(offset = 0, size = 1)
        // then
        actual shouldHaveSize expectSize
    }

    @Test
    @DisplayName("카트 상품을 3개 저장한 후, 모두 삭제한다.")
    fun insert_and_delete_all() {
        // given & when
        dao.saveCart(cartEntity(id = 1L))
        dao.saveCart(cartEntity(id = 2L))
        dao.saveCart(cartEntity(id = 3L))
        dao.deleteAllCarts()
        val expectSize = 0
        val actual = dao.loadCart(offset = 0, size = 3)
        // then
        actual shouldHaveSize expectSize
    }
}