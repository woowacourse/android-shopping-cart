package woowacourse.shopping.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeShoppingCartProductIdDataSource
import woowacourse.shopping.testfixture.productDomainTestFixture
import woowacourse.shopping.testfixture.productDomainsTestFixture
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture

class DefaultShoppingProductRepositoryTest {
    private lateinit var productDataSource: ProductDataSource
    private lateinit var shoppingCartProductIdDataSource: ShoppingCartProductIdDataSource
    private lateinit var repository: ShoppingProductsRepository

    @Test
    fun `첫번재 페이지 데이터 로드`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
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
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSource()
        repository =
            DefaultShoppingProductRepository(
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
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource = FakeShoppingCartProductIdDataSource()

        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.loadAllProductsAsync(page = 1) { loadedProducts ->
            // then
            assertThat(loadedProducts).isEqualTo(productDomainsTestFixture(dataCount = 20))
        }
    }

    @Test
    fun `장바구니 1 페이지의 장바구니에 있는 상품을 불러온다`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
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
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.loadProductsInCartAsync(page = 1) { actual ->
            // then
            assertThat(actual).isEqualTo(
                productDomainsTestFixture(dataCount = 5) { id ->
                    productDomainTestFixture(id.toLong(), quantity = 2)
                },
            )
        }
    }

    @Test
    fun `상품 id 로 상품을 찾는다`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
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
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.loadProductAsync(id = 1) { product ->
            // then
            assertThat(product).isEqualTo(productDomainTestFixture(1, quantity = 2))
        }
    }

    @Test
    fun `상품 목록에서 100 개의 아이템에서 5페이지는 페이지이다`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        val isFinalPage = repository.isFinalPage(page = 5)

        // then
        assertThat(isFinalPage).isTrue
    }

    @Test
    fun `상품 목록에서 100 개의 아이템에서 5페이지는 페이지이다 Async`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.isFinalPageAsync(page = 5) { isFinalPage ->
            // then
            assertThat(isFinalPage).isTrue
        }
    }

    @Test
    fun `장바구니가 마지막 페이지이다`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
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
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.isCartFinalPageAsync(page = 2) { isFinalPage ->
            // then
            assertThat(isFinalPage).isTrue
        }
    }

    @Test
    fun `장바구니에 10개의 상품이 2개씩 있다면 상품의 개수를 20 개`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
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
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.shoppingCartProductQuantityAsync { actual ->
            // then
            assertThat(actual).isEqualTo(20)
        }
    }

    @Test
    fun `장바구니에 상품을 새로 추가한다`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
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
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.addShoppingCartProductAsync(id = 11) {
            // then
            assertThat(repository.loadProduct(id = 11).quantity).isEqualTo(1)
        }
    }

    @Test
    fun `장바구니에 상품을 담을 때, 그 상품이 장바구니에 그 상품이 없다면 새로 담는다`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
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
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        val shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        val repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.increaseShoppingCartProductAsync(id = 11) {
            // then
            assertThat(repository.loadProduct(id = 11).quantity).isEqualTo(1)
        }
    }

    @Test
    fun `장바구니에 상품을 담을 때, 그 상품이 장바구니에 이미 있다면 개수를 증가시킨다`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
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
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        repository.increaseShoppingCartProductAsync(id = 1) {
            // then
            assertThat(repository.loadProduct(id = 1).quantity).isEqualTo(3)
        }
    }
}
