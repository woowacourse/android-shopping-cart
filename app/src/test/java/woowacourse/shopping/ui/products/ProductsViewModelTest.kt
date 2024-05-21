package woowacourse.shopping.ui.products

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.product.FakeProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.model.Product
import woowacourse.shopping.price
import woowacourse.shopping.title

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel
    private lateinit var productRepository: ProductRepository

    @Test
    fun `한 페이지에는 20개의 상품이 있다`() {
        val products = products(20)
        productRepository = FakeProductRepository(products)
        viewModel = ProductsViewModel(productRepository)

        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(20)
        assertThat(actual).isEqualTo(products.take(20))
    }

    @Test
    fun `상품이 40개인 경우 1페이지에서 20개의 상품을 불러온다`() {
        val products = products(40)
        productRepository = FakeProductRepository(products)
        viewModel = ProductsViewModel(productRepository)

        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(20)
        assertThat(actual).isEqualTo(products.take(20))
    }

    @Test
    fun `상품이 5개인 경우 1페이지에서 5개의 상품을 불러온다`() {
        val products = products(5)
        productRepository = FakeProductRepository(products)
        viewModel = ProductsViewModel(productRepository)

        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(products.take(5))
    }

    @Test
    fun `상품이 10개이고 1페이지의 10번째 상품에 위치해 있는 경우 상품을 더 불러올 수 없다`() {
        // given
        val products = products(10)
        productRepository = FakeProductRepository(products)
        viewModel = ProductsViewModel(productRepository)

        // when
        viewModel.changeSeeMoreVisibility(9)

        // then
        val actual = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `상품이 25개이고 1페이지의 20번째 상품에 위치해 있는 경우 상품을 더 불러올 수 있다`() {
        // given
        val products = products(25)
        productRepository = FakeProductRepository(products)
        viewModel = ProductsViewModel(productRepository)

        // when
        viewModel.changeSeeMoreVisibility(19)

        // then
        val actual = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    @Test
    fun `상품이 25개이고 2페이지로 이동하면 25개의 상품이 보인다`() {
        // given
        val products = products(25)
        productRepository = FakeProductRepository(products)
        viewModel = ProductsViewModel(productRepository)

        // when
        viewModel.loadPage()

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertThat(actual).hasSize(25)
        assertThat(actual).isEqualTo(products)
    }

    @Test
    fun `상품이 25개이고 2페이지의 25번째 상품에 위치해 있는 경우 상품을 더 불러올 수 없다`() {
        // given
        val products = products(25)
        productRepository = FakeProductRepository(products)
        viewModel = ProductsViewModel(productRepository)

        // when
        viewModel.loadPage()
        viewModel.changeSeeMoreVisibility(24)

        // then
        val actual = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    private fun products(size: Int): List<Product> {
        return List(size) { Product(it.toLong(), imageUrl, title, price) }
    }
}
