package woowacourse.shopping.data.database.dao.basket

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import woowacourse.shopping.data.database.ShoppingDatabase

class BasketDaoImplTest {
    private lateinit var sut: BasketDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sut = BasketDaoImpl(ShoppingDatabase(context))
    }

//    제이슨이 짜준 예시
//    @Test
//    fun name() {
//        sut.add(Product(1, "", DataPrice(1000), ""))
//        val products = sut.getPartially(5, -1, true)
//        assertEquals(1, products.size)
//    }
}
