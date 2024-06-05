package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.CoroutinesTestExtension
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.CartProductEntity
import woowacourse.shopping.data.CartRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(CoroutinesTestExtension::class)
class CartViewModelTest {

    private lateinit var vm: CartViewModel
    private lateinit var cartRepository: CartRepository

    @BeforeEach
    fun setup() {
        cartRepository = mockk(relaxed = true)
        vm = CartViewModel(cartRepository)
    }

    @Test
    fun `장바구니 데이터를 가져온다`() = runTest {
        // given
        coEvery { cartRepository.getAllCartProducts() } returns listOf(
            CartProductEntity("오둥이", 1_000, ""),
            CartProductEntity("심지", 1_000, ""),
        )

        // when
        vm.getAllCartProducts()
        delay(3000)

        // then
        val actual = vm.cartProducts.value
        assertThat(actual).hasSize(2)
    }

}
