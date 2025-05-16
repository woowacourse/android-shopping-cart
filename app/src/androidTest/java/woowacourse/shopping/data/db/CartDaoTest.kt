package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertAll
import woowacourse.shopping.data.mapper.toProduct
import woowacourse.shopping.data.mapper.toProductEntity
import woowacourse.shopping.fixture.dummyProductsFixture

class CartDaoTest {
    private lateinit var cartDao: CartDao

    @Before
    fun setup() {
        val fakeContext = ApplicationProvider.getApplicationContext<Context>()
        val database =
            Room
                .inMemoryDatabaseBuilder(fakeContext, ShoppingDatabase::class.java)
                .build()

        cartDao = database.cartDao()

        dummyProductsFixture.take(10).forEach {
            cartDao.insert(it.toProductEntity())
        }
    }

    @Test
    fun `데이터베이스에_상품을_추가할_수_있다`() {
        val newProduct = dummyProductsFixture[11].toProductEntity()

        cartDao.insert(newProduct)

        val cartItems = cartDao.getCartItemPaged(1, 10)
        assertThat(cartItems).contains(newProduct)
    }

    @Test
    fun `데이터베이스에서_상품을_삭제할_수_있다`() {
        val productToDelete = dummyProductsFixture[0].toProductEntity()

        cartDao.delete(productToDelete.id)

        val cartItems = cartDao.getCartItemPaged(10, 0)
        assertThat(cartItems).doesNotContain(productToDelete)
    }

    @Test
    fun `데이터베이스에서_offset_을_기준으로_limit_수만큼_추가_상품을_조회할_수_있다`() {
        val limit = 5
        val offset = 5

        val cartItems = cartDao.getCartItemPaged(limit, offset).map { it.toProduct() }
        val expectedItems = dummyProductsFixture.subList(offset, offset + limit)

        assertAll(
            { assertThat(cartItems.size).isEqualTo(limit) },
            { assertThat(cartItems).containsAll(expectedItems) },
        )
    }

    @Test
    fun `이후_상품이_존재하면_true를_반환한다`() {
        val id = 5L

        val exists = cartDao.existsItemAfterId(id)

        assertThat(exists).isTrue()
    }

    @Test
    fun `이후_상품이_없으면_false를_반환한다`() {
        val id = 10L

        val exists = cartDao.existsItemAfterId(id)

        assertThat(exists).isFalse()
    }
}
