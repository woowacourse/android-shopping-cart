package woowacourse.shopping.data.database.dao.basket

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.Product

class BasketDaoImplTest {
    private lateinit var sut: BasketDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sut = BasketDaoImpl(ShoppingDatabase(context))
    }

    @Test
    fun name() {
        sut.add(Product(1, "", DataPrice(1000), ""))
        val products = sut.getPartially(5, -1, true)
        assertEquals(1, products.size)
    }
}
