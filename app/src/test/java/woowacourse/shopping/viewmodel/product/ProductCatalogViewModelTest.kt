package woowacourse.shopping.viewmodel.product

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.fixture.FakeProductRepository
import woowacourse.shopping.view.product.catalog.ProductCatalogViewModel
import woowacourse.shopping.viewmodel.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductCatalogViewModelTest {
    private lateinit var viewModel: ProductCatalogViewModel
    private lateinit var repository: ProductRepository

    @BeforeEach
    fun setup() {
        repository = FakeProductRepository()
        viewModel = ProductCatalogViewModel(repository)
    }

    @Test
    fun `초기 로드시 첫 페이지의 상품 목록을 로드한다`() {
        // when
        val products = viewModel.products.value

        // then
        assertEquals(20, products?.size)
        assertEquals("Product 0", products?.first()?.name)
        assertEquals(true, viewModel.hasNext)
    }

    @Test
    fun `더보기 버튼 클릭 시 다음 페이지의 상품이 추가된다`() {
        // when
        viewModel.onMoreClick()
        val products = viewModel.products.value

        // then
        assertEquals(40, products?.size)
        assertEquals(true, viewModel.hasNext)
    }

    @Test
    fun `이전 페이지의 상품이 다음 페이지 로드 후에도 유지된다`() {
        // when
        viewModel.onMoreClick()

        // then
        val products = viewModel.products.value!!
        assertEquals("Product 0", products[0].name)
        assertEquals("Product 21", products[21].name)
    }

    @Test
    fun `마지막 페이지까지 로딩 후에는 hasNext가 false여야 한다`() {
        // when
        repeat(4) { viewModel.onMoreClick() }

        // then
        val products = viewModel.products.value
        assertEquals(100, products?.size)
        assertEquals(false, viewModel.hasNext)
    }

    @Test
    fun `상품 클릭 시 selectedProduct에 반영된다`() {
        // given
        val product = repository.getAll().first()

        // when
        viewModel.onProductClick(product)

        // then
        assertEquals(product, viewModel.selectedProduct.value)
    }
}
