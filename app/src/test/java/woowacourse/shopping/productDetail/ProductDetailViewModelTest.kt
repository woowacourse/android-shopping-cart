package woowacourse.shopping.productDetail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.domain.repository.DefaultProductIdsCountRepository
import woowacourse.shopping.repository.FakeProductIdsCountDataSource
import woowacourse.shopping.repository.FakeShoppingProductsRepository
import woowacourse.shopping.domain.repository.ProductIdsCountRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import woowacourse.shopping.ui.productDetail.ProductDetailViewModel

class ProductDetailViewModelTest {
    private lateinit var shoppingProductsRepository: ShoppingProductsRepository
    private lateinit var productIdsCountRepository: ProductIdsCountRepository
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        shoppingProductsRepository = FakeShoppingProductsRepository(productsTestFixture(40))
        productIdsCountRepository =
            DefaultProductIdsCountRepository(
                FakeProductIdsCountDataSource(
                    productsIdCountDataTestFixture(5, 1).toMutableList(),
                ),
            )
    }

    @Test
    fun `현재 상품을 표시한다`() {
        // given
        viewModel = ProductDetailViewModel(3, shoppingProductsRepository, productIdsCountRepository)

        // then
        assertThat(viewModel.currentProduct).isEqualTo(productTestFixture(3))
    }

    // TODO: 여기 조금 개선 필요
    @Test
    fun `현재 상품이 장바구니에 이미 있다면, 다시 추가하지 않는다`() {
        // given
        viewModel = ProductDetailViewModel(3, shoppingProductsRepository, productIdsCountRepository)

        // when
        viewModel.addProductToCart()

        // then
        assertThat(productIdsCountRepository.findByProductId(3)).isEqualTo(ProductIdsCount(3, 1))
    }

    @Test
    fun `현재 상품이 장바구니에 없다면 추가한다`() {
        // given
        viewModel = ProductDetailViewModel(3, shoppingProductsRepository, productIdsCountRepository)

        // when
        viewModel.addProductToCart()

        // then
        assertThat(productIdsCountRepository.findByProductId(3)).isEqualTo(ProductIdsCount(3, 1))
    }
}
