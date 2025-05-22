package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.MutableLiveData
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
import woowacourse.shopping.model.DUMMY_HISTORY_PRODUCT_1
import woowacourse.shopping.model.DUMMY_PRODUCT_1
import woowacourse.shopping.ui.productdetail.ProductDetailViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

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
    fun `loadProductDetail은 상품 정보를 불러와 catalogProduct에 저장한다`() {
        // given
        val expected = CatalogProduct(DUMMY_PRODUCT_1, 5)
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
    fun `loadLastHistoryProduct는 최근 검색어를 lastHistoryProduct에 저장한다`() {
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
    fun `addHistoryProduct는 저장 요청만 보낸다`() {
        // given
        val productId = DUMMY_PRODUCT_1.id

        // when
        viewModel.addHistoryProduct(productId)

        // then
        verify { historyRepository.saveSearchHistory(productId) }
    }

    @Test
    fun `increaseCartProductQuantity는 수량을 1 증가시킨다`() {
        // given
        val initial = CatalogProduct(DUMMY_PRODUCT_1, 5)
        setUpCatalogProduct(initial)

        // when
        viewModel.increaseCartProductQuantity()

        // then
        val updated = viewModel.catalogProduct.getOrAwaitValue()
        assertThat(updated.quantity).isEqualTo(6)
    }

    @Test
    fun `decreaseCartProductQuantity는 수량을 1 감소시킨다`() {
        // given
        val initial = CatalogProduct(DUMMY_PRODUCT_1, 5)
        setUpCatalogProduct(initial)

        // when
        viewModel.decreaseCartProductQuantity()

        // then
        val updated = viewModel.catalogProduct.getOrAwaitValue()
        assertThat(updated.quantity).isEqualTo(4)
    }

    @Test
    fun `updateCartProduct는 CartRepository에 저장하고 성공 이벤트를 발생시킨다`() {
        // given
        val catalogProduct = CatalogProduct(DUMMY_PRODUCT_1, 5)
        setUpCatalogProduct(catalogProduct)
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

    @Suppress("UNCHECKED_CAST")
    private fun setUpCatalogProduct(product: CatalogProduct) {
        val field = ProductDetailViewModel::class.java.getDeclaredField("_catalogProduct")
        field.isAccessible = true
        val liveData = field.get(viewModel) as MutableLiveData<CatalogProduct>
        liveData.postValue(product)
    }
}
