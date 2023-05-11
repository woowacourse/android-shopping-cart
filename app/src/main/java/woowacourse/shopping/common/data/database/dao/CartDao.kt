package woowacourse.shopping.common.data.database.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.common.data.database.selectRowId
import woowacourse.shopping.common.data.database.table.SqlCart
import woowacourse.shopping.common.data.database.table.SqlProduct
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL

class CartDao(private val db: SQLiteDatabase) {
    fun insertCartProduct(cartProductModel: CartProductModel) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = cartProductModel.product.picture
        productRow[SqlProduct.TITLE] = cartProductModel.product.title
        productRow[SqlProduct.PRICE] = cartProductModel.product.price

        val row = ContentValues()
        row.put(SqlCart.ORDINAL, cartProductModel.ordinal)
        row.put(SqlCart.PRODUCT_ID, SqlProduct.selectRowId(db, productRow))
        db.insert(SqlCart.name, null, row)
    }

    fun selectAll(): Cart {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCart.name}, ${SqlProduct.name} on ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID}",
            null
        )
        return Cart(
            cursor.use {
                val cart = mutableListOf<CartProduct>()
                while (it.moveToNext()) cart.add(
                    CartProduct(
                        it.getInt(it.getColumnIndexOrThrow(SqlCart.ORDINAL)),
                        Product(
                            URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
                            it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
                            it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
                        )
                    )
                )
                cart
            }
        )
    }

    fun selectAllCount(): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM ${SqlCart.name}",
            null
        )
        return cursor.use {
            it.moveToNext()
            it.getInt(0)
        }
    }

    fun selectPage(page: Int, sizePerPage: Int): Cart {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCart.name}, ${SqlProduct.name} on ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} LIMIT ${page * sizePerPage}, $sizePerPage",
            null
        )
        return Cart(
            cursor.use {
                val cart = mutableListOf<CartProduct>()
                while (it.moveToNext()) cart.add(
                    CartProduct(
                        it.getInt(it.getColumnIndexOrThrow(SqlCart.ORDINAL)),
                        Product(
                            URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
                            it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
                            it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
                        )
                    )
                )
                cart
            }
        )
    }

    fun deleteCartProductByOrdinal(ordinal: Int) {
        db.delete(SqlCart.name, "${SqlCart.ORDINAL} = ?", arrayOf(ordinal.toString()))
    }
}
