package woowacourse.shopping.viewmodel

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.TOTAL_PRODUCTS
import woowacourse.shopping.data.products.ProductRepository
import woowacourse.shopping.data.products.ProductRepositoryImpl
import woowacourse.shopping.view.products.ProductsViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel
    private lateinit var repository: ProductRepository

    @BeforeEach
    fun setUp() {
        repository = ProductRepositoryImpl()
        viewModel = ProductsViewModel(repository)
    }

    @Test
    fun `상품들이 다 보여지지 않았을 경우 더보기 버튼이 보인다`() {
        // given

        // when
        viewModel.updateButtonVisibility(true)

        // then
        val actual = viewModel.isLoadMoreButtonVisible.value
        actual shouldBe true
    }

    @Test
    fun `로드되지 않은 상품들이 최대 20개 단위로 이전에 로드된 상품들과 함께 누적 저장된다`() {
        // given

        // when
        viewModel.loadPage()

        // then
        val actual = viewModel.productsInShop.value
        actual shouldBe TOTAL_PRODUCTS
    }
}
