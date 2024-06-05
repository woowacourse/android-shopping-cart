package woowacourse.shopping.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeShoppingCartProductIdDataSource
import woowacourse.shopping.testfixture.productDomainTestFixture
import woowacourse.shopping.testfixture.productDomainsTestFixture
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import java.util.concurrent.CountDownLatch

class DefaultShoppingProductRepositoryTest {
    private lateinit var productDataSource: ProductDataSource
    private lateinit var shoppingCartProductIdDataSource: ShoppingCartProductIdDataSource
    private lateinit var repository: ShoppingProductsRepository

    @BeforeEach
    fun setUp() {
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSource()
        repository = DefaultShoppingProductRepository(productDataSource, shoppingCartProductIdDataSource)
    }

    @Test
    fun `첫번째 페이지에 있는 상품을 모두 불러온다 async result `() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSource()
        repository = DefaultShoppingProductRepository(productDataSource, shoppingCartProductIdDataSource)

        val latch = CountDownLatch(1)
        var actual: List<Product> = emptyList()

        // when
        repository.loadAllProductsAsyncResult(page = 1) { result ->
            actual = result.getOrThrow()
            latch.countDown()
        }
        latch.await()
        assertThat(actual).isEqualTo(productDomainsTestFixture(dataCount = 20))
    }

    @Test
    fun `상품 상세에서 상품 id 로 상품을 찾는다 async result`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSource()
        repository = DefaultShoppingProductRepository(productDataSource, shoppingCartProductIdDataSource)

        val latch = CountDownLatch(1)
        var actual: Product = Product.NULL

        // when
        repository.loadProductAsyncResult(id = 1) { result ->
            actual = result.getOrThrow()
            latch.countDown()
        }
        latch.await()
        assertThat(actual).isEqualTo(productDomainTestFixture(1, quantity = 1))
    }

    @Test
    fun `상품 목록에서 100 개의 아이템에서 5 페이지는 마지막 페이지이다 async result`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSource()
        repository = DefaultShoppingProductRepository(productDataSource, shoppingCartProductIdDataSource)

        // when
        val latch = CountDownLatch(1)
        var actual = false

        repository.isFinalPageAsyncResult(page = 5) { result ->
            actual = result.getOrThrow()
            latch.countDown()
        }
        latch.await()
        assertThat(actual).isTrue
    }

    @Test
    fun `장바구니가 마지막 페이지이다 async result`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, quantity = 2).toMutableList(),
            )
        repository = DefaultShoppingProductRepository(productDataSource, shoppingCartProductIdDataSource)

        val latch = CountDownLatch(1)
        var actual = false

        // when
        repository.isCartFinalPageAsyncResult(page = 2) { result ->
            actual = result.getOrThrow()
            latch.countDown()
        }

        latch.await()
        assertThat(actual).isTrue
    }

    @Test
    fun `장바구니에 10개의 상품이 2개씩 있다면 상품의 개수는 20 개이다 async result`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, quantity = 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        val latch = CountDownLatch(1)
        var actual: Int = -1

        // when
        repository.shoppingCartProductQuantityAsyncResult { result ->
            actual = result.getOrThrow()
            latch.countDown()
        }
        latch.await()
        assertThat(actual).isEqualTo(20)
    }

    @Test
    fun `장바구니에 상품을 새로 추가한다 async result`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSource()
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        var addedProductId: Long = -1
        val latch = CountDownLatch(1)

        // when
        repository.addShoppingCartProductAsyncResult(id = 11) { result ->
            // then
            addedProductId = result.getOrThrow()
            latch.countDown()
        }

        latch.await()
        assertThat(addedProductId).isEqualTo(11)
    }
}
