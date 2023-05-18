package woowacourse.shopping.data.datasource.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.datasource.ShoppingDataSource
import woowacourse.shopping.data.mock.ProductMock
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop
import woowacourse.shopping.domain.URL

class ShoppingDao(private val db: SQLiteDatabase) : ShoppingDataSource {
    override fun selectByRange(start: Int, range: Int): Shop {
        val cursor = db.rawQuery(
            "SELECT ${SqlProduct.name}.*, COALESCE(${SqlCart.AMOUNT}, 0) as ${SqlCart.AMOUNT} FROM ${SqlProduct.name} left join ${SqlCart.name} on ${SqlProduct.ID} = ${SqlCart.PRODUCT_ID} LIMIT $start, $range",
            null
        )
        return makeCart(cursor)
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

    private fun makeCart(cursor: Cursor) = Shop(
        cursor.use {
            val cart = mutableListOf<CartProduct>()
            while (it.moveToNext()) cart.add(
                makeCartProduct(it)
            )
            cart
        }
    )

    private fun makeCartProduct(it: Cursor) = CartProduct(
        it.getInt(it.getColumnIndexOrThrow(SqlCart.AMOUNT)),
        makeProduct(it)
    )

    private fun makeProduct(it: Cursor) = Product(
        URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
        it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
        it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
    )
}
