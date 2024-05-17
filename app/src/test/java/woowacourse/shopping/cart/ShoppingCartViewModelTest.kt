package woowacourse.shopping.cart

import androidx.lifecycle.MutableLiveData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.repository.FakeShoppingCartItemRepository

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel

    @Test
    fun `장바구니에 담긴 상품이 없을 때`() {
        // given
        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(mutableListOf()))

        // when
        val cartItems = viewModel.itemsInCurrentPage.value

        // then
        assertThat(cartItems).isEmpty()
    }

    @Test
    fun `장바구니에 담긴 상품이 총 3개일 때 첫 페이지 장바구니 개수는 3 `() {
        // given
        val fakeProducts =
            mutableListOf(
                productTestFixture(id = 1),
                productTestFixture(id = 2),
                productTestFixture(id = 3),
            )

        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))

        // when
        val cartItems = viewModel.itemsInCurrentPage.value

        // then
        assertThat(cartItems).hasSize(3)
    }

    @Test
    fun `장바구니에 담긴 상품이 총 8개 일 때 (5개 이상일 때) 첫 페이지 장바구니 개수는 5`() {
        // given
        val fakeProducts =
            mutableListOf(
                productTestFixture(id = 1),
                productTestFixture(id = 2),
                productTestFixture(id = 3),
                productTestFixture(id = 4),
                productTestFixture(id = 5),
                productTestFixture(id = 6),
                productTestFixture(id = 7),
                productTestFixture(id = 8),
            )
        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))

        // when
        val cartItems = viewModel.itemsInCurrentPage.value

        // then
        assertThat(cartItems).hasSize(5)
    }

    @Test
    fun `장바구니에 담긴 상품이 총 3개일 때 첫 페이지는 마지막 페이지이다`() {
        // given
        val fakeProducts =
            mutableListOf(
                productTestFixture(id = 1),
                productTestFixture(id = 2),
                productTestFixture(id = 3),
            )
        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))

        // then
        assertThat(viewModel.isLastPage.value).isTrue
    }

    @Test
    fun `장바구니에 담긴 상품이 총 6개일 때 첫 페이지는 마지막 페이지가 아니다`() {
        // given
        val fakeProducts =
            mutableListOf(
                productTestFixture(id = 1),
                productTestFixture(id = 2),
                productTestFixture(id = 3),
                productTestFixture(id = 4),
                productTestFixture(id = 5),
                productTestFixture(id = 6),
            )
        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))

        // then
        assertThat(viewModel.isLastPage.value).isFalse
    }

    @Test
    fun `장바구니에 담긴 상품이 총 8 개 일 때 첫 페이지에서 다음 페이지로 가면 마지막 페이지가 된다`() {
        // given
        val fakeProducts =
            mutableListOf(
                productTestFixture(id = 1),
                productTestFixture(id = 2),
                productTestFixture(id = 3),
                productTestFixture(id = 4),
                productTestFixture(id = 5),
                productTestFixture(id = 6),
            )
        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))

        // when
        viewModel.nextPage()

        // then
        assertThat(viewModel.isLastPage.getOrAwaitValue()).isTrue
    }

    @Test
    fun `장바구니에 담긴 상품이 총 11 개일 때 첫 페이지에서 다음 페이지로 가면 마지막 페이지가 아니다`() {
        // given
        val fakeProducts =
            mutableListOf(
                productTestFixture(id = 1),
                productTestFixture(id = 2),
                productTestFixture(id = 3),
                productTestFixture(id = 4),
                productTestFixture(id = 5),
                productTestFixture(id = 6),
                productTestFixture(id = 7),
                productTestFixture(id = 8),
                productTestFixture(id = 9),
                productTestFixture(id = 10),
                productTestFixture(id = 11),
            )
        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))

        // when
        viewModel.nextPage()

        // then
        assertThat(viewModel.isLastPage.getOrAwaitValue()).isFalse
    }

    @Test
    fun `장바구니에 담긴 상품이 6개일 때 이전 페이지로 간다`() {
        // given
        val fakeProducts =
            mutableListOf(
                productTestFixture(id = 1),
                productTestFixture(id = 2),
                productTestFixture(id = 3),
                productTestFixture(id = 4),
                productTestFixture(id = 5),
                productTestFixture(id = 6),
            )
        viewModel =
            ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts), _currentPage = MutableLiveData(2))

        // when
        viewModel.previousPage()

        // then
        assertThat(viewModel.currentPage.getOrAwaitValue()).isEqualTo(1)
    }
}
