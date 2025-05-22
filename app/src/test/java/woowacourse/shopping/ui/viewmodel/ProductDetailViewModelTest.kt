package woowacourse.shopping.ui.viewmodel

import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_1
import woowacourse.shopping.model.DUMMY_HISTORY_PRODUCT_1
import woowacourse.shopping.model.DUMMY_PRODUCT_1
import woowacourse.shopping.ui.productdetail.ProductDetailViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue
import woowacourse.shopping.util.setUpTestLiveData

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var historyRepository: HistoryRepository
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setup() {
        productRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        historyRepository = mockk(relaxed = true)

        viewModel = ProductDetailViewModel(productRepository, cartRepository, historyRepository)
    }

    @Test
    fun `상품 상세 정보를 불러온다`() {
        // given
        val expected = DUMMY_CATALOG_PRODUCT_1
        every {
            productRepository.fetchProduct(DUMMY_PRODUCT_1.id, any())
        } answers {
            secondArg<(CatalogProduct?) -> Unit>().invoke(expected)
        }

        // when
        viewModel.loadProductDetail(DUMMY_PRODUCT_1.id)

        // then
        val actual = viewModel.catalogProduct.getOrAwaitValue()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `가장 최근에 탐색한 상품을 불러온다`() {
        // given
        every {
            historyRepository.fetchRecentSearchHistory(any())
        } answers {
            firstArg<(woowacourse.shopping.domain.model.HistoryProduct?) -> Unit>().invoke(DUMMY_HISTORY_PRODUCT_1)
        }

        // when
        viewModel.loadLastHistoryProduct()

        // then
        val actual = viewModel.lastHistoryProduct.getOrAwaitValue()
        assertThat(actual).isEqualTo(DUMMY_HISTORY_PRODUCT_1)
    }

    @Test
    fun `최근 탐색한 상품 목록에 현재 상품을 추가한다`() {
        // given
        val productId = DUMMY_PRODUCT_1.id

        // when
        viewModel.addHistoryProduct(productId)

        // then
        verify { historyRepository.saveSearchHistory(productId) }
    }

    @Test
    fun `상품 수량을 증가시킨다`() {
        // given
        val initial = DUMMY_CATALOG_PRODUCT_1
        setUpTestLiveData(initial, "_catalogProduct", viewModel)

        // when
        viewModel.increaseCartProductQuantity()

        // then
        val updated = viewModel.catalogProduct.getOrAwaitValue()
        assertThat(updated.quantity).isEqualTo(6)
    }

    @Test
    fun `상품 수량을 감소시킨다`() {
        // given
        val initial = DUMMY_CATALOG_PRODUCT_1
        setUpTestLiveData(initial, "_catalogProduct", viewModel)

        // when
        viewModel.decreaseCartProductQuantity()

        // then
        val updated = viewModel.catalogProduct.getOrAwaitValue()
        assertThat(updated.quantity).isEqualTo(4)
    }

    @Test
    fun `장바구니에 변경된 상품 수량을 반영한다`() {
        // given
        val catalogProduct = DUMMY_CATALOG_PRODUCT_1
        setUpTestLiveData(catalogProduct, "_catalogProduct", viewModel)
        every {
            cartRepository.saveCartProduct(CartProduct(DUMMY_PRODUCT_1, 5))
        } just Runs

        // when
        viewModel.updateCartProduct()

        // then
        verify { cartRepository.saveCartProduct(CartProduct(DUMMY_PRODUCT_1, 5)) }
        assertThat(viewModel.onCartProductAddSuccess.getValue()).isTrue()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
