package woowacourse.shopping.viewmodel.product

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.repository.CartProductRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.fixture.FakeCartProductRepository
import woowacourse.shopping.fixture.FakeProductRepository
import woowacourse.shopping.fixture.FakeRecentProductRepository
import woowacourse.shopping.view.product.catalog.ProductCatalogViewModel
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogItem
import woowacourse.shopping.viewmodel.InstantTaskExecutorExtension
import woowacourse.shopping.viewmodel.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductCatalogViewModelTest {
    private lateinit var viewModel: ProductCatalogViewModel
    private lateinit var productRepository: ProductRepository
    private lateinit var cartProductRepository: CartProductRepository
    private lateinit var recentProductRepository: RecentProductRepository

    @BeforeEach
    fun setup() {
        productRepository = FakeProductRepository()
        cartProductRepository = FakeCartProductRepository()
        recentProductRepository = FakeRecentProductRepository()
        viewModel =
            ProductCatalogViewModel(
                productRepository,
                cartProductRepository,
                recentProductRepository,
            )
        viewModel.loadCatalog()
    }

    @Test
    fun `초기 로드 시 첫 페이지의 상품과 더보기 버튼이 포함된다`() {
        // when
        val items = viewModel.productCatalogItems.getOrAwaitValue()

        // then
        assertEquals(21, items.size) // 20 ProductItem + 1 LoadMoreItem
        assert(items.first() is ProductCatalogItem.ProductItem)
        assert(items.last() is ProductCatalogItem.LoadMoreItem)
    }

    @Test
    fun `더보기 버튼 클릭 시 다음 페이지의 상품이 추가되고 더보기 버튼이 포함된다`() {
        // when
        viewModel.onMoreClick()
        val items = viewModel.productCatalogItems.getOrAwaitValue()

        // then
        assertEquals(41, items.size) // 40 ProductItem + 1 LoadMoreItem
        assert(items.last() is ProductCatalogItem.LoadMoreItem)
    }

    @Test
    fun `마지막 페이지 이후에는 더보기 버튼이 없다`() {
        // when
        repeat(4) { viewModel.onMoreClick() }
        val items = viewModel.productCatalogItems.getOrAwaitValue()

        // then
        assertEquals(100, items.size)
        assert(items.none { it is ProductCatalogItem.LoadMoreItem })
    }

    @Test
    fun `더보기 후에도 이전 페이지 상품이 유지된다`() {
        viewModel.onMoreClick()

        val items =
            viewModel.productCatalogItems
                .getOrAwaitValue()
                .filterIsInstance<ProductCatalogItem.ProductItem>()
                .map { it.product }

        assertEquals("Product 0", items.first().name)
        assertEquals("Product 39", items.last().name)
    }

    @Test
    fun `상품 수량 증가 클릭 시 수량이 1 증가한다`() {
        // given
        val product =
            viewModel.productCatalogItems
                .getOrAwaitValue()
                .filterIsInstance<ProductCatalogItem.ProductItem>()
                .first()
                .product
        val item = ProductCatalogItem.ProductItem(product, 1)

        // when
        viewModel.onQuantityIncreaseClick(item)

        // then
        val updatedItem =
            viewModel.productCatalogItems
                .getOrAwaitValue()
                .filterIsInstance<ProductCatalogItem.ProductItem>()
                .first { it.product.id == item.product.id }

        assertEquals(2, updatedItem.quantity)
    }

    @Test
    fun `상품 수량 감소 클릭 시 수량이 1 감소한다`() {
        // given
        val product =
            viewModel.productCatalogItems
                .getOrAwaitValue()
                .filterIsInstance<ProductCatalogItem.ProductItem>()
                .first()
                .product
        val item = ProductCatalogItem.ProductItem(product, 2)

        // when
        viewModel.onQuantityDecreaseClick(item)

        // then
        val updatedItem =
            viewModel.productCatalogItems
                .getOrAwaitValue()
                .filterIsInstance<ProductCatalogItem.ProductItem>()
                .first { it.product.id == item.product.id }

        assertEquals(1, updatedItem.quantity)
    }

    @Test
    fun `상품 수량 변경 시 총 수량에 반영된다`() {
        // given
        val product =
            viewModel.productCatalogItems
                .getOrAwaitValue()
                .filterIsInstance<ProductCatalogItem.ProductItem>()
                .first()
                .product
        val item = ProductCatalogItem.ProductItem(product, 2)
        val totalQuantity = viewModel.totalQuantity.getOrAwaitValue()

        // when
        viewModel.onQuantityIncreaseClick(item)
        viewModel.onQuantityIncreaseClick(item)

        // then
        assertEquals(2, viewModel.totalQuantity.getOrAwaitValue() - totalQuantity)
    }

    @Test
    fun `상품 클릭 시 선택된 상품이 selectedProduct에 반영된다`() {
        // given
        val product =
            viewModel.productCatalogItems
                .getOrAwaitValue()
                .filterIsInstance<ProductCatalogItem.ProductItem>()
                .first()
                .product
        val item = ProductCatalogItem.ProductItem(product, 0)

        // when
        viewModel.onProductClick(item)

        // then
        assertEquals(product, viewModel.selectedProduct.getValue())
    }
}
