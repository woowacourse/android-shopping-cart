package woowacourse.shopping.productlist

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.shopping.repository.DatabaseProductRepository
import woowacourse.shopping.repository.dao.ProductDao
import woowacourse.shopping.repository.entity.ProductEntity
import kotlin.math.min

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ParameterizedTest
    @CsvSource("20, 20", "15, 15", "25, 20")
    fun `로드된 상품 개수에 따라 최대 20개까지 반환한다`(
        itemSize: Int,
        expectedItemSize: Int,
    ) = runTest {
        val productListViewModel =
            ProductListViewModel(
                productRepository =
                    DatabaseProductRepository(
                        productDao = MockProductDao(itemSize),
                    ),
            )

        productListViewModel.loadProducts()

        advanceUntilIdle()

        productListViewModel.uiState.value.productUiModels.size shouldBe expectedItemSize
    }

    @ParameterizedTest
    @CsvSource("20, 1, 20", "25, 2, 25", "50, 2, 40")
    fun `다음 페이지로 이동하면 기존 상품에 추가된 상품을 반환한다`(
        itemSize: Int,
        pageMoveCount: Int,
        expectedItemSize: Int,
    ) = runTest {
        val productListViewModel =
            ProductListViewModel(
                productRepository =
                    DatabaseProductRepository(
                        productDao = MockProductDao(itemSize),
                    ),
            )

        repeat(pageMoveCount) {
            productListViewModel.loadProducts()
        }

        advanceUntilIdle()

        productListViewModel.uiState.value.productUiModels.size shouldBe expectedItemSize
    }
}

private class MockProductDao(
    itemSize: Int,
) : ProductDao {
    private val products =
        List(itemSize) { index ->
            ProductEntity(
                id = index + 1,
                price = 10_000,
                name = "호날두",
                imageUrl = "",
            )
        }

    override suspend fun addProduct(
        name: String,
        price: Int,
        imageUrl: String,
    ): Unit = throw UnsupportedOperationException("쓸 필요가 없습니다")

    override suspend fun getProducts(
        offset: Int,
        size: Int,
    ): List<ProductEntity> = products.subList(offset, min(offset + size, products.size))

    override suspend fun getTotalSize(): Int = products.size

    override suspend fun getProductEntity(id: Int): ProductEntity? = products.find { it.id == id }
}
