package woowacourse.shopping.presentation.view

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixture.FakeCartRepository
import woowacourse.shopping.fixture.FakeProductRepository
import woowacourse.shopping.fixture.FakeRecentProductRepository
import woowacourse.shopping.presentation.model.CatalogItem
import woowacourse.shopping.presentation.view.catalog.CatalogViewModel
import woowacourse.shopping.presentation.view.util.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.view.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CatalogViewModelTest {
    private lateinit var viewModel: CatalogViewModel

    @BeforeEach
    fun setUp() {
        val fakeProductRepository = FakeProductRepository()
        val fakeCartRepository = FakeCartRepository()
        val fakeRecentProductRepository =
            FakeRecentProductRepository(
                initialRecentProductIds = listOf(1, 2, 3),
            )
        viewModel =
            CatalogViewModel(fakeProductRepository, fakeCartRepository, fakeRecentProductRepository)
    }

    @Test
    fun `초기화 시 상품 목록이 로드된다`() {
        // When
        val observedItems = viewModel.products.getOrAwaitValue()

        // Then
        assertAll(
            { assertThat(observedItems).isNotNull },
            { assertThat(observedItems).isNotEmpty },
        )
    }

    @Test
    fun `새로운 상품 조회 시 중복 없이 제품 목록이 누적된다`() {
        // When
        viewModel.loadProducts()
        val observedItems =
            viewModel.products.getOrAwaitValue().filterIsInstance<CatalogItem.ProductItem>()
        val distinctCount =
            observedItems
                .map { it.productId }
                .distinct()
                .count()

        // Then
        assertAll(
            { assertThat(observedItems.size).isGreaterThanOrEqualTo(2) },
            { assertThat(observedItems.size).isEqualTo(distinctCount) },
        )
    }

    @Test
    fun `다음 페이지에 조회 가능한 상품이 존재하는 경우 더보기 버튼이 포함된다`() {
        // When
        val observedItems = viewModel.products.getOrAwaitValue()
        val lastProduct = observedItems.lastOrNull()

        // Then
        assertThat(lastProduct).isInstanceOf(CatalogItem.LoadMoreItem::class.java)
    }

    @Test
    fun `최근에 본 상품 목록을 조회할 수 있다`() {
        // When
        val observedItems = viewModel.products.getOrAwaitValue()
        val recentProducts =
            observedItems.filterIsInstance<CatalogItem.RecentProducts>().firstOrNull()

        // Then
        assertThat(recentProducts).isNotNull
    }

    @Test
    fun `특정 상품의 장바구니 담은 개수를 증가시킬 수 있다`() {
        // When
        val productId = 1L
        viewModel.addProductToCart(productId)

        // Then
        val observedItems =
            viewModel.products.getOrAwaitValue().filterIsInstance<CatalogItem.ProductItem>()
        val productItem = observedItems.find { it.productId == productId }
        assertThat(productItem?.quantity).isGreaterThan(0)
    }

    @Test
    fun `특정 상품의 장바구니 담은 개수를 감소시킬 수 있다`() {
        // Give
        val productId = 1L
        viewModel.addProductToCart(productId)
        val beforeObservedItems =
            viewModel.products.getOrAwaitValue().filterIsInstance<CatalogItem.ProductItem>()
        val beforeQuantity = beforeObservedItems.find { it.productId == productId }?.quantity ?: 0

        // When
        viewModel.removeProductFromCart(productId)

        // Then
        val afterObservedItems =
            viewModel.products.getOrAwaitValue().filterIsInstance<CatalogItem.ProductItem>()
        val afterQuantity = afterObservedItems.find { it.productId == productId }?.quantity ?: 0
        assertThat(afterQuantity).isLessThanOrEqualTo(beforeQuantity)
    }
}
