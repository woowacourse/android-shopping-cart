package woowacourse.shopping.ui.productdetail

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.usecase.AddSearchHistoryUseCase
import woowacourse.shopping.domain.usecase.GetProductDetailUseCase
import woowacourse.shopping.domain.usecase.GetRecentSearchHistoryUseCase
import woowacourse.shopping.domain.usecase.UpdateCartProductUseCase
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_1
import woowacourse.shopping.model.DUMMY_HISTORY_PRODUCT_1
import woowacourse.shopping.model.DUMMY_PRODUCT_1
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue
import woowacourse.shopping.util.setUpTestLiveData

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var getProductDetailUseCase: GetProductDetailUseCase
    private lateinit var getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase
    private lateinit var addSearchHistoryUseCase: AddSearchHistoryUseCase
    private lateinit var updateCartProductUseCase: UpdateCartProductUseCase
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setup() {
        getProductDetailUseCase = mockk()
        getRecentSearchHistoryUseCase = mockk()
        addSearchHistoryUseCase = mockk(relaxed = true)
        updateCartProductUseCase = mockk(relaxed = true)

        viewModel =
            ProductDetailViewModel(
                getProductDetailUseCase,
                getRecentSearchHistoryUseCase,
                addSearchHistoryUseCase,
                updateCartProductUseCase,
            )
    }

    @Test
    fun `상품 상세 정보를 불러온다`() {
        // given
        val expected = DUMMY_CATALOG_PRODUCT_1

        every {
            getProductDetailUseCase.invoke(DUMMY_PRODUCT_1.id, any())
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
            getRecentSearchHistoryUseCase.invoke(any())
        } answers {
            firstArg<(HistoryProduct?) -> Unit>().invoke(DUMMY_HISTORY_PRODUCT_1)
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
        verify { addSearchHistoryUseCase.invoke(productId) }
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

        // when
        viewModel.updateCartProduct()

        // then
        verify { updateCartProductUseCase.invoke(CartProduct(DUMMY_PRODUCT_1, 5)) }
        assertThat(viewModel.onCartProductAddSuccess.getValue()).isTrue()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
