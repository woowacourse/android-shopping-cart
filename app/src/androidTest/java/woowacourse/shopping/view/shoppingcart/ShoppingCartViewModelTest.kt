@file:Suppress("ktlint")

package woowacourse.shopping.view.shoppingcart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import woowacourse.shopping.domain.Product
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.fixture.FakeShoppingCartRepository
import woowacourse.shopping.fixture.TestShoppingCart
import kotlin.math.min

class ShoppingCartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun 한_페이지에_장바구니_상품이_5개씩_로드된다() {
        val viewModel = ShoppingCartViewModel(
            FakeShoppingCartRepository(
                TestShoppingCart.products.toMutableList()
            )
        )
        val page =
            Page(
                TestShoppingCart.products.subList(0,5),
                TestShoppingCart.products.size,
                0,
                5,
            )
        viewModel.requestProductsPage(0)
        assertThat(viewModel.productsLiveData.getOrAwaitValue()).isEqualTo(page)
    }

    @Test
    fun 장바구니에서_상품을_삭제할_수_있다() {
        val viewModel = ShoppingCartViewModel(
            FakeShoppingCartRepository(
                TestShoppingCart.products.toMutableList()
            )
        )
        val product =
            Product(
                "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
                42000,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3c68d05b-d392-4a38-8637-a25068220fa4.jpg",
            )
        viewModel.removeProduct(product, 0)
        assertThat(viewModel.productsLiveData.getOrAwaitValue().items).contains(product)
    }

    @Test
    fun 장바구니에서_상품을_삭제하면_해당_상품이_있었던_페이지가_로드된다() {
        val viewModel = ShoppingCartViewModel(
            FakeShoppingCartRepository(
                TestShoppingCart.products.toMutableList()
            )
        )
        val product =
            Product(
                "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
                39700,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg",
            )
        viewModel.removeProduct(product, 4)
        val page =
            Page(
                TestShoppingCart.products.subList(20,21),
                TestShoppingCart.products.size,
                4,
                5,
            )
        assertThat(viewModel.productsLiveData.getOrAwaitValue()).isEqualTo(page)
    }

}
