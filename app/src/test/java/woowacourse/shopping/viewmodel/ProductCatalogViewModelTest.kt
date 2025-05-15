import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixture.FakeProductRepository
import woowacourse.shopping.view.product.catalog.ProductCatalogViewModel
import woowacourse.shopping.viewmodel.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductCatalogViewModelTest {
    private lateinit var viewModel: ProductCatalogViewModel

    @BeforeEach
    fun setup() {
        viewModel = ProductCatalogViewModel(FakeProductRepository())
    }

    @Test
    fun `초기 로드시 첫 페이지 상품이 로드되어야 한다`() {
        // given
        val products = viewModel.products.value

        // when & then
        assertEquals(20, products?.items?.size)
        assertEquals("Product 0", products?.items?.first()?.name)
        assertEquals(true, products?.hasNext)
    }

    @Test
    fun `loadMoreProducts 호출 시 다음 페이지 상품이 로드되어야 한다`() {
        // given
        viewModel.loadMoreProducts()
        val products = viewModel.products.value

        // when & then
        assertEquals(20, products?.items?.size)
        assertEquals("Product 20", products?.items?.first()?.name)
        assertEquals(true, products?.hasNext)
    }

    @Test
    fun `마지막 페이지까지 로딩 후에는 hasNext가 false여야 한다`() {
        // given
        repeat(4) { viewModel.loadMoreProducts() }

        // when & then
        val products = viewModel.products.value
        assertEquals(20, products?.items?.size)
        assertEquals("Product 80", products?.items?.first()?.name)
        assertEquals(false, products?.hasNext)
    }
}
