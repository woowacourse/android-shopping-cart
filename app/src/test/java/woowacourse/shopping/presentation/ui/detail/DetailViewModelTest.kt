package woowacourse.shopping.presentation.ui.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.DummyShoppingItems
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.ui.cart.FakeCartRepositoryImpl
import woowacourse.shopping.presentation.ui.getOrAwaitValue
import woowacourse.shopping.presentation.ui.shopping.FakeShoppingRepositoryImpl

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var testCartRepository: CartRepository
    private lateinit var testShoppingRepository: ShoppingItemsRepository
    private val dummyProductId = 0L
    private val dummyProduct0 = DummyShoppingItems.items[0]

    @BeforeEach
    fun setUp() {
        testCartRepository = FakeCartRepositoryImpl()
        testShoppingRepository = FakeShoppingRepositoryImpl()
        detailViewModel = DetailViewModel(testCartRepository, testShoppingRepository, dummyProductId)
    }

    @Test
    fun `선택된 상품의 상세 정보를 가져온다`() {
        // when
        val actual = detailViewModel.product.getOrAwaitValue()

        // then
        assertThat(actual.name).isEqualTo(dummyProduct0.name)
        assertThat(actual.price).isEqualTo(dummyProduct0.price)
        assertThat(actual.imageUrl).isEqualTo(dummyProduct0.imageUrl)
    }

    @Test
    fun `장바구니에 상품을 추가한다`() {
        // when
        detailViewModel.product.getOrAwaitValue()
        detailViewModel.createShoppingCartItem()

        // then
        val shoppingCartItemsSize = testCartRepository.findAll().items.size
        val shoppingCartItem = testCartRepository.findOrNullWithProductId(0)

        assertThat(shoppingCartItemsSize).isEqualTo(1)
        assertThat(shoppingCartItem?.productName).isEqualTo(dummyProduct0.name)
        assertThat(shoppingCartItem?.price).isEqualTo(dummyProduct0.price)
        assertThat(shoppingCartItem?.imgUrl).isEqualTo(dummyProduct0.imageUrl)
        assertThat(shoppingCartItem?.quantity).isEqualTo(1)
    }
}
