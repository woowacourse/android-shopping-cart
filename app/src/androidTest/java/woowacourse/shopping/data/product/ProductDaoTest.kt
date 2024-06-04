package woowacourse.shopping.data.product

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.product.ProductDao
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.fixture.getFixtureProducts

class ProductDaoTest {
    private lateinit var database: ShoppingDatabase
    private lateinit var productDao: ProductDao

    @Before
    fun setUp() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    ShoppingDatabase::class.java,
                )
                .allowMainThreadQueries()
                .build()
        productDao = database.productDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `여러_개의_상품_정보를_삽입할_수_있다`() {
        // when
        val insertedIds = productDao.addAll(getFixtureProducts(count = 10))

        // then
        assertThat(insertedIds).isEqualTo(
            listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L),
        )
    }

    @Test
    fun `특정_페이지의_상품_정보를_불러온다`() {
        // given
        productDao.addAll(getFixtureProducts(count = 100))

        // when
        val products = productDao.getCartableProducts(0, 5)

        // then
        assertThat(products).isEqualTo(
            listOf(
                CartableProduct(Product(1, "사과1", "image1", 1000)),
                CartableProduct(Product(2, "사과2", "image2", 2000)),
                CartableProduct(Product(3, "사과3", "image3", 3000)),
                CartableProduct(Product(4, "사과4", "image4", 4000)),
                CartableProduct(Product(5, "사과5", "image5", 5000)),
            ),
        )
    }

    @Test
    fun `특정_상품_이이디의_상품_정보를_불러온다`() {
        // given
        productDao.addAll(getFixtureProducts(count = 100))

        // when
        val products = productDao.getCartableProduct(5)

        // then
        assertThat(products).isEqualTo(
            CartableProduct(Product(5, "사과5", "image5", 5000)),
        )
    }
}
