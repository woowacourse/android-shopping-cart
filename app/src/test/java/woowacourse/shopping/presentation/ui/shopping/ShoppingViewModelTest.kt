package woowacourse.shopping.presentation.ui.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.DummyShoppingItems
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
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
        val dataSource = FakeShoppingItemsDataSource(DummyShoppingItems.items, throwError = true)
        testShoppingRepository = ShoppingItemsRepositoryImpl(dataSource)
        viewModel = ShoppingViewModel(testShoppingRepository)

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Error::class.java)
    }

    @Test
    fun `처음 상품 목록을 부를 때 데이터가 없다면 Empty 상태로 변경한다`() {
        // given
        val dataSource = FakeShoppingItemsDataSource(emptyList())
        testShoppingRepository = ShoppingItemsRepositoryImpl(dataSource)
        viewModel = ShoppingViewModel(testShoppingRepository)

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Empty::class.java)
    }

    @Test
    fun `처음 상품 목록을 부를 때 데이터가 있다면 Success 상태로 변경한다`() {
        // given
        val dataSource = FakeShoppingItemsDataSource(DummyShoppingItems.items)
        testShoppingRepository = ShoppingItemsRepositoryImpl(dataSource)
        viewModel = ShoppingViewModel(testShoppingRepository)

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)
    }

    @Test
    fun `처음 상품 목록 20개를 불러온다`() {
        // given
        val dataSource = FakeShoppingItemsDataSource(DummyShoppingItems.items)
        testShoppingRepository = ShoppingItemsRepositoryImpl(dataSource)
        viewModel = ShoppingViewModel(testShoppingRepository)

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)

        if (state is UIState.Success) {
            assertEquals(20, state.data.size)
        }
    }

    @Test
    fun `더보기 버튼을 클릭하면 20개의 상품이 더 보인다 `() {
        // given
        val dataSource = FakeShoppingItemsDataSource(DummyShoppingItems.items)
        testShoppingRepository = ShoppingItemsRepositoryImpl(dataSource)
        viewModel = ShoppingViewModel(testShoppingRepository)

        // when
        viewModel.onLoadMoreButtonClick()

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)

        if (state is UIState.Success) {
            assertEquals(20, state.data.size)
        }
    }

    @Test
    fun `데이터가 20개보다 작다면 그 데이터의 수만큼 보인다`() {
        // given
        val dataSource = FakeShoppingItemsDataSource(DummyShoppingItems.items)
        testShoppingRepository = ShoppingItemsRepositoryImpl(dataSource)
        viewModel = ShoppingViewModel(testShoppingRepository)

        // when
        viewModel.onLoadMoreButtonClick()
        viewModel.onLoadMoreButtonClick()

        // then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)

        if (state is UIState.Success) {
            assertEquals(17, state.data.size)
        }
    }
}
