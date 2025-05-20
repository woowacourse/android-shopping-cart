package woowacourse.shopping.viewmodel.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.fixture.FakeCartProductRepository
import woowacourse.shopping.view.cart.ShoppingCartViewModel
import woowacourse.shopping.viewmodel.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel
    private lateinit var repository: CartProductRepository

    @BeforeEach
    fun setup() {
        repository = FakeCartProductRepository()
        repeat(12) { id -> repository.insert(id.toLong()) }
        viewModel = ShoppingCartViewModel(repository)
    }

    @Test
    fun `초기 로드 시 첫 페이지의 상품이 로드된다`() {
        val products = viewModel.products.value
        assertAll(
            { assertEquals(5, products?.size) },
            { assertEquals(1, viewModel.page.value) },
            { assertEquals(true, viewModel.hasNext.value) },
            { assertEquals(false, viewModel.hasPrevious.value) },
            { assertEquals(false, viewModel.isSinglePage.value) },
        )
    }

    @Test
    fun `다음 페이지 로드 시 페이지 번호가 증가하고 상품이 로드된다`() {
        // when
        viewModel.loadNextProducts()

        // then
        val products = viewModel.products.value
        assertAll(
            { assertEquals(5, products?.size) },
            { assertEquals(2, viewModel.page.value) },
            { assertEquals(true, viewModel.hasNext.value) },
            { assertEquals(true, viewModel.hasPrevious.value) },
        )
    }

    @Test
    fun `마지막 페이지 로드 시 다음 페이지는 없다`() {
        // when
        viewModel.loadNextProducts()
        viewModel.loadNextProducts()

        // then
        assertAll(
            { assertEquals(2, viewModel.products.value?.size) },
            { assertEquals(3, viewModel.page.value) },
            { assertEquals(false, viewModel.hasNext.value) },
            { assertEquals(true, viewModel.hasPrevious.value) },
        )
    }

    @Test
    fun `이전 페이지 로드 시 페이지 번호가 감소하고 상품이 로드된다`() {
        // when
        viewModel.loadNextProducts()
        viewModel.loadPreviousProducts()

        // then
        val products = viewModel.products.value
        assertAll(
            { assertEquals(5, products?.size) },
            { assertEquals(1, viewModel.page.value) },
            { assertEquals(true, viewModel.hasNext.value) },
            { assertEquals(false, viewModel.hasPrevious.value) },
        )
    }

    @Test
    fun `상품 제거 시 repository에서 제거된다`() {
        // given
        val productToRemove = viewModel.products.value?.first()!!

        // when
        viewModel.onProductRemoveClick(productToRemove)

        // then
        assertEquals(false, repository.getAll().contains(productToRemove))
    }
}
