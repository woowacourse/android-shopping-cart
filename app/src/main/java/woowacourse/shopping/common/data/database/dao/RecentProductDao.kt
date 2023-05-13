package woowacourse.shopping.common.data.database.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.common.data.database.selectRowId
import woowacourse.shopping.common.data.database.table.SqlProduct
import woowacourse.shopping.common.data.database.table.SqlRecentProduct
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.URL
import java.time.LocalDateTime

class RecentProductDao(private val db: SQLiteDatabase) {
    fun insertRecentProduct(recentProductModel: RecentProductModel) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = recentProductModel.product.picture
        productRow[SqlProduct.TITLE] = recentProductModel.product.title
        productRow[SqlProduct.PRICE] = recentProductModel.product.price

        val row = ContentValues()
        row.put(SqlRecentProduct.TIME, recentProductModel.time.toString())
        row.put(SqlRecentProduct.PRODUCT_ID, SqlProduct.selectRowId(db, productRow))
        db.insert(SqlRecentProduct.name, null, row)
    }

    fun selectAll(): RecentProducts {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlRecentProduct.name}, ${SqlProduct.name} ON ${SqlRecentProduct.name}.${SqlRecentProduct.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} " +
                "ORDER BY ${SqlRecentProduct.TIME} DESC",
            null
        )
        return RecentProducts(
            cursor.use {
                val recentProducts = mutableListOf<RecentProduct>()
                while (it.moveToNext()) recentProducts.add(
                    RecentProduct(
                        LocalDateTime.parse(it.getString(it.getColumnIndexOrThrow(SqlRecentProduct.TIME))),
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

    fun selectByProduct(product: ProductModel): RecentProduct? {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = product.picture
        productRow[SqlProduct.TITLE] = product.title
        productRow[SqlProduct.PRICE] = product.price

        val productId = SqlProduct.selectRowId(db, productRow)
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlRecentProduct.name}, ${SqlProduct.name} ON ${SqlRecentProduct.name}.${SqlRecentProduct.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} " +
                "WHERE ${SqlRecentProduct.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
        return cursor.use {
            if (it.moveToNext()) RecentProduct(
                LocalDateTime.parse(it.getString(it.getColumnIndexOrThrow(SqlRecentProduct.TIME))),
                Product(
                    URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
                    it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
                    it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
                )
            )
            else null
        }
    }

    fun updateRecentProduct(recentProductModel: RecentProductModel) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = recentProductModel.product.picture
        productRow[SqlProduct.TITLE] = recentProductModel.product.title
        productRow[SqlProduct.PRICE] = recentProductModel.product.price

        val productId = SqlProduct.selectRowId(db, productRow)
        val row = ContentValues()
        row.put(SqlRecentProduct.TIME, recentProductModel.time.toString())
        row.put(SqlRecentProduct.PRODUCT_ID, productId)

        db.update(SqlRecentProduct.name, row, "${SqlRecentProduct.PRODUCT_ID} = ?", arrayOf(productId.toString()))
    }
}
