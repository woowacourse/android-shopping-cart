package woowacourse.shopping.ui.viewmodel

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.productdetail.ProductDetailViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductEntityDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var fakeCartRepository: FakeCartRepository
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var fakeLastProductRepository: FakeLastProductRepository

    @BeforeEach
    fun setup() {
        fakeCartRepository = FakeCartRepository()
        fakeProductRepository = FakeProductRepository()
        fakeLastProductRepository = FakeLastProductRepository()
        viewModel = ProductDetailViewModel(fakeProductRepository, fakeCartRepository, fakeLastProductRepository)
    }

    @Test
    fun `product 값을 ID에 해당하는 상품으로 업데이트한다`() {
        // given
        val targetProductId = 1
        val expectedProduct = dummyProducts.find { it.id == targetProductId }

        // when
        viewModel.updateProductDetail(targetProductId)

        // then
        val actual = viewModel.product.getOrAwaitValue()
        Assertions.assertEquals(expectedProduct, actual)
    }

    @Test
    fun `현재 선택된 상품을 장바구니에 추가한다`() {
        // given
        val productId = 2
        val expectedProduct = dummyProducts.find { it.id == productId }!!
        viewModel.updateProductDetail(productId)

        // when
        viewModel.addCartProduct()

        // then
        val updatedCart = fakeCartRepository.storage
        Assertions.assertTrue(updatedCart.any { it.name == expectedProduct.name })
    }
}