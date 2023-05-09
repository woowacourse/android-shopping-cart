import com.example.domain.model.Cart
import com.example.domain.model.Product
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class CartTest {

    private lateinit var cart: Cart

    @Before
    fun setUp() {
    }

    @Test
    fun `상품을 담을 수 있다`() {
        //given
        cart = Cart(FakeCartRepository())
        val product = Product(
            1,
            "동원 스위트콘",
            10000,
            "https://contents.lotteon.com/itemimage/_v000651/LM/88/01/04/71/53/27/6_/00/1/LM8801047153276_001_3.jpg/dims/resizef/720X720"
        )

        //when
        cart.add(product)

        //then
        assertTrue(cart.getAll().contains(product))
    }

    @Test
    fun `상품을 뺄 수 있다`() {
        //given
        val product = Product(1, "동원 스위트홈", 10000, "")
        cart = Cart(FakeCartRepository(listOf(product)))

        //when
        cart.remove(product)

        //then
        assertFalse(cart.getAll().contains(product))
    }

    @Test
    fun `상품을 불러올 수 있다`() {
        //given
        val products = List(10) { Product(1, "동원 스위트홈", 10000, "") }
        cart = Cart(FakeCartRepository(products))

        //when
        val actual = cart.getAll()

        //then
        assertTrue(actual.size == 10)
    }


}
