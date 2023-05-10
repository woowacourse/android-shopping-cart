package woowacourse.shopping.data

import android.content.Context
import woowacourse.shopping.data.db.RecentViewedDBHelper
import woowacourse.shopping.domain.RecentViewedRepository

class RecentViewedDbRepository(context: Context) :
    RecentViewedRepository {
    private val dbHelper = RecentViewedDBHelper(context)

    override fun findAll(): List<Int> {
        return dbHelper.selectAll()
    }

    override fun add(id: Int) {
        if (find(id).isNotEmpty()) {
            dbHelper.remove(id)
        }
        if (findAll().size == 10) {
            dbHelper.removeOldest()
        }
        dbHelper.insert(id)
    }

    private fun find(id: Int): List<Int> {
        return dbHelper.selectWhereId(id)
    }

    override fun remove(id: Int) {
    }
}
