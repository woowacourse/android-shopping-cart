package woowacourse.shopping.data.datasource.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.entity.ProductEntity
import woowacourse.shopping.data.mock.ProductMock
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL

class ProductLocalDao(context: Context) : ProductDataSource {
    private val db: SQLiteDatabase = ShoppingDBOpenHelper(context).writableDatabase

    override fun selectByRange(start: Int, range: Int): List<ProductEntity> {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlProduct.name} LIMIT $start, $range", null
        )
        return cursor.use {
            val products = mutableListOf<ProductEntity>()
            while (cursor.moveToNext()) products.add(makeProduct(it))
            products
        }
    }

    override fun initMockData() {
        if (selectAllCount() != 0) return
        repeat(100) {
            insertProduct(ProductMock.make())
        }
    }

    private fun insertProduct(product: Product) {
        val row = ContentValues()
        row.put(SqlProduct.TITLE, product.title)
        row.put(SqlProduct.PRICE, product.price)
        row.put(SqlProduct.PICTURE, product.picture.value)
        db.insert(SqlProduct.name, null, row)
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

    private fun makeProduct(it: Cursor) = ProductEntity(
        it.getInt(it.getColumnIndexOrThrow(SqlProduct.ID)),
        Product(
            URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
            it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
            it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE))
        ),
    )
}
