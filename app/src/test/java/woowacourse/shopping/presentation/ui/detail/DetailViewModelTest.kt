package woowacourse.shopping.presentation.ui.detail

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.ui.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val cartRepository = mockk<CartRepository>()
    private val shoppingRepository = mockk<ShoppingItemsRepository>()

    private val product =
        Product.of(
            name = "[든든] 동원 스위트콘1",
            price = 99800L,
            imageUrl = "https://url.kr/fr947z",
        )

    @BeforeEach
    fun setUp() {
        every { shoppingRepository.findProductItem(any()) } returns product
        viewModel = DetailViewModel(cartRepository, shoppingRepository, 0L)
    }

    @Test
    fun `선택된 상품의 상세 정보를 가져온다`() {
        val actual = viewModel.product.getOrAwaitValue()

        assertThat(actual.name).isEqualTo("[든든] 동원 스위트콘1")
        assertThat(actual.price).isEqualTo(99800L)
        assertThat(actual.imageUrl).isEqualTo("https://url.kr/fr947z")
    }
}
