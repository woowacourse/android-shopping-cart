package woowacourse.shopping.data.history

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.history.ProductHistoryDao
import woowacourse.shopping.data.database.product.ProductDao
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.fixture.getFixtureHistoryItems
import woowacourse.shopping.fixture.getFixtureProducts

class ProductHistoryDaoTest {
    private lateinit var database: ShoppingDatabase
    private lateinit var productHistoryDao: ProductHistoryDao
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
        productHistoryDao = database.productHistoryDao()
        productDao = database.productDao()
        productDao.addAll(getFixtureProducts(20))
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `최근_본_상품_내역을_추가할_수_있다`() {
        // when
        val actualId =
            productHistoryDao.insertProductHistory(ProductHistory(id = 1, productId = 1))
        assertThat(actualId).isEqualTo(1L)
    }

    @Test
    fun `최근_본_상품_내역을_삭제할_수_있다`() {
        // when
        val actualCount =
            productHistoryDao.insertProductHistory(ProductHistory(id = 1, productId = 1))
        assertThat(actualCount).isEqualTo(1)
    }

    @Test
    fun `중복된_상품_내역을_추가하면_삭제_후_대체한다`() {
        // when
        productHistoryDao.addProductHistory(ProductHistory(productId = 1))
        productHistoryDao.addProductHistory(ProductHistory(productId = 2))
        productHistoryDao.addProductHistory(ProductHistory(productId = 1))

        // then
        val actualItems = productHistoryDao.getProductHistory(2)
        assertThat(actualItems).isEqualTo(
            listOf(
                RecentProduct(
                    productHistory = ProductHistory(id = 3, productId = 1),
                    product = Product(id = 1, name = "사과1", imageSource = "image1", price = 1000),
                ),
                RecentProduct(
                    productHistory = ProductHistory(id = 2, productId = 2),
                    product = Product(id = 2, name = "사과2", imageSource = "image2", price = 2000),
                ),
            ),
        )
    }

    @Test
    fun `특정_수만큼의_최근_조회_내역을_가져올_수_있다`() {
        // given
        val givenItems = getFixtureHistoryItems(20)
        givenItems.forEach {
            productHistoryDao.addProductHistory(it)
        }

        // when
        val actualItems = productHistoryDao.getProductHistory(5)

        // then
        assertThat(actualItems).isEqualTo(
            listOf(
                RecentProduct(
                    productHistory = ProductHistory(20, 20),
                    product = Product(20, "사과20", "image20", 20000),
                ),
                RecentProduct(
                    productHistory = ProductHistory(19, 19),
                    product = Product(19, "사과19", "image19", 19000),
                ),
                RecentProduct(
                    productHistory = ProductHistory(18, 18),
                    product = Product(18, "사과18", "image18", 18000),
                ),
                RecentProduct(
                    productHistory = ProductHistory(17, 17),
                    product = Product(17, "사과17", "image17", 17000),
                ),
                RecentProduct(
                    productHistory = ProductHistory(16, 16),
                    product = Product(16, "사과16", "image16", 16000),
                ),
            ),
        )
    }

    @Test
    fun `마지막으로_본_상품을_가져올_수_있다`() {
        // given
        val givenItems = getFixtureHistoryItems(20)
        givenItems.forEach {
            productHistoryDao.addProductHistory(it)
        }

        // when
        val actualItems = productHistoryDao.getLastProduct()

        // then
        assertThat(actualItems).isEqualTo(
            RecentProduct(ProductHistory(20, 20), Product(20, "사과20", "image20", 20000)),
        )
    }
}

/**
 * Trouble Shooting
 * 외래키로 엮여있는 두 테이블의 경우 일치하지 않는 데이터를 엮는 시도를 하면,
 * 다음과 같은 오류 발생
 * android.database.sqlite.SQLiteConstraintException: FOREIGN KEY constraint failed (code 787 SQLITE_CONSTRAINT_FOREIGNKEY)
 */
