package woowacourse.shopping.database.recentProduct

import android.content.Context
import woowacourse.shopping.database.ShoppingDBHelper
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.RecentRepository

class RecentProductDatabase(context: Context) : RecentRepository {
    private val db = ShoppingDBHelper(context).writableDatabase

    override fun insert(product: Product) {
        db.execSQL(RecentProductConstant.getDeleteQuery(product.id))
        db.execSQL(RecentProductConstant.getInsertQuery(product))
    }

    override fun getRecent(maxSize: Int): List<Product> {
        db.rawQuery(RecentProductConstant.getGetRecentProductQuery(maxSize), null).use {
            val products = mutableListOf<Product>()
            while (it.moveToNext()) {
                products.add(RecentProductConstant.fromCursor(it))
            }
            return products
        }
    }

    override fun findById(id: Int): Product? {
        db.rawQuery(RecentProductConstant.getGetQuery(id), null).use {
            if (it.count > 0) {
                it.moveToFirst()
                return RecentProductConstant.fromCursor(it)
            }
            return null
        }
    }

    override fun delete(id: Int) {
        db.rawQuery(RecentProductConstant.getDeleteQuery(id), null).use {
            it.moveToNext()
        }
    }
}
