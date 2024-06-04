package study.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import study.ProductDataSourceStudy
import study.ShoppingCartProductIdDataSourceStudy
import study.source.FakeProductDataSourceStudy
import study.source.FakeShoppingCartProductIdDataSourceStudy
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.testfixture.productDomainTestFixture
import woowacourse.shopping.testfixture.productDomainsTestFixture
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import java.util.concurrent.CountDownLatch

class DefaultShoppingProductRepositoryTestStudy {
    private lateinit var productDataSource: ProductDataSourceStudy
    private lateinit var shoppingCartProductIdDataSource: ShoppingCartProductIdDataSourceStudy
    private lateinit var repository: ShoppingProductsRepositoryStudy

    @Test
    fun `첫번재 페이지 데이터 로드`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        val loadedProducts = repository.loadAllProducts(page = 1)

        // then
        // expected: id 가 0~9 이면 상품 수량이 2 개, 나머지는 0개
        val expected =
            productDomainsTestFixture(dataCount = 20) { id ->
                when (id) {
                    in 0..9 -> productDomainTestFixture(id.toLong(), quantity = 2)
                    else -> productDomainTestFixture(id.toLong(), quantity = 0)
                }
            }

        assertThat(loadedProducts).isEqualTo(expected)
    }

    @Test
    fun `1 페이지에 있는 상품을 모두 불러온다`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSourceStudy()
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        val loadedProducts = repository.loadAllProducts(page = 1)

        // then
        assertThat(loadedProducts).isEqualTo(productDomainsTestFixture(dataCount = 20))
    }

    @Test
    fun `1 페이지에 있는 상품을 모두 불러온다 aync`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSourceStudy()

        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        var actualLoadedProducts: List<Product> = emptyList()
        val latch = CountDownLatch(1)

        // when
        repository.loadAllProductsAsync(page = 1) { loadedProducts ->
            actualLoadedProducts = loadedProducts
            latch.countDown()
        }

        latch.await()
        assertThat(actualLoadedProducts).isEqualTo(productDomainsTestFixture(dataCount = 20))
    }

    @Test
    fun `장바구니 1 페이지의 장바구니에 있는 상품을 불러온다`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        val actual = repository.loadProductsInCart(page = 1)

        // then
        assertThat(actual).isEqualTo(
            productDomainsTestFixture(dataCount = 5) { id ->
                productDomainTestFixture(id.toLong(), quantity = 2)
            },
        )
    }

    @Test
    fun `장바구니 1 페이지의 장바구니에 있는 상품을 불러온다 aync`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        val latch = CountDownLatch(1)
        var actual: List<Product> = emptyList()

        // when
        repository.loadProductsInCartAsync(page = 1) { products ->
            // then
            actual = products
            latch.countDown()
        }
        latch.await()
        assertThat(actual).isEqualTo(
            productDomainsTestFixture(dataCount = 5) { id ->
                productDomainTestFixture(id.toLong(), quantity = 2)
            },
        )
    }

    @Test
    fun `상품 id 로 상품을 찾는다`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        val actual = repository.loadProduct(id = 1)

        // then
        assertThat(actual).isEqualTo(productDomainTestFixture(1, quantity = 2))
    }

    @Test
    fun `상품 id 로 상품을 찾는다 async`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )
        val latch = CountDownLatch(1)
        var actual: Product = Product.NULL

        // when
        repository.loadProductAsync(id = 1) { product ->
            actual = product
            latch.countDown()
            // then
        }
        latch.await()
        assertThat(actual).isEqualTo(productDomainTestFixture(1, quantity = 2))
    }

    @Test
    fun `상품 목록에서 100 개의 아이템에서 5페이지는 마지막 페이지이다`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        val isFinalPage = repository.isFinalPage(page = 5)

        // then
        assertThat(isFinalPage).isTrue
    }

    @Test
    fun `상품 목록에서 100 개의 아이템에서 5페이지는 마지막 페이지이다 Async`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        val latch = CountDownLatch(1)
        var actual = false

        // when
        repository.isFinalPageAsync(page = 5) { isFinalPage ->
            // then
            actual = isFinalPage
            latch.countDown()
        }
        latch.await()
        assertThat(actual).isTrue
    }

    @Test
    fun `장바구니가 마지막 페이지이다`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        val isFinalPage = repository.isCartFinalPage(page = 2)

        // then
        assertThat(isFinalPage).isTrue
    }

    @Test
    fun `장바구니가 마지막 페이지이다 async`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        val latch = CountDownLatch(1)
        var actual = false

        // when
        repository.isCartFinalPageAsync(page = 2) { isFinalPage ->
            actual = isFinalPage
            // then
            latch.countDown()
        }
        latch.await()
        assertThat(actual).isTrue
    }

    @Test
    fun `장바구니에 10개의 상품이 2개씩 있다면 상품의 개수를 20 개`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        val actual = repository.shoppingCartProductQuantity()

        // then
        assertThat(actual).isEqualTo(20)
    }

    @Test
    fun `장바구니에 10개의 상품이 2개씩 있다면 상품의 개수를 20 개 async`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        val latch = CountDownLatch(1)
        var actual: Int = -1

        // when
        repository.shoppingCartProductQuantityAsync { quantity ->
            actual = quantity
            latch.countDown()
        }
        latch.await()
        assertThat(actual).isEqualTo(20)
    }

    @Test
    fun `장바구니에 상품을 새로 추가한다`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.addShoppingCartProduct(id = 11)

        // then
        assertThat(repository.loadProduct(id = 11).quantity).isEqualTo(1)
    }

    @Test
    fun `장바구니에 상품을 새로 추가한다 async`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        val latch = CountDownLatch(1)
        var totalQuantity: Int = -1

        // when
        repository.addShoppingCartProductAsync(id = 11) {
            totalQuantity = repository.loadProduct(id = 11).quantity
            latch.countDown()
        }
        latch.await()
        assertThat(totalQuantity).isEqualTo(1)
    }

    @Test
    fun `장바구니에 상품을 담을 때, 그 상품이 장바구니에 그 상품이 없다면 새로 담는다`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.increaseShoppingCartProduct(id = 11)

        // then
        assertThat(repository.loadProduct(id = 11).quantity).isEqualTo(1)
    }

    @Test
    fun `장바구니에 상품을 담을 때, 그 상품이 장바구니에 그 상품이 없다면 새로 담는다 async`() {
        // given
        val productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        val shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        val repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        val latch = CountDownLatch(1)
        var totalQuantity: Int = -1

        // when
        repository.increaseShoppingCartProductAsync(id = 11) {
            // then
            totalQuantity = repository.loadProduct(id = 11).quantity
            latch.countDown()
        }
        latch.await()
        assertThat(totalQuantity).isEqualTo(1)
    }

    @Test
    fun `장바구니에 상품을 담을 때, 그 상품이 장바구니에 이미 있다면 개수를 증가시킨다`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.increaseShoppingCartProduct(id = 1)

        // then
        assertThat(repository.loadProduct(id = 1).quantity).isEqualTo(3)
    }

    @Test
    fun `장바구니에 상품을 담을 때, 장바구니에 담긴 상품이 이미 있다면 개수를 증가시킨다 async`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepositoryStudy(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        val latch = CountDownLatch(1)
        var totalQuantity: Int = -1

        // when
        repository.increaseShoppingCartProductAsync(id = 1) {
            totalQuantity = repository.loadProduct(id = 1).quantity
            latch.countDown()
            // then
        }
        latch.await()
        assertThat(totalQuantity).isEqualTo(3)
    }

    @Test
    fun `첫번째 페이지에 있는 상품을 모두 불러온다 async result `() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSourceStudy()
        repository = DefaultShoppingProductRepositoryStudy(productDataSource, shoppingCartProductIdDataSource)

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
    fun `상품 id 로 상품을 찾는다 async result`() {
        // given
        productDataSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSourceStudy()
        repository = DefaultShoppingProductRepositoryStudy(productDataSource, shoppingCartProductIdDataSource)

        val latch = CountDownLatch(1)
        var actual: Product = Product.NULL

        // when
        repository.loadProductAsyncResult(id = 1) { result ->
            actual = result.getOrThrow()
            latch.countDown()
        }
        latch.await()
        assertThat(actual).isEqualTo(productDomainTestFixture(1, quantity = 0))
    }

    @Test
    fun `상품 목록에서 100 개의 아이템에서 5 페이지는 마지막 페이지이다 async result`() {
        // given
        productDataSource = FakeProductDataSourceStudy(
            allProducts = productsTestFixture(count = 100).toMutableList()
        )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSourceStudy()
        repository = DefaultShoppingProductRepositoryStudy(productDataSource, shoppingCartProductIdDataSource)

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
        productDataSource = FakeProductDataSourceStudy(
            allProducts = productsTestFixture(count = 100).toMutableList()
        )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSourceStudy(
            data = productsIdCountDataTestFixture(dataCount = 10, quantity = 2).toMutableList()
        )
        repository = DefaultShoppingProductRepositoryStudy(productDataSource, shoppingCartProductIdDataSource)

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
        productDataSource = FakeProductDataSourceStudy(
            allProducts = productsTestFixture(count = 100).toMutableList()
        )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSourceStudy(
            data = productsIdCountDataTestFixture(dataCount = 10, quantity = 2).toMutableList()
        )
        repository = DefaultShoppingProductRepositoryStudy(
            productsSource = productDataSource,
            cartSource = shoppingCartProductIdDataSource
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
        productDataSource = FakeProductDataSourceStudy(
            allProducts = productsTestFixture(count = 100).toMutableList()
        )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSourceStudy()
        repository = DefaultShoppingProductRepositoryStudy(
            productsSource = productDataSource,
            cartSource = shoppingCartProductIdDataSource
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
