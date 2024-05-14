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
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.Product

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
    fun `아이템_전체_조회_테스트`() {
        val item = CartItemEntity(0, Product(0,"상품",1000,""))
        val itemId = dao.saveCartItem(item)

        val insertedItem = item.copy(id = itemId)
        assertThat(dao.findAll()).isEqualTo(listOf(insertedItem))
    }

    @Test
    fun `아이템_저장_테스트`() {
        val item = CartItemEntity(0, Product(0,"상품",1000,""))
        dao.saveCartItem(item)

        val actual = dao.findAll().firstOrNull()?.product
        val expected = item.product
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `아이템_조회_테스트`() {
        val item = CartItemEntity(0, Product(0,"상품",1000,""))
        val itemId = dao.saveCartItem(item)

        val actual = dao.findCartItemById(itemId)
        val expected = item.copy(id = itemId)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `선택한_아이템_삭제_테스트`() {
        val item = CartItemEntity(0, Product(0,"상품",1000,""))
        val itemId = dao.saveCartItem(item)

        dao.deleteCartItemById(itemId)

        val deletedItem = item.copy(id = itemId)
        assertThat(dao.findAll().contains(deletedItem)).isEqualTo(false)
    }
}
