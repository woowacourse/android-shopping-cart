package woowacourse.shopping.ui.productlist

import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ui.productlist.viewmodel.ProductListViewModel

class ProductListViewModelTest {

    private lateinit var viewModel: ProductListViewModel
    private lateinit var repository: MockProductRepository

    @BeforeEach
    fun setUp() {
        repository = MockProductRepository()
        viewModel = ProductListViewModel(repository)
    }

    @Test
    fun `초기 첫 페이지 상품을 불러온다`() = runTest {
        advanceUntilIdle()
        val uiState = viewModel.uiState.value
        assertEquals(20, uiState.products.size)
    }

    @Test
    fun `fetchProducts를 통해 다음 페이지 상품을 추가한다`() = runTest {
        advanceUntilIdle()
        viewModel.fetchProducts()
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(35, uiState.products.size)
    }

    @Test
    fun `fetchProducts를 반복 호출해도 전체 개수를 초과하지 않는다`() = runTest {
        advanceUntilIdle()
        viewModel.fetchProducts()
        viewModel.fetchProducts()
        viewModel.fetchProducts()
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(35, uiState.products.size)
    }

    @Test
    fun `fetchProducts를 한번 호출하면 page Size 만큼 불러온다`() = runTest {
        advanceUntilIdle()
        // 초기 20개 불러옴
        viewModel.fetchProducts(5)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(25, uiState.products.size)
    }

    @Test
    fun `잘못된 PAGE SIZE -1가 입력되면 애러가 발생한다`() {
        assertThrows<IllegalArgumentException> { viewModel.fetchProducts(-1) }
    }

    @Test
    fun `잘못된 PAGE SIZE 0가 입력되면 애러가 발생한다`() {
        assertThrows<IllegalArgumentException> { viewModel.fetchProducts(0) }
    }
}

class MockProductRepository : ProductRepository {
    private val products = (1..35).map {
        Product(
            id = "$it",
            name = "상품$it",
            price = Money(it * 1000),
            imageUrl = "",
        )
    }
    override suspend fun getProducts(): List<Product> = products

    override suspend fun getProduct(id: String): Product? = products.find { it.hasId(id) }
}
