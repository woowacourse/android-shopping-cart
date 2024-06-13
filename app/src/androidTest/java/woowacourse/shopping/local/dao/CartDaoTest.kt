package woowacourse.shopping.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.domain.Product
import woowacourse.shopping.local.AppDatabase
import woowacourse.shopping.local.entity.CartEntity
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class CartDaoTest {
    private lateinit var cartDao: CartDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        cartDao = db.cartDao()

        thread {
            cartDao.clearAllCartItems()
        }.join()
    }

    @Test
    fun `선택한_상품을_장바구니에_저장할_수_있다`() {
        // given
        val item = CartEntity(0, Product(0, 1000, "상품", ""))
        val expected = item.product
        thread {
            cartDao.saveItemCart(item)
        }.join()
        // when
        val actual = cartDao.findAllCartItem().firstOrNull()?.product
        // then
        assertThat(actual).isNotNull()
        assertThat(actual?.id).isEqualTo(expected.id)
    }

    @Test
    fun `상품의_ID값으로_장바구니에서_상품을_불러올_수_있다`() {
        // given
        val item = CartEntity(0, Product(5, 1000, "상품", ""))
        thread {
            cartDao.saveItemCart(item)
        }.join()
        // when
        val actual = cartDao.findCartItemById(item.productId)?.product
        // then
        assertThat(actual).isEqualTo(item.product)
    }

    @Test
    fun `상품의_ID값으로_장바구니에서_상품을_삭제할_수_있다`() {
        // given
        val item = CartEntity(0, Product(5, 1000, "상품", ""))
        thread {
            cartDao.saveItemCart(item)
        }.join()
        // when
        thread {
            cartDao.clearCartItemById(item.productId)
        }.join()
        // then
        assertThat(cartDao.findAllCartItem().contains(item)).isEqualTo(false)
    }
}
