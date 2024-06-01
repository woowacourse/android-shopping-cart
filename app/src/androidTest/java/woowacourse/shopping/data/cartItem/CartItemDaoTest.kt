package woowacourse.shopping.data.cartItem

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
        val itemEntity = makeCartItemEntity(1L, 0L, "아메리카노", 1)
        dao.saveCartItem(itemEntity)

        val actual = dao.findAll()

        val expected = listOf(itemEntity)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `선택한_아이템을_장바구니에_저장할_수_있다`() {
        val itemEntity = makeCartItemEntity(1L, 0L, "아메리카노", 1)

        dao.saveCartItem(itemEntity)

        val result = dao.findAll()
        assertThat(result).contains(itemEntity)
    }

    @Test
    fun `특정_ID로_장바구니_아이템을_불러올_수_있다`() {
        val cartItem = 1L
        val item = makeCartItemEntity(cartItem, 0L, "아메리카노", 1)
        dao.saveCartItem(item)

        val actual = dao.findCartItemById(cartItem)

        assertThat(actual).isEqualTo(item)
    }

    @Test
    fun `특정_ID로_장바구니_아이템을_삭제할_수_있다`() {
        val cartItem = 1L
        val item = makeCartItemEntity(cartItem, 0L, "아메리카노", 1)
        dao.saveCartItem(item)

        dao.deleteCartItemById(cartItem)

        assertThat(dao.findAll().contains(item)).isEqualTo(false)
    }

    @Test
    fun `원하는_범위만큼의_장바구니_아이템_데이터들을_반환해준다`() {
        val items =
            List(5) {
                makeCartItemEntity(it.toLong(), it.toLong(), "아메리카노", 1)
            }
        items.forEach { dao.saveCartItem(it) }

        val actual = dao.findPagingCartItem(offset = 0, pagingSize = 3)

        val expected = listOf(items[0], items[1], items[2])
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에_담긴_상품들의_총_수량을_반환한다`() {
        val items =
            listOf(
                makeCartItemEntity(0L, 0L, "상품1", quantity = 2),
                makeCartItemEntity(1L, 1L, "상품2", quantity = 3),
                makeCartItemEntity(2L, 2L, "상품3", quantity = 2),
            )
        items.forEach { dao.saveCartItem(it) }

        val actual = dao.getTotalQuantity()
        val expected = 7
        assertThat(actual).isEqualTo(expected)
    }
}
