package woowacourse.shopping.data

import android.content.Context
import woowacourse.shopping.data.db.CartDBHelper
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository

class CartDbRepository(context: Context) : CartRepository {
    private val dbHelper = CartDBHelper(context)
    override fun findAll(): List<CartProduct> {
        return dbHelper.selectAll()
    }

    private fun find(id: Int): List<CartProduct> {
        return dbHelper.selectWhereId(id)
    }

    override fun add(id: Int, count: Int) {
        val cardProducts = find(id)
        if (cardProducts.isNotEmpty()) {
            dbHelper.update(id, count + cardProducts[0].count)
            return
        }
        dbHelper.insert(id, count)
    }

    override fun remove(id: Int) {
        dbHelper.remove(id)
    }
}
