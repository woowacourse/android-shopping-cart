package woowacourse.shopping.common.data.database.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.common.data.database.selectRowId
import woowacourse.shopping.common.data.database.table.SqlCart
import woowacourse.shopping.common.data.database.table.SqlProduct
import woowacourse.shopping.common.model.RecentProductModel

class RecentProductDao(private val db: SQLiteDatabase) {
    fun insertRecentProduct(recentProductModel: RecentProductModel) {
        db.use {
            val productRow: MutableMap<String, Any> = mutableMapOf()
            productRow[SqlProduct.PICTURE] = recentProductModel.product.picture
            productRow[SqlProduct.TITLE] = recentProductModel.product.title
            productRow[SqlProduct.PRICE] = recentProductModel.product.price

            val row = ContentValues()
            row.put(SqlCart.ORDINAL, recentProductModel.ordinal)
            row.put(SqlCart.PRODUCT_ID, SqlProduct.selectRowId(db, productRow))
            it.insert(SqlCart.name, null, row)
        }
    }
}
