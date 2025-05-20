package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.product.ProductResult
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.fixture.productFixture3
import woowacourse.shopping.fixture.productFixture4
import woowacourse.shopping.view.main.vm.MainViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: MainViewModel
    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun setup() {
        productRepository = mockk()
    }

    @Test
    fun `초기화시_첫_페이지를_로드한다`() {
        // given
        val expected = ProductResult(listOf(productFixture1, productFixture2), false)
        every { productRepository.loadSinglePage(0, 20) } returns expected

        // when
        viewModel = MainViewModel(productRepository)

        // then
        assertEquals(expected.products, viewModel.uiState.getOrAwaitValue().items)
    }

    @Test
    fun `다음_페이지를_로드하고_기존_목록에_추가한다`() {
        // given
        val page1 = ProductResult(listOf(productFixture1, productFixture2), false)
        val page2 = ProductResult(listOf(productFixture3, productFixture4), true)

        every { productRepository.loadSinglePage(0, 20) } returns page1
        every { productRepository.loadSinglePage(1, 20) } returns page2

        // when
        viewModel = MainViewModel(productRepository)

        viewModel.loadProducts()

        // then
        val expected = listOf(productFixture1, productFixture2, productFixture3, productFixture4)
        assertEquals(expected, viewModel.uiState.getOrAwaitValue().items)

        verifySequence {
            productRepository.loadSinglePage(0, 20)
            productRepository.loadSinglePage(1, 20)
        }
    }
}
