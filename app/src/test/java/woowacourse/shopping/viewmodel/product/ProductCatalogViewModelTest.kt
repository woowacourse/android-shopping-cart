package woowacourse.shopping.viewmodel.product

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.fixture.FakeProductRepository
import woowacourse.shopping.view.product.catalog.ProductCatalogViewModel
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogItem
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
    fun `초기 로드 시 첫 페이지의 상품과 더보기 버튼이 포함된다`() {
        // when
        val items = viewModel.productItems.value.orEmpty()

        // then
        assertEquals(21, items.size) // 20 ProductItem + 1 LoadMoreItem
        assert(items.first() is ProductCatalogItem.ProductItem)
        assert(items.last() is ProductCatalogItem.LoadMoreItem)
    }

    @Test
    fun `더보기 버튼 클릭 시 다음 페이지의 상품이 추가되고 더보기 버튼이 포함된다`() {
        // when
        viewModel.onMoreClick()
        val items = viewModel.productItems.value.orEmpty()

        // then
        assertEquals(41, items.size) // 40 ProductItem + 1 LoadMoreItem
        assert(items.last() is ProductCatalogItem.LoadMoreItem)
    }

    @Test
    fun `마지막 페이지 이후에는 더보기 버튼이 없다`() {
        // when
        repeat(4) { viewModel.onMoreClick() }
        val items = viewModel.productItems.value.orEmpty()

        // then
        assertEquals(100, items.size)
        assert(items.none { it is ProductCatalogItem.LoadMoreItem })
    }

    @Test
    fun `더보기 후에도 이전 페이지 상품이 유지된다`() {
        viewModel.onMoreClick()

        val items =
            viewModel.productItems.value
                .orEmpty()
                .filterIsInstance<ProductCatalogItem.ProductItem>()
                .map { it.product }

        assertEquals("Product 0", items.first().name)
        assertEquals("Product 39", items.last().name)
    }

    @Test
    fun `상품 클릭 시 선택된 상품이 selectedProduct에 반영된다`() {
        // given
        val product = repository.getAll().first()

        // when
        viewModel.onProductClick(product)

        // then
        assertEquals(product, viewModel.selectedProduct.value)
    }
}
