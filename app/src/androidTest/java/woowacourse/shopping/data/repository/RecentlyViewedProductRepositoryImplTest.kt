package woowacourse.shopping.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.data.local.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.local.database.ShoppingDatabase
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository

class RecentlyViewedProductRepositoryImplTest {
    private lateinit var database: ShoppingDatabase
    private lateinit var dao: RecentlyViewedProductDao
    private lateinit var productRepository: ProductRepository
    private lateinit var repository: RecentlyViewedProductRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    ShoppingDatabase::class.java,
                ).allowMainThreadQueries()
                .build()

        dao = database.recentlyViewedProductDao()
        productRepository =
            FakeProductRepository(
                products =
                    Products(
                        ProductFixture.productList,
                    ),
            )

        repository =
            RecentlyViewedProductRepositoryImpl(
                dao = dao,
            )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveRecentlyViewedProduct() =
        runTest {
            val product = ProductFixture.productList.first()

            repository.viewProduct(product)

            val recentlyViewedProducts = repository.getRecentlyViewedProducts()

            assertThat(recentlyViewedProducts.productItems).containsExactly(product)
        }

    @Test
    fun returnRecentlyViewedProductsInLatestOrder() =
        runTest {
            val product1 = ProductFixture.productList[0]
            val product2 = ProductFixture.productList[1]

            repository.viewProduct(product1)
            delay(1)
            repository.viewProduct(product2)

            val recentlyViewedProducts = repository.getRecentlyViewedProducts()

            assertThat(recentlyViewedProducts.productItems).containsExactly(product2, product1)
        }

    @Test
    fun moveExistingProductToFrontWhenViewedAgain() =
        runTest {
            val product1 = ProductFixture.productList[0]
            val product2 = ProductFixture.productList[1]
            val product3 = ProductFixture.productList[2]

            repository.viewProduct(product1)
            delay(1)
            repository.viewProduct(product2)
            delay(1)
            repository.viewProduct(product3)
            delay(1)
            repository.viewProduct(product2)

            val recentlyViewedProducts = repository.getRecentlyViewedProducts()

            assertThat(recentlyViewedProducts.productItems).containsExactly(product2, product3, product1)
        }

    @Test
    fun keepOnlyLatestTenRecentlyViewedProducts() =
        runTest {
            ProductFixture.productList.take(11).forEachIndexed { index, product ->
                repository.viewProduct(product)
                delay(1)
            }

            val recentlyViewedProducts = repository.getRecentlyViewedProducts()

            assertThat(recentlyViewedProducts.productItems).hasSize(10)
            assertThat(recentlyViewedProducts.productItems.first()).isEqualTo(ProductFixture.productList[10])
            assertThat(recentlyViewedProducts.productItems.last()).isEqualTo(ProductFixture.productList[1])
        }
}
