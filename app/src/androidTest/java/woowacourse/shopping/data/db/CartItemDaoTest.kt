package woowacourse.shopping.data.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.TestFixture.deleteAll
import woowacourse.shopping.TestFixture.makeCartItemEntity
import woowacourse.shopping.data.db.cartItem.CartItemDao
import woowacourse.shopping.data.db.cartItem.CartItemDatabase

@RunWith(AndroidJUnit4::class)
class CartItemDaoTest {
    private lateinit var database: CartItemDatabase
    private lateinit var dao: CartItemDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = CartItemDatabase.getInstance(context)
        dao = database.cartItemDao()
    }

    @After
    fun tearDown() {
        database.deleteAll()
    }

    @Test
    fun `전체_장바구니_아이템을_불러올_수_있다`() {
        val itemEntity = makeCartItemEntity(0L, "아메리카노", 1)
        val itemId = dao.saveCartItem(itemEntity)

        val insertedItemEntity = itemEntity.copy(id = itemId)
        assertThat(dao.findAll()).isEqualTo(listOf(insertedItemEntity))
    }

    @Test
    fun `선택한_아이템을_장바구니에_저장할_수_있다`() {
        val itemEntity = makeCartItemEntity(0L, "아메리카노", 1)
        val itemId = dao.saveCartItem(itemEntity)

        val actual = dao.findAll()
        val expected = listOf(itemEntity.copy(id = itemId))
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `특정_ID로_장바구니_아이템을_불러올_수_있다`() {
        val item = makeCartItemEntity(0L, "아메리카노", 1)
        val itemId = dao.saveCartItem(item)

        val actual = dao.findCartItemById(itemId)
        val expected = item.copy(id = itemId)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `특정_ID로_장바구니_아이템을_삭제할_수_있다`() {
        val item = makeCartItemEntity(0L, "아메리카노", 1)
        val itemId = dao.saveCartItem(item)

        dao.deleteCartItemById(itemId)

        val deletedItem = item.copy(id = itemId)
        assertThat(dao.findAll().contains(deletedItem)).isEqualTo(false)
    }

    @Test
    fun `원하는_범위만큼의_장바구니_아이템_데이터들을_반환해준다`() {
        val items =
            List(5) {
                makeCartItemEntity(it.toLong(), "아메리카노", 1)
            }

        val updated =
            items.map {
                val itemId = dao.saveCartItem(it)
                it.copy(id = itemId)
            }

        val actual = dao.findPagingCartItem(offset = 0, pagingSize = 3)
        val expected = listOf(updated[0], updated[1], updated[2])
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에_담긴_상품들의_총_수량을_반환한다`() {
        val items =
            listOf(
                makeCartItemEntity(0L, "상품1", 2),
                makeCartItemEntity(1L, "상품2", 3),
                makeCartItemEntity(2L, "상품3", 2),
            )

        items.forEach { dao.saveCartItem(it) }

        val actual = dao.getTotalQuantity()
        val expected = 7
        assertThat(actual).isEqualTo(expected)
    }
}
