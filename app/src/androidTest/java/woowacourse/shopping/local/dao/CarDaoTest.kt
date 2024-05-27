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
import woowacourse.shopping.local.CartDatabase
import woowacourse.shopping.local.entity.CartEntity
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class CarDaoTest {
    private lateinit var carDao: CarDao
    private lateinit var db: CartDatabase


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CartDatabase::class.java).build()
        carDao = db.dao()

        thread {
            carDao.clearAllCartItems()
        }.join()
    }

    @Test
    fun `선택한_상품을_장바구니에_저장할_수_있다`() {
        // given
        val item = CartEntity(0, Product(0, 1000, "상품", ""))
        val expected = item.product
        thread {
            carDao.saveItemCart(item)
        }.join()
        // when
        val actual = carDao.findAllCartItem().firstOrNull()?.product
        // then
        assertThat(actual).isNotNull()
        assertThat(actual?.id).isEqualTo(expected.id)
    }

    @Test
    fun `상품의_ID값으로_장바구니에서_상품을_불러올_수_있다`() {
        // given
        val item = CartEntity(0, Product(5, 1000, "상품", ""))
        thread {
            carDao.saveItemCart(item)
        }.join()
        // when
        val actual = carDao.findCartItemById(item.productId)?.product
        // then
        assertThat(actual).isEqualTo(item.product)
    }

    @Test
    fun `상품의_ID값으로_장바구니에서_상품을_삭제할_수_있다`() {
        // given
        val item = CartEntity(0, Product(5, 1000, "상품", ""))
        thread {
            carDao.saveItemCart(item)
        }.join()
        // when
        thread {
            carDao.clearCartItemById(item.productId)
        }.join()
        // then
        assertThat(carDao.findAllCartItem().contains(item)).isEqualTo(false)
    }
}
