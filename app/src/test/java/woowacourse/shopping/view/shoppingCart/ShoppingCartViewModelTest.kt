package woowacourse.shopping.view.shoppingCart

import org.junit.jupiter.api.BeforeEach
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository

class ShoppingCartViewModelTest {
    private lateinit var repository: ShoppingCartRepository
    private lateinit var viewModel: ShoppingCartViewModel

    @BeforeEach
    fun setUp() {
        repository = FakeShoppingCartRepository()
        viewModel = ShoppingCartViewModel(repository)
    }
}
