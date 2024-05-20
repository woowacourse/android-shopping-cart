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
import woowacourse.shopping.data.db.cartItem.CartItemDao
import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.Product
import kotlin.concurrent.thread

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
        thread {
            database.deleteAll()
        }.join()
    }

    @Test
    fun `전체_장바구니_아이템을_불러올_수_있다`() {
        val item = CartItemEntity(0, Product(0, "상품", 1000, ""))
        var itemId = -1L
        thread {
            itemId = dao.saveCartItem(item)
        }.join()

        val insertedItem = item.copy(id = itemId)
        assertThat(dao.findAll()).isEqualTo(listOf(insertedItem))
    }

    @Test
    fun `선택한_아이템을_장바구니에_저장할_수_있다`() {
        val item = CartItemEntity(0, Product(0, "상품", 1000, ""))
        thread {
            dao.saveCartItem(item)
        }.join()

        val actual = dao.findAll().firstOrNull()?.product
        val expected = item.product
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `특정_ID로_장바구니_아이템을_불러올_수_있다`() {
        val item = CartItemEntity(0, Product(0, "상품", 1000, ""))
        var itemId = -1L
        thread {
            itemId = dao.saveCartItem(item)
        }.join()

        val actual = dao.findCartItemById(itemId)
        val expected = item.copy(id = itemId)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `특정_ID로_장바구니_아이템을_삭제할_수_있다`() {
        val item = CartItemEntity(0, Product(0, "상품", 1000, ""))
        var itemId = -1L
        thread {
            itemId = dao.saveCartItem(item)
        }.join()

        thread {
            dao.deleteCartItemById(itemId)
        }.join()

        val deletedItem = item.copy(id = itemId)
        assertThat(dao.findAll().contains(deletedItem)).isEqualTo(false)
    }
}
