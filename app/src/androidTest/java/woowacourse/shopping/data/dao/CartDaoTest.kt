package woowacourse.shopping.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.entity.CartProductEntity

@Suppress("ktlint:standard:function-naming")
class CartDaoTest {
    private lateinit var database: ShoppingDatabase
    private lateinit var dao: CartDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room
                .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        dao = database.cartDao()
    }

    @Test
    fun 상품_ID에_해당하는_상품을_불러온다() {
        // given
        val cartProduct = CartProductEntity(productId = 3, quantity = 2)
        dao.insertCartProduct(cartProduct)

        // when
        val cartProductDetail = dao.getCartProductDetailById(3)

        // then
        assertThat(cartProductDetail?.cartProduct?.productId).isEqualTo(3)
        assertThat(cartProductDetail?.cartProduct?.quantity).isEqualTo(2)
    }

    @Test
    fun 장바구니에_상품을_저장한다() {
        // given
        val cartProduct = CartProductEntity(productId = 1, quantity = 3)

        // when
        dao.insertCartProduct(cartProduct)

        // then
        val result = dao.getCartProductById(1)
        assertThat(result).isEqualTo(cartProduct)
    }

    @Test
    fun ID에_해당하는_상품을_장바구니에서_삭제한다() {
        // given
        val cartProduct = CartProductEntity(productId = 2, quantity = 5)
        dao.insertCartProduct(cartProduct)

        // when
        dao.deleteCartProduct(2)

        // then
        val result = dao.getCartProductById(2)
        assertThat(result).isNull()
    }

    @Test
    fun 페이지에_따른_장바구니_상품_목록을_불러온다() {
        // given
        val cartProducts = (1..10).map { CartProductEntity(productId = it, quantity = it) }
        cartProducts.forEach { dao.insertCartProduct(it) }

        // when
        val page1 = dao.getCartProductDetails(page = 1, size = 3)
        val page2 = dao.getCartProductDetails(page = 2, size = 3)

        // then
        assertThat(page1).hasSize(3)
        assertThat(page1[0].cartProduct.productId).isEqualTo(1)
        assertThat(page2[0].cartProduct.productId).isEqualTo(4)
    }

    @Test
    fun 장바구니의_총_상품_수를_불러온다() {
        // given
        dao.insertCartProduct(CartProductEntity(productId = 1, quantity = 1))
        dao.insertCartProduct(CartProductEntity(productId = 2, quantity = 2))

        // when
        val count = dao.getCartItemCount()

        // then
        assertThat(count).isEqualTo(2)
    }

    @Test
    fun 장바구니의_총_페이지_수를_불러온다() {
        // given
        (1..11).forEach { dao.insertCartProduct(CartProductEntity(productId = it, quantity = 1)) }

        // when
        val totalPage = dao.getTotalPageCount(5)

        // then
        assertThat(totalPage).isEqualTo(3)
    }

    @After
    fun tearDown() {
        database.close()
    }
}
