package woowacourse.shopping.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.data.repository.FakeCartProductRepositoryImpl
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private lateinit var fakeCartRepository: FakeCartProductRepositoryImpl

    private val dummyProducts =
        (1..12).map {
            ProductUiModel(
                id = it,
                name = "상품 $it",
                imageUrl = "https://example.com/$it.jpg",
                price = 1000 * it,
                quantity = it,
            )
        }

    @BeforeEach
    fun setUp() {
        fakeCartRepository =
            FakeCartProductRepositoryImpl().apply {
                dummyProducts.forEach { insertCartProduct(it.toEntity()) }
            }
        viewModel = CartViewModel(fakeCartRepository)
    }

    @Test
    fun `초기 로딩 시 첫 페이지의 상품 5개만 로딩된다`() {
        val loaded = viewModel.cartProducts.getOrAwaitValue()
        Assertions.assertNotNull(loaded)
        assertThat(loaded.size).isEqualTo(5)
        assertThat(loaded.first().name).isEqualTo("상품 1")
    }

    @Test
    fun `다음 페이지 버튼 클릭 시 다음 상품 페이지 로딩`() {
        viewModel.cartProducts.getOrAwaitValue()
        viewModel.onPaginationButtonClick(2) // NEXT_BUTTON = 2
        val loaded = viewModel.cartProducts.getOrAwaitValue()
        Assertions.assertNotNull(loaded)
        assertThat(loaded.size).isEqualTo(5)
        assertThat(loaded.first().name).isEqualTo("상품 6")
    }

    @Test
    fun `이전 페이지 버튼 클릭 시 이전 상품 페이지 로딩`() {
        viewModel.cartProducts.getOrAwaitValue()
        viewModel.onPaginationButtonClick(2) // NEXT_BUTTON
        viewModel.cartProducts.getOrAwaitValue()
        viewModel.onPaginationButtonClick(1) // PREV_BUTTON
        val loaded = viewModel.cartProducts.getOrAwaitValue()
        assertThat(loaded.first().name).isEqualTo("상품 1")
    }

    @Test
    fun `상품 수량 증가 시 updatedItem에 반영된다`() {
        val product = dummyProducts[0]
        viewModel.updateQuantity(1, product) // INCREASE_BUTTON
        val updated = viewModel.updatedItem.getOrAwaitValue()
        Assertions.assertNotNull(updated)
        assertThat(product.id).isEqualTo(updated.id)
        assertThat(product.quantity + 1).isEqualTo(updated.quantity)
    }

    @Test
    fun `상품 수량 감소 시 updatedItem에 반영된다`() {
        val product = dummyProducts[1]
        viewModel.updateQuantity(0, product) // DECREASE_BUTTON
        val updated = viewModel.updatedItem.getOrAwaitValue()
        Assertions.assertNotNull(updated)
        assertThat(product.id).isEqualTo(updated?.id)
        assertThat(product.quantity - 1).isEqualTo(updated.quantity)
    }

    @Test
    fun `상품 삭제 시 해당 상품이 목록에서 제거된다`() {
        val toDelete = viewModel.cartProducts.getOrAwaitValue().first()
        viewModel.deleteCartProduct(CartItem.ProductItem(toDelete))
        val updated = viewModel.cartProducts.getOrAwaitValue()
        assertThat(updated.any { it.id == toDelete.id }).isEqualTo(false)
    }

    @Test
    fun `페이지 끝에서 상품 삭제 시 페이지가 감소된다`() {
        // 페이지 끝으로 이동
        viewModel.onPaginationButtonClick(2)
        viewModel.onPaginationButtonClick(2)
        val lastPage = viewModel.page.getOrAwaitValue()
        val toDelete = viewModel.cartProducts.getOrAwaitValue().first()

        viewModel.deleteCartProduct(CartItem.ProductItem(toDelete))
        val newPage = viewModel.page.getOrAwaitValue()
        assertThat(newPage <= lastPage).isEqualTo(true)
    }

    @Test
    fun `초기 상태에서 이전 버튼은 비활성화, 다음 버튼은 활성화`() {
        assertThat(viewModel.isPrevButtonEnabled.getOrAwaitValue()).isEqualTo(false)
        assertThat(viewModel.isNextButtonEnabled.getOrAwaitValue()).isEqualTo(true)
    }
}
