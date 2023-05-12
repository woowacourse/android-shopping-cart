package woowacourse.shopping.common.data.database.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.common.data.database.table.SqlProduct
import woowacourse.shopping.common.data.mock.ProductMock
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

    private fun selectAllCount(): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM ${SqlProduct.name}", null
        )
        return cursor.use {
            it.moveToNext()
            it.getInt(0)
        }
    }

    private fun insert(product: Product) {
        val row = ContentValues()
        row.put(SqlProduct.TITLE, product.title)
        row.put(SqlProduct.PRICE, product.price)
        row.put(SqlProduct.PICTURE, product.picture.value)
        db.insert(SqlProduct.name, null, row)
    }

    fun initMockData() {
        if (selectAllCount() != 0) return
        repeat(100) {
            insert(ProductMock.make())
        }
    }
}
