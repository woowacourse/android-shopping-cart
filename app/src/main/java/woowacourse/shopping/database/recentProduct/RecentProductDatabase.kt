package woowacourse.shopping.database.recentProduct

import android.content.ContentValues
import android.content.Context
import com.example.domain.model.Product
import com.example.domain.repository.RecentRepository
import woowacourse.shopping.database.recentProduct.RecentProductConstant.TABLE_COLUMN_ID
import woowacourse.shopping.database.recentProduct.RecentProductConstant.TABLE_COLUMN_IMAGE_URL
import woowacourse.shopping.database.recentProduct.RecentProductConstant.TABLE_COLUMN_NAME
import woowacourse.shopping.database.recentProduct.RecentProductConstant.TABLE_COLUMN_PRICE
import woowacourse.shopping.database.recentProduct.RecentProductConstant.TABLE_COLUMN_SAVE_TIME
import woowacourse.shopping.database.recentProduct.RecentProductConstant.TABLE_NAME

class RecentProductDatabase(context: Context) : RecentRepository {
    private val db = RecentProductDBHelper(context).writableDatabase

    override fun insert(product: Product) {
        findById(product.id)?.let {
            delete(it.id)
        }
        val values = ContentValues()
        values.put(TABLE_COLUMN_ID, product.id)
        values.put(TABLE_COLUMN_NAME, product.name)
        values.put(TABLE_COLUMN_PRICE, product.price)
        values.put(TABLE_COLUMN_IMAGE_URL, product.imageUrl)
        values.put(
            TABLE_COLUMN_SAVE_TIME,
            System.currentTimeMillis(),
        )
        db.insert(TABLE_NAME, null, values)
    }

    override fun getRecent(size: Int): List<Product> {
        val cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "$TABLE_COLUMN_SAVE_TIME DESC",
            "$size",
        )
        val products = mutableListOf<Product>()
        while (cursor.moveToNext()) {
            val id =
                cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ID))
            val name =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(TABLE_COLUMN_NAME),
                )
            val price =
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRICE),
                )
            val imageUrl =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(TABLE_COLUMN_IMAGE_URL),
                )
            products.add(Product(id, name, price, imageUrl))
        }
        cursor.close()
        return products
    }

    override fun findById(id: Long): Product? {
        val selection = "$TABLE_COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null,
            "1",
        )
        var product: Product? = null
        if (cursor.count > 0) {
            cursor.moveToFirst()
            val id =
                cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ID))
            val name =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(TABLE_COLUMN_NAME),
                )
            val price =
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRICE),
                )
            val imageUrl =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(TABLE_COLUMN_IMAGE_URL),
                )
            product = Product(id, name, price, imageUrl)
        }
        cursor.close()
        return product
    }

    override fun delete(id: Long) {
        val whereClause = "$TABLE_COLUMN_ID = ?"
        val whereArgs = arrayOf(id.toString())
        db.delete(
            TABLE_NAME,
            whereClause,
            whereArgs,
        )
    }
}
