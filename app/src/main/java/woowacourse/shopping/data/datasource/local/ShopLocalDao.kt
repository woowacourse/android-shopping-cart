package woowacourse.shopping.data.datasource.local

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.datasource.ShopDataSource
import woowacourse.shopping.data.datasource.entity.ProductEntity
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Shop

class ShopLocalDao(private val db: SQLiteDatabase) : ShopDataSource {
    override fun selectByRange(products: List<ProductEntity>): Shop {
        return Shop(
            products.map {
                db.rawQuery(
                    "SELECT * FROM ${SqlCart.name} WHERE ${SqlCart.PRODUCT_ID} = ${it.id}", null
                ).use { cursor ->
                    if (cursor.moveToNext()) makeCartProduct(cursor, it)
                    else CartProduct(0, it.product)
                }
            }
        )
    }

    private fun makeCartProduct(cursor: Cursor, product: ProductEntity) = CartProduct(
        cursor.getInt(cursor.getColumnIndexOrThrow(SqlCart.AMOUNT)), product.product
    )
}
