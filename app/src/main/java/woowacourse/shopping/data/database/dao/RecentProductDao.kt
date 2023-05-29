package woowacourse.shopping.data.database.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.selectRowId
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.database.table.SqlRecentProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.URL
import java.time.LocalDateTime

class RecentProductDao(private val db: SQLiteDatabase) {
    fun insertRecentProduct(recentProduct: RecentProduct) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = recentProduct.product.picture.value
        productRow[SqlProduct.TITLE] = recentProduct.product.title
        productRow[SqlProduct.PRICE] = recentProduct.product.price

        val row = ContentValues()
        row.put(SqlRecentProduct.TIME, recentProduct.time.toString())
        row.put(SqlRecentProduct.PRODUCT_ID, SqlProduct.selectRowId(db, productRow))
        db.insert(SqlRecentProduct.name, null, row)
    }

    fun selectAll(): RecentProducts {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlRecentProduct.name}, ${SqlProduct.name} ON ${SqlRecentProduct.name}.${SqlRecentProduct.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} " +
                "ORDER BY ${SqlRecentProduct.TIME} DESC",
            null
        )
        return createRecentProducts(cursor)
    }

    private fun createRecentProducts(cursor: Cursor) = RecentProducts(
        cursor.use {
            val recentProducts = mutableListOf<RecentProduct>()
            while (it.moveToNext()) {
                recentProducts.add(createRecentProduct(it))
            }
            recentProducts
        }
    )

    private fun createRecentProduct(cursor: Cursor) = RecentProduct(
        LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow(SqlRecentProduct.TIME))),
        Product(
            URL(cursor.getString(cursor.getColumnIndexOrThrow(SqlProduct.PICTURE))),
            cursor.getString(cursor.getColumnIndexOrThrow(SqlProduct.TITLE)),
            cursor.getInt(cursor.getColumnIndexOrThrow(SqlProduct.PRICE)),
        )
    )

    fun selectByProduct(product: Product): RecentProduct? {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = product.picture.value
        productRow[SqlProduct.TITLE] = product.title
        productRow[SqlProduct.PRICE] = product.price

        val productId = SqlProduct.selectRowId(db, productRow)
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlRecentProduct.name}, ${SqlProduct.name} ON ${SqlRecentProduct.name}.${SqlRecentProduct.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} " +
                "WHERE ${SqlRecentProduct.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
        return cursor.use {
            if (it.moveToNext()) createRecentProduct(it)
            else null
        }
    }

    fun updateRecentProduct(recentProduct: RecentProduct) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = recentProduct.product.picture.value
        productRow[SqlProduct.TITLE] = recentProduct.product.title
        productRow[SqlProduct.PRICE] = recentProduct.product.price

        val productId = SqlProduct.selectRowId(db, productRow)
        val row = ContentValues()
        row.put(SqlRecentProduct.TIME, recentProduct.time.toString())
        row.put(SqlRecentProduct.PRODUCT_ID, productId)

        db.update(
            SqlRecentProduct.name,
            row,
            "${SqlRecentProduct.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
    }

    fun selectLatestRecentProduct(): RecentProduct? {
        val cursor = db.rawQuery(
            """
                |SELECT * FROM ${SqlRecentProduct.name}, ${SqlProduct.name} ON ${SqlRecentProduct.name}.${SqlRecentProduct.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} 
                |ORDER BY ${SqlRecentProduct.TIME} DESC
                |LIMIT 1
            """.trimMargin(),
            null
        )

        return cursor.use {
            if (it.moveToNext()) createRecentProduct(cursor)
            else null
        }
    }
}
