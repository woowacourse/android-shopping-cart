package woowacourse.shopping.ui.viewmodel

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
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCTS
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_1
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_2
import woowacourse.shopping.model.DUMMY_HISTORY_PRODUCT_1
import woowacourse.shopping.model.DUMMY_PRODUCT_1
import woowacourse.shopping.ui.products.ProductsViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue
import woowacourse.shopping.util.setUpTestLiveData

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductsViewModelTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var historyRepository: HistoryRepository
    private lateinit var viewModel: ProductsViewModel

    @BeforeEach
    fun setup() {
        productRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        historyRepository = mockk(relaxed = true)

        viewModel = ProductsViewModel(productRepository, cartRepository, historyRepository)
    }

    @Test
    fun `전체 상품 목록과 다른 상품 존재 여부를 불러온다`() {
        // given
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS, "_catalogProducts", viewModel)

        val newProduct = DUMMY_CATALOG_PRODUCT_2
        val newData = CatalogProducts(products = listOf(DUMMY_CATALOG_PRODUCT_2), hasMore = false)

        every {
            productRepository.fetchProducts(any(), any(), any())
        } answers {
            thirdArg<(CatalogProducts) -> Unit>().invoke(newData)
        }

        // when
        viewModel.loadCartProducts()

        // then
        val result = viewModel.catalogProducts.getOrAwaitValue()
        assertThat(result.products).containsExactlyElementsIn(DUMMY_CATALOG_PRODUCTS.products + newProduct)
        assertThat(result.hasMore).isFalse()
    }

    @Test
    fun `최근에 탐색한 상품 목록을 불러온다`() {
        // given
        val expected = listOf(DUMMY_HISTORY_PRODUCT_1)

        every {
            historyRepository.fetchAllSearchHistory(any())
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
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS, "_catalogProducts", viewModel)

        every {
            cartRepository.increaseProductQuantity(eq(productId), any(), any())
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
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS, "_catalogProducts", viewModel)

        every {
            cartRepository.decreaseProductQuantity(eq(productId), any(), any())
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
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS, "_catalogProducts", viewModel)

        val updatedProduct = DUMMY_CATALOG_PRODUCT_1.copy(quantity = 100)

        every {
            productRepository.fetchProduct(eq(productId), any())
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
        setUpTestLiveData(DUMMY_CATALOG_PRODUCTS, "_catalogProducts", viewModel)

        val updatedList = listOf(DUMMY_CATALOG_PRODUCT_1.copy(quantity = 6))

        every {
            productRepository.fetchProducts(any<List<Int>>(), any())
        } answers {
            secondArg<(List<CatalogProduct>) -> Unit>().invoke(updatedList)
        }

        // when
        viewModel.loadCartProducts(listOf(DUMMY_PRODUCT_1.id))

        // then
        val result = viewModel.catalogProducts.getOrAwaitValue()
        assertThat(result.products).containsExactlyElementsIn(updatedList)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
