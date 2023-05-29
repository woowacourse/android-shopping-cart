package woowacourse.shopping.data.database.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.domain.URL

class ProductDao(private val db: SQLiteDatabase) {
    fun selectByRange(start: Int, range: Int): Products {
        val productColumns = SqlProduct.scheme.joinToString {
            column ->
            "${SqlProduct.name}.${column.name}"
        }

        val cursor = db.rawQuery(
            """
                |SELECT $productColumns, IFNULL(${SqlCart.name}.${SqlCart.AMOUNT}, 0) as ${SqlCart.AMOUNT} FROM ${SqlProduct.name} 
                |LEFT OUTER JOIN ${SqlCart.name} ON ${SqlProduct.ID} = ${SqlCart.PRODUCT_ID} 
                |LIMIT $start, $range
            """.trimMargin(),
            null
        )
        return createProducts(cursor)
    }

    private fun createProducts(cursor: Cursor) = Products(
        cursor.use {
            val products = mutableListOf<ShoppingProduct>()
            while (it.moveToNext()) {
                products.add(createShoppingProduct(it))
            }
            products
        }
    )

    private fun createShoppingProduct(cursor: Cursor) = ShoppingProduct(
        product = createProduct(cursor),
        amount = cursor.getInt(cursor.getColumnIndexOrThrow(SqlCart.AMOUNT))
    )

    private fun createProduct(cursor: Cursor) = Product(
        URL(cursor.getString(cursor.getColumnIndexOrThrow(SqlProduct.PICTURE))),
        cursor.getString(cursor.getColumnIndexOrThrow(SqlProduct.TITLE)),
        cursor.getInt(cursor.getColumnIndexOrThrow(SqlProduct.PRICE))
    )

    fun insertProduct(product: Product) {
        val row = ContentValues()
        row.put(SqlProduct.PICTURE, product.picture.value)
        row.put(SqlProduct.TITLE, product.title)
        row.put(SqlProduct.PRICE, product.price)

        db.insert(SqlProduct.name, null, row)
    }
}
