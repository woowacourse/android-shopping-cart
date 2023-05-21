package woowacourse.shopping.data.datasource.local

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.datasource.ShopDataSource
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop

class ShopLocalDao(context: Context) : ShopDataSource {
    private val db: SQLiteDatabase = ShoppingDBOpenHelper(context).writableDatabase

    override fun selectByRange(products: List<Product>): Shop {
        return Shop(
            products.map {
                db.rawQuery(
                    "SELECT * FROM ${SqlCart.name} WHERE ${SqlCart.PRODUCT_ID} = ${it.id}", null
                ).use { cursor ->
                    if (cursor.moveToNext()) makeCartProduct(cursor, it)
                    else CartProduct(0, it)
                }
            }
        )
    }

    private fun makeCartProduct(cursor: Cursor, product: Product) = CartProduct(
        cursor.getInt(cursor.getColumnIndexOrThrow(SqlCart.AMOUNT)), product
    )
}
