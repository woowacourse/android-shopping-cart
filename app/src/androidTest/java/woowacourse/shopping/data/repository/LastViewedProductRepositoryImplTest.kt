package woowacourse.shopping.data.repository

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.LastViewedProductRepository
import java.io.File

class LastViewedProductRepositoryImplTest {
    private lateinit var testFile: File

    @After
    fun tearDown() {
        if (::testFile.isInitialized && testFile.exists()) {
            testFile.delete()
        }
    }

    @Test
    fun saveLastViewedProduct() =
        runTest {
            val repository = createRepository()
            val product = ProductFixture.productList.first()

            repository.saveLastViewedProduct(product)

            val lastViewedProduct = repository.getLastViewedProduct()

            assertThat(lastViewedProduct).isEqualTo(product)
        }

    @Test
    fun returnNullWhenLastViewedProductDoesNotExist() =
        runTest {
            val repository = createRepository()
            val lastViewedProduct = repository.getLastViewedProduct()
            assertThat(lastViewedProduct).isNull()
        }

    @Test
    fun replaceLastViewedProductWhenSavingAnotherProduct() =
        runTest {
            val repository = createRepository()

            val firstProduct = ProductFixture.productList[0]
            val secondProduct = ProductFixture.productList[1]

            repository.saveLastViewedProduct(firstProduct)
            repository.saveLastViewedProduct(secondProduct)

            val lastViewedProduct = repository.getLastViewedProduct()

            assertThat(lastViewedProduct).isEqualTo(secondProduct)
        }

    private fun TestScope.createRepository(): LastViewedProductRepository {
        val context = ApplicationProvider.getApplicationContext<Context>()

        testFile = File(context.filesDir, "test_preferences.preferences_pb")
        if (testFile.exists()) testFile.delete()

        val dataStore =
            PreferenceDataStoreFactory.create(
                scope = backgroundScope,
                produceFile = { testFile },
            )

        return LastViewedProductRepositoryImpl(
            dataStore = dataStore,
            productRepository =
                FakeProductRepository(
                    products = Products(ProductFixture.productList),
                ),
        )
    }
}
