package woowacourse.shopping.viewmodel

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.PRODUCT_1
import woowacourse.shopping.SIX_QUANTITY_PRODUCTS
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.view.cart.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private lateinit var repository: CartRepository

    @BeforeEach
    fun setUp() {
        repository = CartRepositoryImpl

        SIX_QUANTITY_PRODUCTS.forEach {
            repository.add(it)
        }

        viewModel = CartViewModel(repository)
    }

    @Test
    fun `한개의 상품을 제거하면 1페이지만 남는다`() {
        // given

        // when
        viewModel.removeToCart(PRODUCT_1)

        // then
        viewModel.products.value shouldNotBe null
        viewModel.products.value?.contains(PRODUCT_1) shouldNotBe true
        viewModel.isOnlyOnePage.value shouldBe true
    }

    @Test
    fun `다음페이지로 이동할 때 그 페이지가 마지막 페이지이며 1개의 로드될 상품이 저장된다`() {
        // given

        // when
        viewModel.loadNextPage()

        // then
        viewModel.currentPageNumber.value shouldBe 2
        viewModel.loadedProducts.value shouldNotBe null
        viewModel.loadedProducts.value?.size shouldBe 1
        viewModel.isLastPage.value shouldBe true
    }

    @Test
    fun `이전 페이지로 이동할 때 그 페이지가 첫 페이지이며 5개의 로드될 상품이 저장된다`() {
        // given
        viewModel.loadNextPage()

        // when
        viewModel.loadPreviousPage()

        // then
        viewModel.currentPageNumber.value shouldBe 1
        viewModel.loadedProducts.value shouldNotBe null
        viewModel.loadedProducts.value?.size shouldBe 5
        viewModel.isFirstPage.value shouldBe true
    }
}
