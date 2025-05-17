package woowacourse.shopping.presentation.view

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixture.dummyProductsFixture
import woowacourse.shopping.fixture.fakeCartRepository
import woowacourse.shopping.fixture.fakeProductRepository
import woowacourse.shopping.presentation.model.toProduct
import woowacourse.shopping.presentation.view.detail.DetailViewModel
import woowacourse.shopping.presentation.view.util.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.view.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = DetailViewModel(fakeCartRepository(), fakeProductRepository)
    }

    @Test
    fun `상품 ID에 해당하는 상품을 조회한다`() {
        viewModel.fetchProduct(1)

        val result = viewModel.product.getOrAwaitValue().toProduct()
        val expected = dummyProductsFixture.find { it.id == 1L }

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `상품을 장바구니에 추가하면 저장 상태가 변경된다`() {
        viewModel.fetchProduct(1)

        viewModel.addProduct()

        val result = viewModel.saveState.getValue()

        assertThat(result).isNotNull
    }
}
