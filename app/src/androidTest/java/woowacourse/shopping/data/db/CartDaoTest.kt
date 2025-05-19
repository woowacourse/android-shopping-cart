package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertTrue

class CartDaoTest {
    private lateinit var cartDao: CartDao
    private val cartItem: CartEntity =
        CartEntity(
            cartId = 1,
            productId = 101,
        )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database =
            Room
                .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        cartDao = database.cartDao()
    }

    @Test
    fun `상품ID로_조회할_수_있다`() {
        cartDao.insert(cartItem)

        val result = cartDao.findByProductId(101)

        assertAll(
            { assertThat(result).isNotNull },
            { assertThat(result?.productId).isEqualTo(101) },
            { assertThat(result?.quantity).isEqualTo(1) },
        )
    }

    @Test
    fun `상품_수량을_증가시킬_수_있다`() {
        cartDao.insert(cartItem)
        cartDao.updateQuantity(101)

        val updated = cartDao.findByProductId(101)
        assertEquals(2, updated?.quantity)
    }

    @Test
    fun `이미_장바구니에_담긴_동일_상품이_있을_때_수량이_증가한다`() {
        cartDao.insert(cartItem)
        cartDao.insertOrUpdate(cartItem)

        val updated = cartDao.findByProductId(101)
        assertThat(updated?.quantity).isEqualTo(2)
    }

    @Test
    fun `특정_시간_이후에_담겨진_상품_존재_여부_확인할_수_있다`() {
        val now = System.currentTimeMillis()
        val oldProduct = CartEntity(cartId = 1, productId = 201, createdAt = now + 10000)
        cartDao.insert(oldProduct)

        val exists = cartDao.existsItemCreatedAfter(now)
        val notExists = cartDao.existsItemCreatedAfter(now + 20000)

        assertAll(
            { assertTrue(exists) },
            { assertFalse(notExists) },
        )
    }

    @Test
    fun `장바구니_상품_페이징_조회할_수_있다`() {
        val baseTime = System.currentTimeMillis()
        val products =
            (1..5).map {
                CartEntity(
                    cartId = it.toLong(),
                    productId = it.toLong(),
                    createdAt = baseTime + it,
                )
            }
        products.forEach { cartDao.insert(it) }

        val result = cartDao.getCartItemPaged(limit = 2, offset = 0)

        assertAll(
            { assertThat(result.size).isEqualTo(2) },
            { assertThat(result[0].productId).isEqualTo(1) },
            { assertThat(result[1].productId).isEqualTo(2) },
        )
    }

    @Test
    fun `상품ID로_장바구니에_담긴_상품을_제거할_수_있다`() {
        cartDao.insert(cartItem)

        cartDao.delete(1)
        val result = cartDao.findByProductId(301)

        assertThat(result).isNull()
    }
}
