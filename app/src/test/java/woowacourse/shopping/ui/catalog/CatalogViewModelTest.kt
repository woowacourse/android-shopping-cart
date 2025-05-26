package woowacourse.shopping.ui.catalog

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.model.CatalogProducts
import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.usecase.DecreaseCartProductQuantityUseCase
import woowacourse.shopping.domain.usecase.GetCatalogProductUseCase
import woowacourse.shopping.domain.usecase.GetCatalogProductsByIdsUseCase
import woowacourse.shopping.domain.usecase.GetCatalogProductsUseCase
import woowacourse.shopping.domain.usecase.GetSearchHistoryUseCase
import woowacourse.shopping.domain.usecase.IncreaseCartProductQuantityUseCase
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCTS_1
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCTS_2
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_1
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_2
import woowacourse.shopping.model.DUMMY_HISTORY_PRODUCT_1
import woowacourse.shopping.model.DUMMY_PRODUCT_1
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue
import woowacourse.shopping.util.setUpTestLiveData

@ExtendWith(InstantTaskExecutorExtension::class)
class CatalogViewModelTest {
    private lateinit var getCatalogProductsUseCase: GetCatalogProductsUseCase
    private lateinit var getCatalogProductUseCase: GetCatalogProductUseCase
    private lateinit var getCatalogProductsByIdsUseCase: GetCatalogProductsByIdsUseCase
    private lateinit var getSearchHistoryUseCase: GetSearchHistoryUseCase
    private lateinit var increaseCartProductQuantityUseCase: IncreaseCartProductQuantityUseCase
    private lateinit var decreaseCartProductQuantityUseCase: DecreaseCartProductQuantityUseCase
    private lateinit var viewModel: CatalogViewModel

    @BeforeEach
    fun setup() {
        getCatalogProductsUseCase = mockk()
        getCatalogProductUseCase = mockk()
        getCatalogProductsByIdsUseCase = mockk()
        getSearchHistoryUseCase = mockk()
        increaseCartProductQuantityUseCase = mockk()
        decreaseCartProductQuantityUseCase = mockk()

        every {
            getCatalogProductsUseCase.invoke(any(), any(), any())
        } answers {
            thirdArg<(CatalogProducts) -> Unit>().invoke(DUMMY_CATALOG_PRODUCTS_1)
        }

        viewModel =
            CatalogViewModel(
                getCatalogProductsUseCase,
                getCatalogProductUseCase,
                getCatalogProductsByIdsUseCase,
                getSearchHistoryUseCase,
                increaseCartProductQuantityUseCase,
                decreaseCartProductQuantityUseCase,
            )
    }

    @Test
    fun `전체 상품 목록과 다른 상품 존재 여부를 불러온다`() {
        // given
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS_1, "_catalogProducts", viewModel)

        val newProduct = DUMMY_CATALOG_PRODUCT_2
        val newData = CatalogProducts(products = listOf(newProduct), hasMore = false)

        every {
            getCatalogProductsUseCase.invoke(any(), any(), any())
        } answers {
            thirdArg<(CatalogProducts) -> Unit>().invoke(newData)
        }

        // when
        viewModel.loadCartProducts()

        // then
        val result = viewModel.catalogProducts.getOrAwaitValue()
        assertThat(result.products).containsExactlyElementsIn(DUMMY_CATALOG_PRODUCTS_1.products + newProduct)
        assertThat(result.hasMore).isFalse()
    }

    @Test
    fun `최근에 탐색한 상품 목록을 불러온다`() {
        // given
        val expected = listOf(DUMMY_HISTORY_PRODUCT_1)

        every {
            getSearchHistoryUseCase.invoke(any())
        } answers {
            firstArg<(List<HistoryProduct>) -> Unit>().invoke(expected)
        }

        // when
        viewModel.loadHistoryProducts()

        // then
        val result = viewModel.historyProducts.getOrAwaitValue()
        assertThat(result).containsExactlyElementsIn(expected)
    }

    @Test
    fun `장바구니 상품 갯수를 증가시킨다`() {
        // given
        val productId = DUMMY_CATALOG_PRODUCT_1.product.id
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS_1, "_catalogProducts", viewModel)

        every {
            increaseCartProductQuantityUseCase.invoke(eq(productId), any(), any())
        } answers {
            thirdArg<(Int) -> Unit>().invoke(10)
        }

        // when
        viewModel.increaseCartProduct(productId)

        // then
        val updated = viewModel.catalogProducts.getOrAwaitValue()
        assertThat(updated.products.first { it.product.id == productId }.quantity).isEqualTo(10)
    }

    @Test
    fun `장바구니 상품 갯수를 감소시킨다`() {
        // given
        val productId = DUMMY_CATALOG_PRODUCT_1.product.id
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS_1, "_catalogProducts", viewModel)

        every {
            decreaseCartProductQuantityUseCase.invoke(eq(productId), any(), any())
        } answers {
            thirdArg<(Int) -> Unit>().invoke(1)
        }

        // when
        viewModel.decreaseCartProduct(productId)

        // then
        val updated = viewModel.catalogProducts.getOrAwaitValue()
        assertThat(updated.products.first { it.product.id == productId }.quantity).isEqualTo(1)
    }

    @Test
    fun `특정 상품의 정보를 불러와 상품 목록에 반영한다`() {
        // given
        val productId = DUMMY_CATALOG_PRODUCT_1.product.id
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS_1, "_catalogProducts", viewModel)

        val updatedProduct = DUMMY_CATALOG_PRODUCT_1.copy(quantity = 100)

        every {
            getCatalogProductUseCase.invoke(eq(productId), any())
        } answers {
            secondArg<(CatalogProduct?) -> Unit>().invoke(updatedProduct)
        }

        // when
        viewModel.loadCartProduct(productId)

        // then
        val result = viewModel.catalogProducts.getOrAwaitValue()
        assertThat(result.products.first { it.product.id == productId }.quantity).isEqualTo(100)
    }

    @Test
    fun `특정 상품들의 정보를 불러와 상품 목록에 반영한다`() {
        // given
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS_2, "_catalogProducts", viewModel)

        val updatedProducts = listOf(DUMMY_CATALOG_PRODUCT_1.copy(quantity = 6))

        every {
            getCatalogProductsByIdsUseCase.invoke(any(), any())
        } answers {
            secondArg<(List<CatalogProduct>) -> Unit>().invoke(updatedProducts)
        }

        // when
        viewModel.loadCartProducts(listOf(DUMMY_PRODUCT_1.id))

        // then
        val result = viewModel.catalogProducts.getOrAwaitValue()
        assertThat(result.products).containsExactlyElementsIn(updatedProducts)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
