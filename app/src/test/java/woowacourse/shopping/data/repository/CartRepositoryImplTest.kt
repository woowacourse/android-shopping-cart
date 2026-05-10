package woowacourse.shopping.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.DUMMY_PRODUCTS
import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.local.CartEntity
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem

class CartRepositoryImplTest {
    private val cartDao: CartDao = mockk()
    private lateinit var repository: CartRepositoryImpl
    private val products = DUMMY_PRODUCTS

    @BeforeEach
    fun setUp() {
        repository = CartRepositoryImpl(cartDao)
    }

    @Test
    fun `장바구니 항목을 추가하면 DAO의 upsert를 호출한다`() = runTest {
        // given
        val product = products[0]
        val cartItem = CartItem(product, Quantity(1))
        coEvery { cartDao.upsert(any()) } returns Unit

        // when
        repository.updateCart(cartItem)

        // then
        coVerify { cartDao.upsert(match { it.productId == product.id && it.quantity == 1 }) }
    }

    @Test
    fun `장바구니 항목을 삭제하면 DAO의 deleteCartItem을 호출한다`() = runTest {
        // given
        val productId = "1"
        coEvery { cartDao.deleteCartItem(productId) } returns Unit

        // when
        repository.deleteCartItem(productId)

        // then
        coVerify { cartDao.deleteCartItem(productId) }
    }

    @Test
    fun `수량 증가 시 기존 수량에서 1 증가된 값으로 upsert를 호출한다`() = runTest {
        // given
        val product = products[0]
        val existingEntity = CartEntity(product.id, product.productTitle.value, product.imageUrl, product.price.value, 1)
        coEvery { cartDao.getCartItem(product.id) } returns flowOf(existingEntity)
        coEvery { cartDao.upsert(any()) } returns Unit

        // when
        repository.increaseCartItemQuantity(product.id)

        // then
        coVerify { cartDao.upsert(match { it.productId == product.id && it.quantity == 2 }) }
    }

    @Test
    fun `수량 감소 시 기존 수량이 1보다 크면 1 감소된 값으로 upsert를 호출한다`() = runTest {
        // given
        val product = products[0]
        val existingEntity = CartEntity(product.id, product.productTitle.value, product.imageUrl, product.price.value, 2)
        coEvery { cartDao.getCartItem(product.id) } returns flowOf(existingEntity)
        coEvery { cartDao.upsert(any()) } returns Unit

        // when
        repository.decreaseCartItemQuantity(product.id)

        // then
        coVerify { cartDao.upsert(match { it.productId == product.id && it.quantity == 1 }) }
    }

    @Test
    fun `수량 감소 시 기존 수량이 1이면 아무 일도 일어나지 않는다`() = runTest {
        // given
        val product = products[0]
        val existingEntity = CartEntity(product.id, product.productTitle.value, product.imageUrl, product.price.value, 1)
        coEvery { cartDao.getCartItem(product.id) } returns flowOf(existingEntity)

        // when
        repository.decreaseCartItemQuantity(product.id)

        // then
        coVerify(exactly = 0) { cartDao.upsert(any()) }
        coVerify(exactly = 0) { cartDao.deleteCartItem(any()) }
    }

    @Test
    fun `페이지네이션 요청 시 DAO의 getPagingCartItems를 호출하여 결과를 반환한다`() = runTest {
        // given
        val pageSize = 5
        val page = 0
        val entities = products.take(pageSize).map { 
            CartEntity(it.id, it.productTitle.value, it.imageUrl, it.price.value, 1) 
        }
        coEvery { cartDao.getPagingCartItems(pageSize, 0) } returns entities

        // when
        val result = repository.getPagingCartItems(page, pageSize)

        // then
        assertThat(result.items).hasSize(pageSize)
        assertThat(result.items[0].product.id).isEqualTo(products[0].id)
        coVerify { cartDao.getPagingCartItems(pageSize, 0) }
    }
}
