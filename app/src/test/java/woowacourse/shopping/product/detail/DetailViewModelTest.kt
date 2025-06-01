package woowacourse.shopping.product.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.repository.FakeCartProductRepositoryImpl
import woowacourse.shopping.data.repository.FakeCatalogProductRepositoryImpl
import woowacourse.shopping.data.repository.FakeRecentlyViewedProductRepositoryImpl
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.util.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel

    private val dummyProduct =
        ProductUiModel(
            id = 1,
            name = "아이스 카페 아메리카노",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
            price = 10000,
        )

    private val fakeCartRepository = FakeCartProductRepositoryImpl()
    private val fakeRecentlyViewedRepository =
        FakeRecentlyViewedProductRepositoryImpl(FakeCatalogProductRepositoryImpl(size = 25))

    private fun createViewModel() {
        viewModel =
            DetailViewModel(
                productData = dummyProduct,
                cartProductRepository = fakeCartRepository,
                recentlyViewedProductRepository = fakeRecentlyViewedRepository,
            )
    }

    @Test
    fun `초기 상태는 수량과 가격이 0이다`() {
        createViewModel()
        assert(viewModel.quantity.value == 0)
        assert(viewModel.price.value == 0)
        assert(viewModel.product.value == dummyProduct)
    }

    @Test
    fun `수량을 증가시키면 수량이 1 증가하고, 가격도 갱신된다`() {
        createViewModel()
        viewModel.increaseQuantity()
        assert(viewModel.quantity.value == 1)
        assert(viewModel.price.value == 10000)
    }

    @Test
    fun `수량을 감소시키면 수량이 1 감소하고, 가격도 갱신된다`() {
        createViewModel()
        viewModel.increaseQuantity() // 1
        viewModel.increaseQuantity() // 2
        viewModel.decreaseQuantity() // 1
        assert(viewModel.quantity.value == 1)
        assert(viewModel.price.value == 10000)
    }

    @Test
    fun `setQuantity는 productData의 수량을 설정한다`() {
        val productWithQuantity = dummyProduct.copy(quantity = 3)
        viewModel =
            DetailViewModel(
                productData = productWithQuantity,
                cartProductRepository = fakeCartRepository,
                recentlyViewedProductRepository = fakeRecentlyViewedRepository,
            )

        viewModel.setQuantity()
        assert(viewModel.quantity.value == 3)
    }

    @Test
    fun `장바구니에 추가 시 수량이 포함된 상품이 저장된다`() {
        createViewModel()
        viewModel.increaseQuantity() // 수량 1
        viewModel.addToCart()

        val added = fakeCartRepository.cartProducts.firstOrNull()
        requireNotNull(added)
        assert(added.uid == dummyProduct.id)
        assert(added.quantity == 1)
    }
}
