package woowacourse.shopping.product.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixture.FakeCartItemRepository
import woowacourse.shopping.fixture.FakeViewedItemRepository
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.util.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel

    @Test
    fun `최근 본 상품이 존재하면 loadLastViewedItem 호출 시 lastViewed가 설정된다`() {
        viewModel =
            DetailViewModel(
                FakeCartItemRepository(0),
                FakeViewedItemRepository(1),
            )

        viewModel.loadLastViewedItem(currentProductId = 100)

        val lastViewed = viewModel.lastViewed.value
        assertThat(lastViewed?.name).isEqualTo("1 아이스 카페 아메리카노")
    }

    @Test
    fun `최근 본 상품이 현재 상품과 같으면 lastViewed는 null이 된다`() {
        viewModel =
            DetailViewModel(
                FakeCartItemRepository(0),
                FakeViewedItemRepository(1),
            )

        viewModel.loadLastViewedItem(currentProductId = 1L)

        val lastViewed = viewModel.lastViewed.value
        assertThat(lastViewed).isEqualTo(null)
    }

    @Test
    fun `상품을 장바구니에 담을 수 있다`() {
        val cartRepository = FakeCartItemRepository(0)

        viewModel =
            DetailViewModel(
                cartRepository,
                FakeViewedItemRepository(1),
            )

        val cartItem =
            ProductUiModel(
                id = 10L,
                price = 10000,
                quantity = 1,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                name = "베르가못 아이스 커피",
            )

        viewModel.setProduct(cartItem)
        viewModel.addToCart()

        val cartItems = mutableListOf<ProductUiModel>()
        cartRepository.getAllCartItem { cartItems.addAll(it) }

        assertThat(cartItems).hasSize(1)
        assertThat(cartItems[0].quantity).isEqualTo(1)
        assertThat(cartItems[0].name).isEqualTo("베르가못 아이스 커피")
    }

    @Test
    fun `상품을 원하는 갯수만큼 장바구니에 담을 수 있다`() {
        val cartRepository = FakeCartItemRepository(0)

        viewModel =
            DetailViewModel(
                cartRepository,
                FakeViewedItemRepository(1),
            )

        val cartItem =
            ProductUiModel(
                id = 10L,
                price = 10000,
                quantity = 1,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                name = "베르가못 아이스 커피",
            )

        viewModel.setProduct(cartItem)
        viewModel.increaseQuantity()
        viewModel.increaseQuantity()
        viewModel.addToCart()

        val cartItems = mutableListOf<ProductUiModel>()
        cartRepository.getAllCartItem { cartItems.addAll(it) }

        assertThat(cartItems).hasSize(1)
        assertThat(cartItems[0].quantity).isEqualTo(3)
        assertThat(cartItems[0].name).isEqualTo("베르가못 아이스 커피")
    }
}
