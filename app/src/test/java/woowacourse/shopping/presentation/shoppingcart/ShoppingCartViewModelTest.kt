package woowacourse.shopping.presentation.shoppingcart

import io.kotest.matchers.collections.shouldNotContain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.ShoppingDataBase
import woowacourse.shopping.fixture.ICE_CREAM
import woowacourse.shopping.fixture.SUNDAE
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private val goods =
        listOf(
            SUNDAE,
            ICE_CREAM,
        )

    @BeforeEach
    fun setUp() {
        goods.forEach { ShoppingDataBase.addItem(it) }
        shoppingCartViewModel = ShoppingCartViewModel()
    }

    @Test
    fun `장바구니의 물품을 삭제한다`() {
        // when
        shoppingCartViewModel.deleteGoods(SUNDAE)

        // then
        shoppingCartViewModel.goods.getOrAwaitValue().shouldNotContain(SUNDAE)
    }
}
