package woowacourse.shopping.data.product.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.ShoppingCartDataBase
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {
    private lateinit var productDataBase: ShoppingCartDataBase
    private lateinit var productDao: ProductDao

    @Before
    fun setUp() {
        productDataBase =
            Room.databaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ShoppingCartDataBase::class.java,
                "products",
            ).build()
        productDataBase.clearAllTables()
        productDao = productDataBase.productDao()
    }

    @After
    fun tearDown() {
        productDataBase.clearAllTables()
        productDataBase.close()
    }

    @Test
    fun `상품의_id로_상품을_찾는다`() {
        // given
        val product = Product(imageUrl = imageUrl, title = title, price = price)
        val productId = productDao.insertAll(listOf(product)).first()

        // when
        val actual = productDao.findOrNull(productId)

        // then
        assertThat(actual).isNotNull
        assertThat(actual?.imageUrl).isEqualTo(imageUrl)
        assertThat(actual?.title).isEqualTo(title)
        assertThat(actual?.price).isEqualTo(price)
    }

    @Test
    fun `30개의_상품이_있고_첫_페이지를_불러오면_20개가_반환된다`() {
        // given
        val products = List(30) { Product(imageUrl = imageUrl, title = title, price = price) }
        productDao.insertAll(products)

        // when
        val actual = productDao.findRange(0, 20)

        // then
        assertThat(actual).hasSize(20)
    }

    @Test
    fun `5개의_상품이_있고_첫_페이지를_불러오면_5개가_반환된다`() {
        // given
        val products = List(5) { Product(imageUrl = imageUrl, title = title, price = price) }
        productDao.insertAll(products)

        // when
        val actual = productDao.findRange(0, 20)

        // then
        assertThat(actual).hasSize(5)
    }

    @Test
    fun `상품의_총_개수를_반환한다`() {
        // given
        val products = List(17) { Product(imageUrl = imageUrl, title = title, price = price) }
        productDao.insertAll(products)

        // when
        val actual = productDao.totalCount()

        // then
        assertThat(actual).isEqualTo(17)
    }

    @Test
    fun `5개의_상품을_저장한다`() {
        // given
        val products = List(5) { Product(imageUrl = imageUrl, title = title, price = price) }

        // when
        productDao.insertAll(products)

        // then
        val expected = productDao.totalCount()
        assertThat(5).isEqualTo(expected)
    }
}
