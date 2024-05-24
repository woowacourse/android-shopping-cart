package woowacourse.shopping.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.cart.dao.CartDao
import woowacourse.shopping.data.cart.database.CartDataBase
import woowacourse.shopping.data.cart.entity.CartItem
import woowacourse.shopping.model.Quantity
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class CartDaoTest {
    private lateinit var cartDataBase: CartDataBase
    private lateinit var cartDao: CartDao

    @Before
    fun setUp() {
        cartDataBase = Room.databaseBuilder(ApplicationProvider.getApplicationContext(), CartDataBase::class.java, "cart").build()
        cartDao = cartDataBase.cartDao()
    }

    @After
    fun tearDown() {
        cartDataBase.clearAllTables()
        cartDataBase.close()
    }

    @Test
    fun `카트_아이템을_저장한다`() {
        // given
        val cartItem = CartItem(productId = 0L, quantity = Quantity(10))

        // when
        cartDao.insertCartItem(cartItem)

        // then
        val actual = cartDao.findCartItem(productId = 0L)
        assertThat(actual.productId).isEqualTo(0L)
        assertThat(actual.quantity).isEqualTo(Quantity(10))
    }

    @Test
    fun `카트_아이템의_수량을_변경한다`() {
        // given
        val cartItem = CartItem(productId = 0L, quantity = Quantity(10))
        cartDao.insertCartItem(cartItem)

        // when
        cartDao.changeQuantity(productId = 0L, quantity = Quantity(1))

        // then
        val actual = cartDao.findCartItem(productId = 0L)
        assertThat(actual.quantity).isEqualTo(Quantity(1))
    }

    @Test
    fun `카트_아이템을_삭제한다`() {
        // given
        val cartItem = CartItem(productId = 0L, quantity = Quantity(10))
        cartDao.insertCartItem(cartItem)

        // when
        cartDao.deleteCartItem(productId = 0L)

        // then
        assertThrows(IllegalArgumentException::class.java) {
            assertThat(cartDao.findCartItem(productId = 0L)).isNull()
        }
    }

    @Test
    fun `상품_아이디에_맞는_카트_아이템을_찾는다`() {
        // given
        val cartItem = CartItem(productId = 0L, quantity = Quantity(10))
        val cartItemId = cartDao.insertCartItem(cartItem)

        // when
        val actual = cartDao.findCartItem(productId = 0L)

        // then
        assertThat(actual.id).isEqualTo(cartItemId)
        assertThat(actual.productId).isEqualTo(0L)
        assertThat(actual.quantity).isEqualTo(Quantity(10))
    }

    @Test
    fun `카트_아이템이_15개_저장되어_있고_첫_페이지를_불러오면_5개가_반환된다`() {
        // given
        val cartItems = List(10) { CartItem(productId = 0L, quantity = Quantity(10)) }
        cartDao.insertAllCartItem(cartItems)

        // when
        val actual = cartDao.findCartItemRange(0, 5)

        // then
        assertThat(actual).hasSize(5)
    }

    @Test
    fun `카트_아이템이_3개_저장되어_있고_첫_페이지를_불러오면_3개가_반환된다`() {
        // given
        val cartItems = List(3) { CartItem(productId = 0L, quantity = Quantity(10)) }
        cartDao.insertAllCartItem(cartItems)

        // when
        val actual = cartDao.findCartItemRange(0, 5)

        // then
        assertThat(actual).hasSize(3)
    }

    @Test
    fun `카트_아이템의_총_개수를_반환한다`() {
        // given
        val cartItems = List(22) { CartItem(productId = 0L, quantity = Quantity(10)) }
        cartDao.insertAllCartItem(cartItems)

        // when
        val actual = cartDao.totalCartItemCount()

        // then
        assertThat(actual).isEqualTo(22)
    }

    private fun CartDao.insertAllCartItem(cartItems: List<CartItem>) {
        cartItems.forEach {
            insertCartItem(it)
        }
    }
}
