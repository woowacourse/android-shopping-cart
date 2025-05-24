package woowacourse.shopping.data.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.db.dao.CartDao
import woowacourse.shopping.data.db.entity.CartEntity
import woowacourse.shopping.fixture.cartEntityFixture1
import woowacourse.shopping.fixture.cartEntityFixture2
import woowacourse.shopping.fixture.cartEntityFixture4
import woowacourse.shopping.fixture.cartEntityFixtures
import woowacourse.shopping.fixture.fakeContext

@RunWith(AndroidJUnit4::class)
class CartDaoTest {
    private lateinit var db: PetoMarketDatabase
    private lateinit var cartDao: CartDao

    @Before
    fun setUp() {
        db =
            Room.databaseBuilder(
                fakeContext,
                PetoMarketDatabase::class.java,
                "peto_market_database",
            )
                .allowMainThreadQueries()
                .build()

        cartDao = db.cartDao()
    }

    @Test
    fun `새로운_상품을_추가한다`() {
        // when
        val cart = cartEntityFixture1

        // given
        cartDao.insert(cart)
        val expected = cartDao.cartByProductId(1)

        expected?.productId shouldBe cart.productId
    }

    @Test
    fun `상품이_이미_존재하면_새로운_수량으로_수정한다`() {
        // when
        val cart = cartEntityFixture2

        cartDao.insert(cart)
        val expected = cartDao.cartByProductId(2)

        expected?.productId shouldBe cart.productId

        // given
        cartDao.upsert(CartEntity(2, 10))

        // then
        val updated = cartDao.cartByProductId(2)
        updated?.quantity shouldBe 10
    }

    @Test
    fun `상품을_제거한다`() {
        // gieven
        val cart = cartEntityFixture4
        cartDao.insert(cart)

        // when
        cartDao.deleteByProductId(4)

        // then
        val expected = cartDao.cartByProductId(4)
        expected shouldBe null
    }

    @Test
    fun `첫_번쟤_페이자의_장바구니_상품울_5개_가져온다`() {
        // given
        cartEntityFixtures.forEach {
            cartDao.insert(it)
        }

        // when
        val expected = cartDao.cartSinglePage(0, 5)

        // then
        expected shouldBe cartEntityFixtures
    }

    @Test
    fun `장바구니_전체_페이지_개수를_가져온다`() {
        // given
        cartEntityFixtures.forEach {
            cartDao.insert(it)
        }

        // when
        val expected = cartDao.pageCount(5)

        // then
        expected shouldBe 1
    }

    @After
    fun tearDown() {
        db.close()
    }
}
