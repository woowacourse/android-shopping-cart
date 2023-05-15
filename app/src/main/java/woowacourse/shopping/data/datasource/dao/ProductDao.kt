package woowacourse.shopping.data.datasource.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.mock.ProductMock
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.URL

class ProductDao(private val db: SQLiteDatabase) : ProductDataSource {
    override fun selectByRange(start: Int, range: Int): Products {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlProduct.name} LIMIT $start, $range", null
        )
        return makeProducts(cursor)
    }

    override fun initMockData() {
        if (selectAllCount() != 0) return
        repeat(100) {
            insert(ProductMock.make())
        }
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

    private fun makeProducts(cursor: Cursor) = Products(
        cursor.use {
            val products = mutableListOf<Product>()
            while (it.moveToNext()) products.add(
                makeProduct(it)
            )
            products
        }
    )

    private fun makeProduct(it: Cursor) = Product(
        URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
        it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
        it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
    )

    private fun insert(product: Product) {
        val row = ContentValues()
        row.put(SqlProduct.TITLE, product.title)
        row.put(SqlProduct.PRICE, product.price)
        row.put(SqlProduct.PICTURE, product.picture.value)
        db.insert(SqlProduct.name, null, row)
    }
}
