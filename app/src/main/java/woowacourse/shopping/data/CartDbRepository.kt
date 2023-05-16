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

    private fun find(id: Int): CartProduct? {
        return dbHelper.selectWhereId(id)
    }

    override fun add(id: Int, count: Int) {
        val cardProduct = find(id)
        if (cardProduct != null) {
            dbHelper.update(id, count + cardProduct.count)
            return
        }
        dbHelper.insert(id, count)
    }

    override fun update(id: Int, count: Int) {
        dbHelper.update(id, count)
    }

    override fun remove(id: Int) {
        dbHelper.remove(id)
    }

    override fun findRange(mark: Int, rangeSize: Int): List<CartProduct> {
        return dbHelper.selectRange(mark, rangeSize)
    }

    override fun isExistByMark(mark: Int): Boolean {
        return dbHelper.getSize(mark)
    }
}
