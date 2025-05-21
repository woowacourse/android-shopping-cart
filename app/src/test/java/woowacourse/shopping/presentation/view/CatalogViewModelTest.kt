package woowacourse.shopping.presentation.view

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.fixture.FakeShoppingRepository
import woowacourse.shopping.fixture.dummyProductsFixture
import woowacourse.shopping.presentation.view.catalog.CatalogViewModel
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem
import woowacourse.shopping.presentation.view.util.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.view.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CatalogViewModelTest {
    private lateinit var viewModel: CatalogViewModel
    private lateinit var fakeProductRepository: ShoppingRepository

    @BeforeEach
    fun setUp() {
        fakeProductRepository =
            FakeShoppingRepository(
                dummyProductsFixture,
                mutableMapOf(),
            )
        viewModel = CatalogViewModel(fakeProductRepository)
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
        viewModel.fetchProducts()
        val observedItems = viewModel.products.getOrAwaitValue()
        val distinctCount =
            observedItems.map { (it as CatalogItem.ProductItem).product.productId }.distinct().count()

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
}
