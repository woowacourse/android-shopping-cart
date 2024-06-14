package woowacourse.shopping.presentation.ui.detail

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.ui.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private lateinit var testCartRepository: CartRepository
    private val shoppingRepository = mockk<ShoppingItemsRepository>()
    private var testRecentProductRepository: RecentProductRepository = mockk()

    private val product =
        Product.of(
            name = "[든든] 동원 스위트콘1",
            price = 99800L,
            imageUrl = "https://url.kr/fr947z",
        )

    @BeforeEach
    fun setUp() {
        testCartRepository = FakeCartRepositoryImpl()
        every { shoppingRepository.findProductItem(any()) } returns product
        every { testRecentProductRepository.loadSecondLatest() } returns null

        viewModel = DetailViewModel(testCartRepository, shoppingRepository, testRecentProductRepository, 0L)
    }

    @Test
    fun `선택된 상품의 상세 정보를 가져온다`() {
        val actual = viewModel.shoppingProduct.getOrAwaitValue()

        assertThat(actual.product.name).isEqualTo("[든든] 동원 스위트콘1")
        assertThat(actual.product.price).isEqualTo(99800L)
        assertThat(actual.product.imageUrl).isEqualTo("https://url.kr/fr947z")
    }

    @Test
    fun `아무것도 담지 않은 장바구니의 크기는 0 이다`() {
        val actual = testCartRepository.size()

        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `상품을 장바구니에 담으면 장바구니의 사이즈가 증가한다`() {
        viewModel.createShoppingCartItem()
        viewModel.shoppingProduct.getOrAwaitValue()
        val actual = testCartRepository.size()

        assertThat(actual).isEqualTo(1)
    }
}
