package woowacourse.shopping.view.product

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.shoppingCart.ShoppingCartProduct
import woowacourse.shopping.fixture.PRODUCT_ITEMS_20
import woowacourse.shopping.fixture.PRODUCT_ITEMS_20_MORE
import woowacourse.shopping.fixture.PRODUCT_ITEMS_20_MORE_AND_RECENT
import woowacourse.shopping.fixture.RECENT_PRODUCTS
import woowacourse.shopping.fixture.getProducts
import woowacourse.shopping.view.common.InstantTaskExecutorExtension
import woowacourse.shopping.view.common.getOrAwaitValue
import woowacourse.shopping.view.product.ProductsItem.LoadItem
import woowacourse.shopping.view.product.ProductsItem.ProductItem

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var productViewModel: ProductsViewModel
    private lateinit var productsRepository: ProductsRepository
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @BeforeEach
    fun setUp() {
        productsRepository = mockk(relaxed = true)
        shoppingCartRepository = mockk(relaxed = true)

        productViewModel =
            ProductsViewModel(
                productsRepository = productsRepository,
                shoppingCartRepository = shoppingCartRepository,
            )
    }

    @Test
    fun `상품 목록 업데이트 성공 시 결과값 개수가 20개 초과일 경우 ProductItem 과 LoadItem 추가된 products 값이 된다`() {
        // given
        every {
            productsRepository.load(null, 21, captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(getProducts(21)))
        }

        every {
            productsRepository.getRecentWatchingProducts(any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(emptyList()))
        }

        every {
            shoppingCartRepository.fetchSelectedQuantity(any<List<Product>>(), captureLambda())
        } answers {
            lambda<(Result<List<ShoppingCartProduct>>) -> Unit>().invoke(Result.success(emptyList()))
        }

        // when
        productViewModel.updateProducts()

        // then
        val result = productViewModel.products.getOrAwaitValue()
        assertAll({
            assertThat(result.count { it is ProductItem }).isEqualTo(20)
            assertThat(result.last()).isInstanceOf(LoadItem::class.java)
            assertThat(result).isEqualTo(PRODUCT_ITEMS_20_MORE)
        })
    }

    @Test
    fun `상품 목록 업데이트 성공 시 결과값 개수가 20개 초과이면서 최근 본 상품이 있을 경우 최근 본 상품, ProductItem 과 LoadItem 추가된 products 값이 된다`() {
        // given
        every {
            productsRepository.load(null, 21, captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(getProducts(21)))
        }

        every {
            productsRepository.getRecentWatchingProducts(any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(RECENT_PRODUCTS))
        }

        every {
            shoppingCartRepository.fetchSelectedQuantity(any<List<Product>>(), captureLambda())
        } answers {
            lambda<(Result<List<ShoppingCartProduct>>) -> Unit>().invoke(Result.success(emptyList()))
        }

        // when
        productViewModel.updateProducts()

        // then
        val result = productViewModel.products.getOrAwaitValue()
        assertThat(result).isEqualTo(PRODUCT_ITEMS_20_MORE_AND_RECENT)
    }

    @Test
    fun `상품 목록 업데이트 성공 시 결과값 개수가 20개 이하일 경우 ProductItem 만 추가된 products 값이 된다`() {
        // given:
        every {
            productsRepository.load(any(), any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(getProducts(20)))
        }

        every {
            productsRepository.getRecentWatchingProducts(any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(emptyList()))
        }

        every {
            shoppingCartRepository.fetchSelectedQuantity(any<List<Product>>(), captureLambda())
        } answers {
            lambda<(Result<List<ShoppingCartProduct>>) -> Unit>().invoke(Result.success(emptyList()))
        }

        // when:
        productViewModel.updateProducts()

        // then:
        val result = productViewModel.products.getOrAwaitValue()
        assertAll({
            assertThat(result.count { it is ProductItem }).isEqualTo(20)
            assertThat(result).isEqualTo(PRODUCT_ITEMS_20)
        })
    }

    @Test
    fun `상품 목록 업데이트 성공 시 결과값이 비어있을 경우 products 는 빈 값 이다`() {
        // given:
        every {
            productsRepository.load(any(), any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(emptyList()))
        }

        every {
            productsRepository.getRecentWatchingProducts(any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(emptyList()))
        }

        every {
            shoppingCartRepository.fetchSelectedQuantity(any<List<Product>>(), captureLambda())
        } answers {
            lambda<(Result<List<ShoppingCartProduct>>) -> Unit>().invoke(Result.success(emptyList()))
        }

        // when:
        productViewModel.updateProducts()

        // then:
        val result = productViewModel.products.getOrAwaitValue()
        assertAll({
            assertThat(result.count { it is ProductItem }).isEqualTo(0)
            assertThat(result).isEqualTo(emptyList<ProductsItem>())
        })
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }
}
