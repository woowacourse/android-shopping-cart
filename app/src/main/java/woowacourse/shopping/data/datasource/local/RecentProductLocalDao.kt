package woowacourse.shopping.data.datasource.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.database.table.SqlRecentProduct
import woowacourse.shopping.data.datasource.RecentProductDataSource
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.URL

class RecentProductLocalDao(context: Context) : RecentProductDataSource {
    private val db: SQLiteDatabase = ShoppingDBOpenHelper(context).writableDatabase
    override fun insertRecentProduct(recentProduct: RecentProduct) {
        val row = ContentValues()
        row.put(SqlRecentProduct.ORDINAL, recentProduct.ordinal)
        row.put(SqlRecentProduct.PRODUCT_ID, recentProduct.product.id)
        db.insert(SqlRecentProduct.name, null, row)
    }

    override fun selectAll(): RecentProducts {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlRecentProduct.name}, ${SqlProduct.name} on ${SqlRecentProduct.name}.${SqlRecentProduct.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID}",
            null
        )
        return makeRecentProducts(cursor)
    }

    private fun makeRecentProducts(cursor: Cursor) = RecentProducts(
        cursor.use {
            val recentProducts = mutableListOf<RecentProduct>()
            while (it.moveToNext()) recentProducts.add(
                makeRecentProduct(it)
            )
            recentProducts
        }
    )

    private fun makeRecentProduct(it: Cursor) = RecentProduct(
        it.getInt(it.getColumnIndexOrThrow(SqlRecentProduct.ORDINAL)), makeProduct(it)
    )

    private fun makeProduct(it: Cursor) = Product(
        it.getInt(it.getColumnIndexOrThrow(SqlProduct.ID)),
        URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
        it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
        it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
    )
}
