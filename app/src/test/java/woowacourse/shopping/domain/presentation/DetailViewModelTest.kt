package woowacourse.shopping.domain.presentation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.fixture.dummyProductUiModelFixture
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.domain.util.InstantTaskExecutorExtension
import woowacourse.shopping.domain.util.getOrAwaitValue
import woowacourse.shopping.presentation.view.detail.DetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var cartRepository: FakeCartRepository
    private lateinit var productRepository: FakeProductRepository
    private lateinit var viewModel: DetailViewModel

    @BeforeEach
    fun setUp() {
        cartRepository = FakeCartRepository()
        productRepository = FakeProductRepository()
        viewModel = DetailViewModel(cartRepository, productRepository)
    }

    @Test
    fun `addCartItem하면 saveState가 바뀐다`() {
        val product = dummyProductUiModelFixture[1]
        viewModel.fetchProduct(product)

        viewModel.addCartItem()

        val result = viewModel.saveState.getOrAwaitValue()
        assertThat(result).isNotNull
    }

    @Test
    fun `상품의 수량을 1 증가시킬 수 있다`() {
        val amount = viewModel.amount.getOrAwaitValue()

        viewModel.increaseAmount()

        val updatedAmount = viewModel.amount.getOrAwaitValue()
        assertThat(updatedAmount).isEqualTo(amount + 1)
    }

    @Test
    fun `상품의 수량을 1 감소시킬 수 있다`() {
        viewModel.increaseAmount()
        val amount = viewModel.amount.getOrAwaitValue()

        viewModel.decreaseAmount()

        val updatedAmount = viewModel.amount.getOrAwaitValue()
        assertThat(updatedAmount).isEqualTo(amount - 1)
    }
}
