package woowacourse.shopping.feature.main.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.feature.InstantTaskExecutorExtension
import woowacourse.shopping.feature.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private val productRepository: ProductRepository = ProductDummyRepository
    private val pageSize: Int = 20

    @BeforeEach
    fun setUp() {
        viewModel = MainViewModel(productRepository)
        productRepository.deleteAll()
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
        assertAll(
            { assertThat(actual).hasSize(pageSize) },
            { assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize)) },
        )
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
        assertAll(
            { assertThat(actual).hasSize(pageSize) },
            { assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize)) },
        )
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
        assertAll(
            { assertThat(actual).hasSize(5) },
            { assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize)) },
        )
    }
}
