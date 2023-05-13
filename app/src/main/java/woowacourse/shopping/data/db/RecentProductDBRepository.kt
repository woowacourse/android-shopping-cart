package woowacourse.shopping.data.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import woowacourse.shopping.data.db.RecentProductDBHelper.Companion.KEY_ID
import woowacourse.shopping.data.db.RecentProductDBHelper.Companion.KEY_IMAGE
import woowacourse.shopping.data.db.RecentProductDBHelper.Companion.KEY_NAME
import woowacourse.shopping.data.db.RecentProductDBHelper.Companion.KEY_PRICE
import woowacourse.shopping.data.db.RecentProductDBHelper.Companion.TABLE_NAME
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductDBRepository(private val database: SQLiteDatabase) {
    fun getAll(): List<RecentProductUIModel> {
        val products = mutableListOf<RecentProductUIModel>()
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val recentProductUIModel = RecentProductUIModel(
                    ProductUIModel(
                        id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                        name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                        url = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                        price = it.getInt(it.getColumnIndexOrThrow(KEY_PRICE)),
                    )
                )
                products.add(recentProductUIModel)
            }
        }

        if (products.isEmpty()) {
            return emptyList()
        }
        return products.reversed().subList(0, minOf(products.size, 10))
    }

    fun insert(recentProductUIModel: RecentProductUIModel) {
        if (exist(recentProductUIModel)) {
            Log.d("bboddo", "entered")
            remove(recentProductUIModel)
        }

        val record = ContentValues().apply {
            put(KEY_ID, recentProductUIModel.productUIModel.id)
            put(KEY_NAME, recentProductUIModel.productUIModel.name)
            put(KEY_IMAGE, recentProductUIModel.productUIModel.url)
            put(KEY_PRICE, recentProductUIModel.productUIModel.price)
        }
        database.insert(TABLE_NAME, null, record)
    }

    fun exist(recentProductUIModel: RecentProductUIModel): Boolean {
        val arrayToFind = arrayOf(recentProductUIModel.productUIModel.id.toString())
        val cursor = database.query(
            TABLE_NAME,
            arrayOf("COUNT(*)"),
            "$KEY_ID=?",
            arrayToFind,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count >= 1
    }

    fun remove(recentProductUIModel: RecentProductUIModel) {
        database.execSQL("DELETE FROM $TABLE_NAME WHERE $KEY_ID='${recentProductUIModel.productUIModel.id}';")
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
