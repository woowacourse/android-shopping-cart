package woowacourse.shopping.datas

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.datas.ProductDBHelper.Companion.KEY_IMAGE
import woowacourse.shopping.datas.ProductDBHelper.Companion.KEY_NAME
import woowacourse.shopping.datas.ProductDBHelper.Companion.KEY_PRICE
import woowacourse.shopping.datas.ProductDBHelper.Companion.TABLE_NAME
import woowacourse.shopping.uimodel.ProductUIModel

class ProductDBRepository(private val database: SQLiteDatabase) {
    fun getAll(): List<ProductUIModel> {
        val products = mutableListOf<ProductUIModel>()
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val productUIModel = ProductUIModel(
                    name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                    url = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                    price = it.getInt(it.getColumnIndexOrThrow(KEY_PRICE)),
                )
                products.add(productUIModel)
            }
        }
        return products
    }

    fun insert(productUIModel: ProductUIModel) {
        val record = ContentValues().apply {
            put(KEY_NAME, productUIModel.name)
            put(KEY_IMAGE, productUIModel.url)
            put(KEY_PRICE, productUIModel.price)
        }
        database.insert(TABLE_NAME, null, record)
    }

    fun remove(productUIModel: ProductUIModel) {
        database.execSQL(
            "DELETE FROM $TABLE_NAME " +
                "WHERE $KEY_NAME = '${productUIModel.name}' " +
                "AND $KEY_PRICE = '${productUIModel.price}'" +
                "AND $KEY_IMAGE = '${productUIModel.url}'"
        )
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
