package woowacourse.shopping.common.data.database.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.common.data.database.selectRowId
import woowacourse.shopping.common.data.database.table.SqlProduct
import woowacourse.shopping.common.data.database.table.SqlRecentProduct
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.URL

class RecentProductDao(private val db: SQLiteDatabase) {
    fun insertRecentProduct(recentProductModel: RecentProductModel) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = recentProductModel.product.picture
        productRow[SqlProduct.TITLE] = recentProductModel.product.title
        productRow[SqlProduct.PRICE] = recentProductModel.product.price

        val row = ContentValues()
        row.put(SqlRecentProduct.ORDINAL, recentProductModel.ordinal)
        row.put(SqlRecentProduct.PRODUCT_ID, SqlProduct.selectRowId(db, productRow))
        db.insert(SqlRecentProduct.name, null, row)
    }

    fun selectAll(): RecentProducts {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlRecentProduct.name}, ${SqlProduct.name} on ${SqlRecentProduct.name}.${SqlRecentProduct.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID}",
            null
        )
        return RecentProducts(
            cursor.use {
                val recentProducts = mutableListOf<RecentProduct>()
                while (it.moveToNext()) recentProducts.add(
                    RecentProduct(
                        it.getInt(it.getColumnIndexOrThrow(SqlRecentProduct.ORDINAL)),
                        Product(
                            URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
                            it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
                            it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
                        )
                    )
                )
                recentProducts
            }
        )
    }
}
