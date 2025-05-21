package woowacourse.shopping.data.shopping

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.mapper.toShoppingEntity
import woowacourse.shopping.fixture.createShoppingGoods

class ShoppingDaoTest {
    private lateinit var db: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = db.shoppingDao()
        dao.insert(createShoppingGoods(goodsId = 1, goodsQuantity = 2).toShoppingEntity())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `장바구니에_저장된_모든_상품을_불러온다`() {
        // when
        val actual = dao.getAll()

        // then
        actual shouldBe listOf(createShoppingGoods(1, 2).toShoppingEntity())
    }

    @Test
    fun `장바구니에_저장된_상품을_삭제한다`() {
        // when
        dao.delete(1)
        val actual = dao.getAll()

        // then
        actual shouldBe emptyList()
    }

    @Test
    fun `상품의_수량을_변경한다`() {
        // when
        dao.updateQuantity(1, 4)
        val actual = dao.getAll()

        // then
        actual shouldBe listOf(createShoppingGoods(1, 4).toShoppingEntity())
    }

    @Test
    fun `상품을_추가한다`() {
        // when
        dao.insert(createShoppingGoods(goodsId = 2).toShoppingEntity())
        val actual = dao.getAll()

        // then
        actual shouldContain createShoppingGoods(2).toShoppingEntity()
    }

    @Test
    fun `페이지와_개수에_맞는_상품_목록을_반환한다`() {
        // given
        val shoppingEntities =
            listOf(
                createShoppingGoods(goodsId = 1).toShoppingEntity(),
                createShoppingGoods(goodsId = 2).toShoppingEntity(),
                createShoppingGoods(goodsId = 3).toShoppingEntity(),
                createShoppingGoods(goodsId = 4).toShoppingEntity(),
                createShoppingGoods(goodsId = 5).toShoppingEntity(),
                createShoppingGoods(goodsId = 6).toShoppingEntity(),
            )
        shoppingEntities.forEach { dao.insert(it) }

        // when
        val actual = dao.getPagedGoods(0, 5)

        // then
        actual shouldBe shoppingEntities.subList(0, 5)
    }

    @Test
    fun `id_값으로_장바구니에_담긴_상품을_찾는다`() {
        // when
        val actual = dao.findGoodsById(1)

        // then
        actual shouldBe createShoppingGoods(goodsId = 1, goodsQuantity = 2).toShoppingEntity()
    }
}
