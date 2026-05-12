package woowacourse.shopping.data.repository

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.remote.datasource.ProductRemoteDataSource
import woowacourse.shopping.data.remote.dto.ProductResponse
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImplTest {
    private lateinit var repository: ProductRepository

    @BeforeEach
    fun setup() {
        repository = createRepository()
    }

    @Test
    fun getProducts() =
        runTest {
            val products = repository.getProducts()

            assertThat(products.productItems).hasSize(3)
            assertThat(products.productItems[0].productId).isEqualTo(1)
            assertThat(products.productItems[0].productName).isEqualTo("치킨")
            assertThat(products.productItems[0].price.value).isEqualTo(10000)
        }

    @Test
    fun getPagingProducts() =
        runTest {
            val products =
                repository.getPagingProducts(
                    page = 0,
                    pageSize = 2,
                )

            assertThat(products.productItems).hasSize(2)
            assertThat(products.productItems[0].productId).isEqualTo(1)
            assertThat(products.productItems[1].productId).isEqualTo(2)
        }

    @Test
    fun returnEmptyProductsWhenPageIsNegative() =
        runTest {
            val products =
                repository.getPagingProducts(
                    page = -1,
                    pageSize = 2,
                )

            assertThat(products.productItems).isEmpty()
        }

    @Test
    fun returnEmptyProductsWhenPageSizeIsZeroOrNegative() =
        runTest {
            val zeroPageSizeProducts =
                repository.getPagingProducts(
                    page = 0,
                    pageSize = 0,
                )

            val negativePageSizeProducts =
                repository.getPagingProducts(
                    page = 0,
                    pageSize = -1,
                )

            assertThat(zeroPageSizeProducts.productItems).isEmpty()
            assertThat(negativePageSizeProducts.productItems).isEmpty()
        }

    @Test
    fun returnEmptyProductsWhenPageIsOutOfRange() =
        runTest {
            val products =
                repository.getPagingProducts(
                    page = 10,
                    pageSize = 2,
                )

            assertThat(products.productItems).isEmpty()
        }

    @Test
    fun returnTrueWhenNextPageExists() =
        runTest {
            val hasNextPage =
                repository.hasNextPage(
                    currentPage = 0,
                    pageSize = 2,
                )

            assertThat(hasNextPage).isTrue()
        }

    @Test
    fun returnFalseWhenNextPageDoesNotExist() =
        runTest {
            val hasNextPage =
                repository.hasNextPage(
                    currentPage = 1,
                    pageSize = 2,
                )

            assertThat(hasNextPage).isFalse()
        }

    @Test
    fun findProductById() =
        runTest {
            val product = repository.findProductById(1)

            assertThat(product?.productId).isEqualTo(1)
            assertThat(product?.productName).isEqualTo("치킨")
        }
}

private fun createRepository(): ProductRepository =
    ProductRepositoryImpl(
        productRemoteDataSource =
            FakeProductRemoteDataSource(
                products =
                    listOf(
                        ProductResponse(
                            id = 1,
                            name = "치킨",
                            price = 10000,
                            imageUrl = "http://example.com/chicken.jpg",
                        ),
                        ProductResponse(
                            id = 2,
                            name = "피자",
                            price = 20000,
                            imageUrl = "http://example.com/pizza.jpg",
                        ),
                        ProductResponse(
                            id = 3,
                            name = "햄버거",
                            price = 15000,
                            imageUrl = "http://example.com/burger.jpg",
                        ),
                    ),
            ),
    )

private class FakeProductRemoteDataSource(
    private val products: List<ProductResponse>,
) : ProductRemoteDataSource {
    override suspend fun getProducts(): List<ProductResponse> = products

    override suspend fun getProduct(productId: Int): ProductResponse = products.first { it.id == productId }
}
