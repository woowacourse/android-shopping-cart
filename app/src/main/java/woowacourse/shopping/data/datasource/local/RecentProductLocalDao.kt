package woowacourse.shopping.data.datasource.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.database.table.SqlRecentProduct
import woowacourse.shopping.data.datasource.RecentProductDataSource
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts

class RecentProductLocalDao(context: Context) : RecentProductDataSource {
    private val db: SQLiteDatabase = ShoppingDBOpenHelper(context).writableDatabase
    override fun insertRecentProduct(recentProduct: RecentProduct) {
        val row = ContentValues()
        row.put(SqlRecentProduct.ORDINAL, recentProduct.ordinal)
        row.put(SqlRecentProduct.PRODUCT_ID, recentProduct.product.id)
        db.insert(SqlRecentProduct.name, null, row)
    }

    override fun selectAll(products: List<Product>): RecentProducts {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlRecentProduct.name}", null
        )
        return makeRecentProducts(cursor, products)
    }

    private fun makeRecentProducts(cursor: Cursor, products: List<Product>) =
        RecentProducts(
            cursor.use {
                val recentProducts = mutableListOf<RecentProduct>()
                while (it.moveToNext()) recentProducts.add(
                    makeRecentProduct(it, products)
                )
                recentProducts
            }
        )

    private fun makeRecentProduct(cursor: Cursor, products: List<Product>) =
        RecentProduct(
            cursor.getInt(cursor.getColumnIndexOrThrow(SqlRecentProduct.ORDINAL)),
            products.first { it.id == cursor.getInt(cursor.getColumnIndexOrThrow(SqlCart.PRODUCT_ID)) }
        )
}
