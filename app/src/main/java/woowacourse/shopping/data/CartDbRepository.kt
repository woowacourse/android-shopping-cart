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

    override fun add(id: Int, count: Int, check: Boolean) {
        val cardProduct = find(id)
        if (cardProduct != null) {
            dbHelper.update(id, count + cardProduct.count)
            return
        }
        dbHelper.insert(id, count, check)
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

    override fun plusCount(id: Int) {
        dbHelper.plusCount(id)
    }

    override fun subCount(id: Int) {
        dbHelper.subCount(id)
    }

    override fun findCheckedItem(): List<CartProduct> {
        return dbHelper.selectChecked()
    }

    override fun updateCheckState(id: Int, checked: Boolean) {
        dbHelper.updateCheck(id, checked)
    }
}
