package woowacourse.shopping.common.data.database.dao

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.common.data.database.table.SqlProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.URL

class ProductDao(private val db: SQLiteDatabase) {
    fun selectByRange(start: Int, range: Int): Products {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlProduct.name} LIMIT $start, $range", null
        )
        return Products(
            cursor.use {
                val products = mutableListOf<Product>()
                while (it.moveToNext()) products.add(
                    Product(
                        URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
                        it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
                        it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
                    )
                )

                products
            }
        )
    }
}
