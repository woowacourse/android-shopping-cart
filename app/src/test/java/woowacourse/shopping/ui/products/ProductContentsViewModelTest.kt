package woowacourse.shopping.ui.products

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductContentsViewModelTest {
    private lateinit var viewModel: ProductContentsViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ProductContentsViewModel()
    }

    @Test
    fun `상품은 한 화면에 20개까지만 보여져야 한다`() {
        // when
        viewModel.loadProducts()
        // then
        assertThat(viewModel.products.getOrAwaitValue().size).isEqualTo(20)
    }

    @Test
    fun `첫번째 상품은 맥북이어야 한다`() {
        // when
        viewModel.loadProducts()
        // then
        assertThat(viewModel.products.getOrAwaitValue().find { it.id == 0L }?.name)
            .isEqualTo("맥북")
    }

    @Test
    fun `두번째 상품은 아이폰이어야 한다`() {
        // when
        viewModel.loadProducts()
        // then
        assertThat(viewModel.products.getOrAwaitValue().find { it.id == 1L }?.name)
            .isEqualTo("아이폰")
    }
}
