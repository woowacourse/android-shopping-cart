package woowacourse.shopping.data.database.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.URL

class ProductDao(private val db: SQLiteDatabase) {
    fun selectByRange(start: Int, range: Int): Products {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlProduct.name} LIMIT $start, $range", null
        )
        return createProducts(cursor)
    }

    private fun createProducts(cursor: Cursor) = Products(
        cursor.use {
            val products = mutableListOf<Product>()
            while (it.moveToNext()) {
                products.add(createProduct(it))
            }
            products
        }
    )

    private fun createProduct(cursor: Cursor) = Product(
        URL(cursor.getString(cursor.getColumnIndexOrThrow(SqlProduct.PICTURE))),
        cursor.getString(cursor.getColumnIndexOrThrow(SqlProduct.TITLE)),
        cursor.getInt(cursor.getColumnIndexOrThrow(SqlProduct.PRICE)),
    )
}
