package woowacourse.shopping.database.recentProduct

import android.content.Context
import woowacourse.shopping.database.ShoppingDBHelper
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.RecentProduct
import woowacourse.shopping.repository.RecentRepository

class RecentProductDatabase(context: Context) : RecentRepository {
    private val db = ShoppingDBHelper(context).writableDatabase

    override fun insert(product: Product) {
        db.execSQL(RecentProductConstant.getDeleteQuery(product.id))
        val recentProducts = RecentProduct(
            id = product.id,
            name = product.name,
            price = product.price,
            imageUrl = product.imageUrl
        )
        db.execSQL(RecentProductConstant.getInsertQuery(recentProducts))
    }

    override fun getRecent(maxSize: Int): List<RecentProduct> {
        db.rawQuery(RecentProductConstant.getGetRecentProductQuery(maxSize), null).use {
            val products = mutableListOf<RecentProduct>()
            while (it.moveToNext()) {
                products.add(RecentProductConstant.fromCursor(it))
            }
            return products
        }
    }

    override fun findById(id: Int): RecentProduct? {
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
