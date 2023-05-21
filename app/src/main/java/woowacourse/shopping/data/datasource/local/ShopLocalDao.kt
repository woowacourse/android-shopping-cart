package woowacourse.shopping.data.datasource.local

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.datasource.ShopDataSource
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop
import woowacourse.shopping.domain.URL

class ShopLocalDao(private val db: SQLiteDatabase) : ShopDataSource {
    override fun selectByRange(start: Int, range: Int): Shop {
        val cursor = db.rawQuery(
            "SELECT ${SqlProduct.name}.*, COALESCE(${SqlCart.AMOUNT}, 0) as ${SqlCart.AMOUNT} FROM ${SqlProduct.name} left join ${SqlCart.name} on ${SqlProduct.ID} = ${SqlCart.PRODUCT_ID} LIMIT $start, $range",
            null
        )
        return makeCart(cursor)
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