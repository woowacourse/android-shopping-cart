package woowacourse.shopping.cart

import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension

// TODO: 변경된 뷰모델로 테스트
@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
//    private lateinit var viewModel: ShoppingCartViewModel

//    @Test
//    fun `장바구니에 담긴 상품이 없을 때`() {
//        // given
//        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(mutableListOf()))
//
//        // when
//        val cartItems = viewModel.itemsInCurrentPage.value
//
//        // then
//        assertThat(cartItems).isEmpty()
//    }
//
//    @Test
//    fun `장바구니에 담긴 상품이 총 3개일 때 첫 페이지 장바구니 개수는 3 `() {
//        // given
//        val fakeProducts = productsTestFixture(3).toMutableList()
//
//        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))
//
//        // when
//        val cartItems = viewModel.itemsInCurrentPage.value
//
//        // then
//        assertThat(cartItems).hasSize(3)
//    }
//
//    @Test
//    fun `장바구니에 담긴 상품이 총 8개 일 때 (5개 이상일 때) 첫 페이지 장바구니 개수는 5`() {
//        // given
//        val fakeProducts = productsTestFixture(8).toMutableList()
//        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))
//
//        // when
//        val cartItems = viewModel.itemsInCurrentPage.value
//
//        // then
//        assertThat(cartItems).hasSize(5)
//    }
//
//    @Test
//    fun `장바구니에 담긴 상품이 총 3개일 때 첫 페이지는 마지막 페이지이다`() {
//        // given
//        val fakeProducts = productsTestFixture(3).toMutableList()
//        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))
//
//        // then
//        assertThat(viewModel.isLastPage.value).isTrue
//    }
//
//    @Test
//    fun `장바구니에 담긴 상품이 총 6개일 때 첫 페이지는 마지막 페이지가 아니다`() {
//        // given
//        val fakeProducts = productsTestFixture(6).toMutableList()
//        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))
//
//        // then
//        assertThat(viewModel.isLastPage.value).isFalse
//    }
//
//    @Test
//    fun `장바구니에 담긴 상품이 총 8 개 일 때 첫 페이지에서 다음 페이지로 가면 마지막 페이지가 된다`() {
//        // given
//        val fakeProducts = productsTestFixture(6).toMutableList()
//        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))
//
//        // when
//        viewModel.nextPage()
//
//        // then
//        assertThat(viewModel.isLastPage.getOrAwaitValue()).isTrue
//    }
//
//    @Test
//    fun `장바구니에 담긴 상품이 총 11 개일 때 첫 페이지에서 다음 페이지로 가면 마지막 페이지가 아니다`() {
//        // given
//        val fakeProducts = productsTestFixture(11).toMutableList()
//        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))
//
//        // when
//        viewModel.nextPage()
//
//        // then
//        assertThat(viewModel.isLastPage.getOrAwaitValue()).isFalse
//    }
//
//    @Test
//    fun `장바구니에 담긴 상품이 6개일 때 이전 페이지로 간다`() {
//        // given
//        val fakeProducts = productsTestFixture(6).toMutableList()
//        viewModel =
//            ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts), _currentPage = MutableLiveData(2))
//
//        // when
//        viewModel.previousPage()
//
//        // then
//        assertThat(viewModel.currentPage.getOrAwaitValue()).isEqualTo(1)
//    }
//
//    @Test
//    fun `2번째 페이지에서 로드되는 데이터 5개`() {
//        // given
//        val fakeProducts = productsTestFixture(11).toMutableList()
//        viewModel =
//            ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts), _currentPage = MutableLiveData(2))
//
//        // when
//        val cartItems = viewModel.itemsInCurrentPage.getOrAwaitValue()
//
//        // then
//        val expected = productsTestFixture(5, productFixture = { productTestFixture(it + 5) })
//        assertThat(cartItems).containsExactlyElementsOf(expected)
//    }
//
//    @Test
//    fun `2번째 페이지에서 1번째 페이지로 왔을 때 로드되는 데이터`() {
//        // given
//        val fakeProducts = productsTestFixture(11).toMutableList()
//        viewModel =
//            ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts), _currentPage = MutableLiveData(2))
//
//        // when
//        viewModel.previousPage()
//        val cartItems = viewModel.itemsInCurrentPage.getOrAwaitValue()
//
//        // then
//        val expected = productsTestFixture(5, productFixture = { productTestFixture(it) })
//        assertThat(cartItems).isEqualTo(expected)
//    }
//
//    @Test
//    fun `세번째 페이지에서 로드되는 데이터 3개`() {
//        // given
//        val fakeProducts = productsTestFixture(13).toMutableList()
//        viewModel =
//            ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts), _currentPage = MutableLiveData(3))
//
//        // when
//        val cartItems = viewModel.itemsInCurrentPage.getOrAwaitValue()
//
//        // then
//        val expected = productsTestFixture(3, productFixture = { productTestFixture(it + 10) })
//        assertThat(cartItems).isEqualTo(expected)
//    }
//
//    @Test
//    fun `상품 삭제 시 현 페이지가 변경된다`() {
//        // given
//        val fakeProducts = productsTestFixture(13).toMutableList()
//        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))
//
//        // when
//        viewModel.deleteItem(1)
//
//        // then
//        val cartItems = viewModel.itemsInCurrentPage.getOrAwaitValue()
//        val expected =
//            mutableListOf(
//                productTestFixture(id = 0),
//                productTestFixture(id = 2),
//                productTestFixture(id = 3),
//                productTestFixture(id = 4),
//                productTestFixture(id = 5),
//            )
//        assertThat(cartItems).isEqualTo(expected)
//    }
//
//    @Test
//    fun `상품 삭제 시 현재 페이지가 마지막 페이지가 된다`() {
//        // given
//        val fakeProducts = productsTestFixture(6).toMutableList()
//        viewModel =
//            ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts), _currentPage = MutableLiveData(1))
//
//        // when
//        viewModel.deleteItem(1)
//
//        // then
//        assertThat(viewModel.isLastPage.getOrAwaitValue()).isTrue
//    }
}
