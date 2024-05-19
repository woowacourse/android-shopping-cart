package woowacourse.shopping.presentation.ui.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.ui.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingViewModelTest {
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var testShoppingRepository: ShoppingItemsRepository

    @Test
    fun `처음 상품 목록을 부를 때 오류가 있다면 Error 상태로 변경한다`() {
        // given
        testShoppingRepository = ErrorShoppingRepositoryImpl()
        viewModel = ShoppingViewModel(testShoppingRepository)

        // when
        viewModel.loadProducts()

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Error::class.java)
    }

    @Test
    fun `처음 상품 목록을 부를 때 데이터가 없다면 Empty 상태로 변경한다`() {
        // given
        testShoppingRepository = EmptyShoppingRepositoryImpl()
        viewModel = ShoppingViewModel(testShoppingRepository)

        // when
        viewModel.loadProducts()

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Empty::class.java)
    }

    @Test
    fun `처음 상품 목록을 부를 때 데이터가 있다면 Success 상태로 변경한다`() {
        // given
        testShoppingRepository = FakeShoppingRepositoryImpl()
        viewModel = ShoppingViewModel(testShoppingRepository)

        // when
        viewModel.loadProducts()

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)
    }

    @Test
    fun `처음 상품 목록 20개를 불러온다`() {
        // given
        testShoppingRepository = FakeShoppingRepositoryImpl()
        viewModel = ShoppingViewModel(testShoppingRepository)

        // when
        viewModel.loadProducts()

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)

        if (state is UIState.Success) {
            assertEquals(20, state.data.size)
        }
    }

    @Test
    fun `데이터가 더 존재한다면 더보기 버튼이 보인다 `() {
        // given
        testShoppingRepository = FakeShoppingRepositoryImpl()
        viewModel = ShoppingViewModel(testShoppingRepository)

        // when
        viewModel.loadProducts()

        // then
        val isVisible = viewModel.loadMoreVisibility.getOrAwaitValue()
        assertThat(isVisible).isTrue()
    }

    @Test
    fun `데이터가 더 존재하지 않는다면 더보기 버튼이 보이지 않는다`() {
        // given
        testShoppingRepository = FakeShoppingRepositoryImpl()
        viewModel = ShoppingViewModel(testShoppingRepository)

        // when
        viewModel.loadProducts()
        viewModel.onLoadMoreButtonClick()

        // then
        val isVisible = viewModel.loadMoreVisibility.getOrAwaitValue()
        assertThat(isVisible).isFalse()
    }
}
