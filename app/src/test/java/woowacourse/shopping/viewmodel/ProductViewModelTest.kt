package woowacourse.shopping.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title
import java.lang.IllegalArgumentException

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductViewModelTest {
    private lateinit var viewModel: ProductViewModel
    private lateinit var productRepository: ProductRepository
    private val pageSize = 20

    @BeforeEach
    fun setUp() {
        viewModel = ProductViewModel(ProductDummyRepository)
        productRepository = ProductDummyRepository
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
        assertThat(actual.imageUrl).isEqualTo(imageUrl)
        assertThat(actual.title).isEqualTo(title)
        assertThat(actual.price).isEqualTo(price)
    }

    @Test
    fun `상품 id에 해당하는 상품이 없는 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            viewModel.loadProduct(-1L)
        }
    }

    @Test
    fun `한 페이지에는 20개의 상품이 있다`() {
        // given
        repeat(pageSize) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage(0, pageSize)

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(pageSize)
        assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize))
    }

    @Test
    fun `상품이 40개인 경우 20개의 상품을 불러온다`() {
        // given
        repeat(40) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage(0, pageSize)

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(pageSize)
        assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize))
    }

    @Test
    fun `상품이 5개인 경우 5개의 상품을 불러온다`() {
        // given
        repeat(5) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage(0, pageSize)

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize))
    }
}
