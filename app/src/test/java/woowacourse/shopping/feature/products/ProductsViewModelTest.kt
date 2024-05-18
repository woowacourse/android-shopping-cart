package woowacourse.shopping.feature.products

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.feature.products.viewmodel.ProductsViewModel
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel
    private val productRepository: ProductRepository = ProductDummyRepository
    private val pageSize = 20

    @BeforeEach
    fun setUp() {
        viewModel = ProductsViewModel(productRepository)
        productRepository.deleteAll()
    }

    @Test
    fun `한 페이지에는 20개의 상품이 있다`() {
        // given
        repeat(20) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage()

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(20)
        assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize))
    }

    @Test
    fun `상품이 40개인 경우 1페이지에서 20개의 상품을 불러온다`() {
        // given
        repeat(40) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage()

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(20)
        assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize))
    }

    @Test
    fun `상품이 5개인 경우 1페이지에서 5개의 상품을 불러온다`() {
        // given
        repeat(5) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage()

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize))
    }

    @Test
    fun `상품이 10개이고 1페이지인 경우 상품을 더 불러올 수 없다`() {
        // given
        repeat(10) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage()
        viewModel.changeSeeMoreVisibility(lastPosition())

        // then
        val actual = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `상품이 25개이고 1페이지인 경우 상품을 더 불러올 수 있다`() {
        // given
        repeat(25) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage()
        viewModel.changeSeeMoreVisibility(lastPosition())

        // then
        val actual = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    @Test
    fun `상품이 25개이고 2페이지로 이동하면 25개의 상품이 보인다`() {
        // given
        repeat(25) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage()
        viewModel.loadPage()

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(25)
    }

    @Test
    fun `상품이 25개이고 2페이지인 경우 상품을 더 불러올 수 없다`() {
        // given
        repeat(25) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage()
        viewModel.loadPage()
        viewModel.changeSeeMoreVisibility(lastPosition())

        // then
        val actual = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    private fun lastPosition(): Int = viewModel.products.getOrAwaitValue().size - 1
}
