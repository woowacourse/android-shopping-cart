package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertTrue

class CartDaoTest {
    private lateinit var cartDao: CartDao
    private val cartItem: CartEntity = CartEntity(productId = 101)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database =
            Room
                .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        cartDao = database.cartDao()
        cartDao.insert(cartItem.copy(productId = 102))
        cartDao.insert(cartItem.copy(productId = 103))
        cartDao.insert(cartItem.copy(productId = 104))
    }

    @Test
    fun `장바구니의_모든_상품을_조회할_수_있다`() {
        // When
        val result = cartDao.getAll()
        val expected =
            listOf(
                cartItem.copy(productId = 102),
                cartItem.copy(productId = 103),
                cartItem.copy(productId = 104),
            )

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `장바구니에_담긴_상품의_수량을_조회할_수_있다`() {
        // When
        val result = cartDao.getTotalQuantity()
        val expected = 3

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `장바구니_상품_페이징_조회할_수_있다`() {
        // When
        val result = cartDao.getCartItemPaged(limit = 2, offset = 0)

        // Then
        assertAll(
            { assertThat(result.size).isEqualTo(2) },
            { assertThat(result[0].productId).isEqualTo(102) },
            { assertThat(result[1].productId).isEqualTo(103) },
        )
    }

    @Test
    fun `장바구니에서_특정_시간_이후에_담겨진_상품_존재_여부_확인할_수_있다`() {
        // Give
        val now = System.currentTimeMillis()
        val oldProduct = CartEntity(productId = 201, createdAt = now + 10000)
        cartDao.insert(oldProduct)

        // When
        val exists = cartDao.existsItemCreatedAfter(now)
        val notExists = cartDao.existsItemCreatedAfter(now + 20000)

        // Then
        assertAll(
            { assertTrue(exists) },
            { assertFalse(notExists) },
        )
    }

    @Test
    fun `장바구니에서_상품ID로_상품을_조회할_수_있다`() {
        // Give
        cartDao.insert(cartItem)

        // When
        val result = cartDao.findByProductId(101)

        // Then
        assertAll(
            { assertThat(result).isNotNull },
            { assertThat(result?.productId).isEqualTo(101) },
            { assertThat(result?.quantity).isEqualTo(1) },
        )
    }

    @Test
    fun `장바구니에_상품을_추가할_수_있다`() {
        // Give
        val cartItems = cartDao.getAll()

        // When
        cartDao.insert(cartItem)

        val resultSize = cartDao.getAll().size
        val result = cartDao.findByProductId(101)

        // Then
        assertAll(
            { assertThat(resultSize).isGreaterThan(cartItems.size) },
            { assertThat(result).isNotNull },
            { assertThat(result).isEqualTo(cartItem) },
        )
    }

    @Test
    fun `장바구니에서_특정_상품의_담은_수량을_증가시킬_수_있다`() {
        // Give
        cartDao.insert(cartItem)

        // When
        cartDao.updateQuantity(101, 1)
        val result = cartDao.findByProductId(101)
        val expected = 2

        // Then
        assertThat(result?.quantity).isEqualTo(expected)
    }

    @Test
    fun `장바구니에서_특정_상품의_담은_수량을_감소시킬_수_있다`() {
        // Give
        cartDao.insert(cartItem)
        cartDao.insertOrUpdate(cartItem, 2)

        // When
        cartDao.decreaseQuantity(101)
        val result = cartDao.findByProductId(101)
        val expected = 2

        // Then
        assertThat(result?.quantity).isEqualTo(expected)
    }

    @Test
    fun `상품ID로_장바구니에_담긴_상품을_제거할_수_있다`() {
        // Give
        cartDao.insert(cartItem)

        // When
        cartDao.delete(101)
        val result = cartDao.findByProductId(101)

        // Then
        assertThat(result).isNull()
    }

    @Test
    fun `이미_장바구니에_담긴_동일_상품이_있을_때_수량이_증가한다`() {
        // Give
        cartDao.insert(cartItem)
        cartDao.insertOrUpdate(cartItem, 1)

        // When
        val result = cartDao.findByProductId(101)
        val expected = 2

        // Then
        assertThat(result?.quantity).isEqualTo(expected)
    }

    @Test
    fun `장바구니에_1개_존재하는_상품의_담은_수량을_감소시키는_경우_장바구니에서_제거한다`() {
        // Give
        cartDao.insert(cartItem)

        // When
        cartDao.decreaseOrDelete(101)
        val result = cartDao.findByProductId(101)

        // Then
        assertThat(result).isNull()
    }
}
