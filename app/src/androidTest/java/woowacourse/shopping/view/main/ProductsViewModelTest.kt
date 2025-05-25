package woowacourse.shopping.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.fixture.FakeProductsRepository
import woowacourse.shopping.fixture.FakeRecentProductRepository
import woowacourse.shopping.fixture.FakeShoppingCartRepository
import woowacourse.shopping.fixture.TestProducts
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.mapper.toProductUiModel
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.view.uimodel.QuantityInfo

@Suppress("FunctionName")
class ProductsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val productsRepository = FakeProductsRepository()
    val shoppingCartRepository = FakeShoppingCartRepository()
    val recentProductRepository = FakeRecentProductRepository()
    val viewModel =
        ProductsViewModel(
            productsRepository,
            shoppingCartRepository,
            recentProductRepository,
        )

    @Test
    fun 한_페이지에_상품이_20개씩_로드된다() {
        val page =
            Page(
                TestProducts.productUiModels.subList(0, 20).map { it.toProductUiModel() },
                TestProducts.productUiModels.size,
                0,
                20,
            )
        viewModel.requestProductsPage(0)
        assertThat(viewModel.productsLiveData.getOrAwaitValue().page).isEqualTo(
            page,
        )
    }

    @Test
    fun 상품의_총_개수를_반환한다() {
        assertThat(viewModel.totalSize.getOrAwaitValue()).isEqualTo(TestProducts.productUiModels.size)
    }

    @Test
    fun 현재_상품들의_수량을_장바구니에_저장할_수_있다() {
        val quantityInfo =
            QuantityInfo(
                TestProducts.productUiModels.subList(0, 1)
                    .map { it.toProductUiModel() }
                    .associateWith { MutableLiveData(2) },
            )
        viewModel.saveCurrentShoppingCart(quantityInfo)
        Thread.sleep(500)

        assertThat(
            shoppingCartRepository.findAll(),
        ).contains(
            ShoppingCartItem(
                id = 1,
                product = TestProducts.productUiModels[0],
                quantity = 2,
            ),
        )
    }

    @Test
    fun 최신_장바구니의_상황을_fetch_할_수있다() {
        // given
        shoppingCartRepository.update(
            ShoppingCartItem(
                id = 1,
                product = TestProducts.productUiModels[0],
                quantity = 2,
            ),
        )
        viewModel.updateShoppingCart(0)
        Thread.sleep(500)
        assertThat(viewModel.totalShoppingCartSize.getOrAwaitValue()).isEqualTo(
            shoppingCartRepository.totalQuantity(),
        )
    }

    @Test
    fun 최근_본_상품을_가져올_수_있다() {
        viewModel.requestRecentProducts()
        assertThat(viewModel.recentProductsLiveData.getOrAwaitValue())
            .isEqualTo(
                recentProductRepository.findAll().map {
                    ProductUiModel(
                        id = it.product.id,
                        name = it.product.name,
                        price = it.product.price,
                        imageUrl = it.product.imageUrl,
                    )
                },
            )
    }

    @Test
    fun 상품_수량을_증가시킬_수_있다() {
        // given
        viewModel.requestProductsPage(0)
        Thread.sleep(500)
        val product = TestProducts.productUiModels[0].toProductUiModel()

        // when
        viewModel.increaseCount(product)

        // then
        assertThat(viewModel.quantityInfo[product].value).isEqualTo(2)
    }

    @Test
    fun 상품_수량을_감소시킬_수_있다() {
        // given
        viewModel.requestProductsPage(0)
        Thread.sleep(500)
        val product = TestProducts.productUiModels[0].toProductUiModel()

        // when
        viewModel.decreaseCount(product)

        // then
        assertThat(viewModel.quantityInfo[product].value).isEqualTo(0)
    }
}
