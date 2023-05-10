package woowacourse.shopping.common.data.database.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.common.data.database.selectRowId
import woowacourse.shopping.common.data.database.table.SqlCart
import woowacourse.shopping.common.data.database.table.SqlProduct
import woowacourse.shopping.common.model.CartProductModel

class CartDao(private val db: SQLiteDatabase) {
    fun insertCartProduct(cartProductModel: CartProductModel) {
        db.use {
            val productRow: MutableMap<String, Any> = mutableMapOf()
            productRow[SqlProduct.PICTURE] = cartProductModel.product.picture
            productRow[SqlProduct.TITLE] = cartProductModel.product.title
            productRow[SqlProduct.PRICE] = cartProductModel.product.price

            val row = ContentValues()
            row.put(SqlCart.ORDINAL, cartProductModel.ordinal)
            row.put(SqlCart.PRODUCT_ID, SqlProduct.selectRowId(db, productRow))
            it.insert(SqlCart.name, null, row)
        }
    }

    fun deleteCartProductByOrdinal(ordinal: Int) {
        db.use {
            it.delete(SqlCart.name, "${SqlCart.ORDINAL} = ?", arrayOf(ordinal.toString()))
        }
    }
}
