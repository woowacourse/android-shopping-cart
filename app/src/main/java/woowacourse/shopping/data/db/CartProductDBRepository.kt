package woowacourse.shopping.data.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.db.CartProductDBHelper.Companion.KEY_ID
import woowacourse.shopping.data.db.CartProductDBHelper.Companion.KEY_IMAGE
import woowacourse.shopping.data.db.CartProductDBHelper.Companion.KEY_NAME
import woowacourse.shopping.data.db.CartProductDBHelper.Companion.KEY_PRICE
import woowacourse.shopping.data.db.CartProductDBHelper.Companion.TABLE_NAME
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class CartProductDBRepository(private val database: SQLiteDatabase) {
    fun getAll(): List<CartProductUIModel> {
        val cartProducts = mutableListOf<CartProductUIModel>()
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val cartProductUIModel = CartProductUIModel(
                    ProductUIModel(
                        id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                        name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                        url = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                        price = it.getInt(it.getColumnIndexOrThrow(KEY_PRICE)),
                    )
                )
                cartProducts.add(cartProductUIModel)
            }
        }
        return cartProducts
    }

    fun insert(cartProductUIModel: CartProductUIModel) {
        val record = ContentValues().apply {
            put(KEY_ID, cartProductUIModel.productUIModel.id)
            put(KEY_NAME, cartProductUIModel.productUIModel.name)
            put(KEY_IMAGE, cartProductUIModel.productUIModel.url)
            put(KEY_PRICE, cartProductUIModel.productUIModel.price)
        }
        database.insert(TABLE_NAME, null, record)
    }

    fun remove(cartProductUIModel: CartProductUIModel) {
        database.execSQL("DELETE FROM ${RecentProductDBHelper.TABLE_NAME} WHERE ${RecentProductDBHelper.KEY_ID}='${cartProductUIModel.productUIModel.id}';")
    }

    fun clear() {
        database.execSQL("DELETE FROM $TABLE_NAME")
    }

    fun isEmpty(): Boolean =
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use { it.count == 0 }

    fun close() {
        database.close()
    }
}
