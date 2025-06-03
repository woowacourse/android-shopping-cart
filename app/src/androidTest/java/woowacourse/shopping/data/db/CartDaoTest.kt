package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertAll
import woowacourse.shopping.data.mapper.toCartEntity
import woowacourse.shopping.data.mapper.toProduct
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.fixture.dummyCartItemFixture

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
    }

    @Test
    fun `상품_수량을_1_증가시킬_수_있다`() {
        val target = dummyCartItemFixture[1].toCartEntity()
        cartDao.insert(target)

        cartDao.increaseAmount(target.id)

        val result = cartDao.getByProductId(target.id)
        assertThat(result?.amount).isEqualTo(target.amount + 1)
    }

    @Test
    fun `상품_수량을_1_감소시킬_수_있다`() {
        val target = dummyCartItemFixture[1].toCartEntity()
        cartDao.insert(target)

        cartDao.increaseAmount(target.id)

        val result = cartDao.getByProductId(target.id)
        assertThat(result?.amount).isEqualTo(target.amount + 1)
    }

    @Test
    fun `데이터베이스에서_상품을_삭제할_수_있다`() {
        val productToDelete = dummyCartItemFixture[1].toCartEntity()

        cartDao.delete(productToDelete.id)

        val cartItems = cartDao.getCartItemPaged(10, 0)
        assertThat(cartItems).doesNotContain(productToDelete)
    }

    @Test
    fun `장바구니에_동일한_상품이_없으면_새로_추가한다`() {
        val newProduct =
            CartItem(
                Product(
                    id = 6,
                    name = "리자몽",
                    imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000601.png",
                    Price(10_000),
                ),
                amount = 1,
            ).toCartEntity()

        cartDao.upsert(newProduct)

        val result = cartDao.getByProductId(newProduct.id)
        assertThat(result).isEqualTo(newProduct)
    }

    @Test
    fun `장바구니에_동일한_상품이_이미_있으면_수량을_더한다`() {
        val existing = dummyCartItemFixture[0].toCartEntity()
        cartDao.insert(existing)
        val additional = existing.copy(amount = 2)

        cartDao.upsert(additional)

        val result = cartDao.getByProductId(existing.id)
        assertThat(result?.amount).isEqualTo(existing.amount + 2)
    }

    @Test
    fun `데이터베이스에서_offset_을_기준으로_limit_수만큼_추가_상품을_조회할_수_있다`() {
        dummyCartItemFixture.forEach {
            cartDao.insert(it.toCartEntity())
        }

        val limit = 2
        val offset = 2

        val cartItems = cartDao.getCartItemPaged(limit, offset).map { it.toProduct() }
        val expectedItems = dummyCartItemFixture.map { it.product }.subList(offset, offset + limit)

        assertAll(
            { assertThat(cartItems.size).isEqualTo(limit) },
            { assertThat(cartItems).containsAll(expectedItems) },
        )
    }

    @Test
    fun `이후_상품이_존재하면_true를_반환한다`() {
        dummyCartItemFixture.forEach {
            cartDao.insert(it.toCartEntity())
        }

        val id = 1L

        val exists = cartDao.existsItemAfterId(id)

        assertThat(exists).isTrue()
    }

    @Test
    fun `이후_상품이_없으면_false를_반환한다`() {
        val id = 5L

        val exists = cartDao.existsItemAfterId(id)

        assertThat(exists).isFalse()
    }
}
