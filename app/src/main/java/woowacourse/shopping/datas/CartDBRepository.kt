package woowacourse.shopping.datas

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_COUNT
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_ID
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_IMAGE
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_NAME
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_PRICE
import woowacourse.shopping.datas.CartDBHelper.Companion.TABLE_NAME
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class CartDBRepository(private val database: SQLiteDatabase) : CartRepository {
    override fun getAll(): List<CartProductUIModel> {
        val products = mutableListOf<CartProductUIModel>()
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val cartProductUIModel =
                    CartProductUIModel(
                        count = it.getInt(it.getColumnIndexOrThrow(KEY_COUNT)),
                        ProductUIModel(
                            id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                            name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                            imageUrl = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                            price = it.getInt(it.getColumnIndexOrThrow(KEY_PRICE)),
                        )
                    )
                products.add(cartProductUIModel)
            }
        }
        return products
    }

    override fun insert(cartProduct: CartProductUIModel) {
        val record = ContentValues().apply {
            put(KEY_COUNT, 1)
            put(KEY_ID, cartProduct.product.id)
            put(KEY_NAME, cartProduct.product.name)
            put(KEY_IMAGE, cartProduct.product.imageUrl)
            put(KEY_PRICE, cartProduct.product.price)
        }
        database.insert(TABLE_NAME, null, record)
    }

    override fun remove(cartProduct: CartProductUIModel) {
        database.execSQL(
            "DELETE FROM $TABLE_NAME WHERE $KEY_ID = '${cartProduct.product.id}' "
        )
    }

    override fun clear() {
        database.execSQL("DELETE FROM $TABLE_NAME")
    }

    override fun isEmpty(): Boolean =
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use { it.count == 0 }

    override fun close() {
        database.close()
    }
}
