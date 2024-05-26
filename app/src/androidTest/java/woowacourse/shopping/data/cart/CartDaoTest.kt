package woowacourse.shopping.data.cart

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.kotest.assertions.any
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.cart.CartDao
import woowacourse.shopping.data.database.product.ProductDao
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.fixture.getFixtureCartItems
import woowacourse.shopping.fixture.getFixtureProducts

class CartDaoTest {
    private lateinit var database: ShoppingDatabase
    private lateinit var cartDao: CartDao
    private lateinit var productDao: ProductDao

    @Before
    fun setUp() {
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ShoppingDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()
        cartDao = database.cartDao()
        productDao = database.productDao()
        productDao.addAll(getFixtureProducts(100))
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `장바구니에_상품을_담을_수_있다`() {
        // given
        val cartItems = getFixtureCartItems(5)

        // when
        val actualId = cartDao.addCartItem(cartItems.last())

        // then
        assertThat(actualId).isEqualTo(5L)
    }

    @Test
    fun `페이지_및_페이지당_아이템_수에_해당되는_장바구니_상품_정보를_가져올_수_있다`() {
        // when
        val cartItems = getFixtureCartItems(5)
        cartItems.forEach(cartDao::addCartItem)

        // then
        val actualItems = cartDao.getCartedProducts(0, 5)
        assertThat(actualItems).isEqualTo(
            listOf(
                CartedProduct(
                    CartItem(1, 1, 1),
                    Product(1, "사과1", "image1", 1000)
                ),
                CartedProduct(
                    CartItem(2, 2, 2),
                    Product(2, "사과2", "image2", 2000)
                ),
                CartedProduct(
                    CartItem(3, 3, 3),
                    Product(3, "사과3", "image3", 3000)
                ),
                CartedProduct(
                    CartItem(4, 4, 4),
                    Product(4, "사과4", "image4", 4000)
                ),
                CartedProduct(
                    CartItem(5, 5, 5),
                    Product(5, "사과5", "image5", 5000)
                ),
            )
        )
    }

    @Test
    fun `장바구니_상품_정보를_삭제할_수_있다`() {
        // given
        val cartItems = getFixtureCartItems(5)
        cartItems.forEach(cartDao::addCartItem)

        // when
        cartDao.deleteCartItem(CartItem(1, 1, 1))

        // then
        val actualResult = cartDao.getCartedProducts(0, 5)
        assertThat(actualResult).doesNotContain(
            CartedProduct(
                CartItem(1, 1, 1),
                Product(1, "사과1", "image1", 1000),
            ),
        )
    }

    @Test
    fun `장바구니에_담긴_상품_수량의_총합을_가져올_수_있다`() {
        // given
        val cartItems = getFixtureCartItems(5)
        cartItems.forEach(cartDao::addCartItem)

        // when
        val actualCount = cartDao.getTotalQuantity()

        // then
        assertThat(actualCount).isEqualTo(15)
    }

    @Test
    fun `장바구니에_담긴_상품_수량을_변경할_수_있다`() {
        // given
        val cartItems = getFixtureCartItems(5)
        cartItems.forEach(cartDao::addCartItem)

        // when
        cartDao.updateQuantity(1, 2)

        // then
        val actualItems = cartDao.getCartedProducts(0, 5)
        assertThat(actualItems).contains(
            CartedProduct(
                CartItem(1, 1, 2),
                Product(1, "사과1", "image1", 1000)
            )
        )
    }
}
