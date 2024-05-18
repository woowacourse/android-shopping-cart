package woowacourse.shopping.feature.detail.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.feature.InstantTaskExecutorExtension
import woowacourse.shopping.feature.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title
import java.lang.IllegalArgumentException

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel
    private val productRepository: ProductRepository = ProductDummyRepository

    @BeforeEach
    fun setUp() {
        viewModel = ProductDetailViewModel(productRepository)
        productRepository.deleteAll()
    }

    @Test
    fun `상품 id에 맞는 상품을 불러온다`() {
        // given
        val id = productRepository.save(imageUrl, title, price)

        // when
        viewModel.loadProduct(id)

        // then
        val actual = viewModel.product.getOrAwaitValue()
        assertAll(
            Executable { assertThat(actual.imageUrl).isEqualTo(imageUrl) },
            Executable { assertThat(actual.title).isEqualTo(title) },
            Executable { assertThat(actual.price).isEqualTo(price) },
        )
    }

    @Test
    fun `상품 id에 해당하는 상품이 없는 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            viewModel.loadProduct(-1L)
        }
    }
}
