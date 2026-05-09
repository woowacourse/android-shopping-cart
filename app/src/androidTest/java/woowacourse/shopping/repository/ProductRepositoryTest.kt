package woowacourse.shopping.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.repository.dao.ProductDao

@RunWith(AndroidJUnit4::class)
class ProductRepositoryTest {
    private val product = Product("1", ProductTitle("호날두"), Price(1_0000), "")

    private lateinit var database: AndroidShoppingDatabase
    private lateinit var productDao: ProductDao

    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        runBlocking {
            val context = ApplicationProvider.getApplicationContext<Context>()
            database =
                Room
                    .inMemoryDatabaseBuilder(context, AndroidShoppingDatabase::class.java)
                    .allowMainThreadQueries()
                    .build()
            productDao = database.productDao()
            productRepository = DatabaseProductRepository(productDao = productDao)
            productDao.addProduct("호날두", 1_0000, "")
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    // A_상품이_저장되어 있는 레파지토리에서 모든 상품을 꺼내오면 A 상품이 조회된다
    @Test
    fun only_A_stored_repository_getProducts_return_A() =
        runTest {
            assertEquals(productRepository.getProducts(0, 20).single(), product)
        }

    // id값이 2인 상품이 저장되어 있지 않은 레파지토리에서 id값이 2인 상품을 조회하면 null이 반환된다
    @Test
    fun repository_getProduct_return_null_about_unstored_product() =
        runTest {
            assertEquals(productRepository.getProduct("2"), null)
        }

    // A 상품이 저장된 레파지토리에서 동일 상품의 id로 조회하면 A 상품이 조회된다
    @Test
    fun repository_getProduct_return_product() =
        runTest {
            assertEquals(productRepository.getProduct("1"), product)
        }

    // 1개의 상품이 저장된 레파지토리의 전체 크기는 1이다
    @Test
    fun only_1_stored_repository_return_totalSize_1() =
        runTest {
            assertEquals(productRepository.totalSize(), 1)
        }
}
