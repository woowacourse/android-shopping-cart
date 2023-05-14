package woowacourse.shopping.data.database.dao.basket

import android.annotation.SuppressLint
import android.content.ContentValues
import android.provider.BaseColumns
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.contract.BasketContract
import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct

class BasketDaoImpl(private val database: ShoppingDatabase) : BasketDao {
    @SuppressLint("Range")
    override fun getPartially(page: DataPageNumber): List<DataProduct> {
        val products = mutableListOf<DataProduct>()

        database.writableDatabase.use { db ->
            val cursor =
                db.rawQuery(
                    GET_PARTIALLY_QUERY,
                    arrayOf(page.startId.toString(), page.pageSizeForCheckHasNext.toString())
                )
            while (cursor.moveToNext()) {
                val id: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val name: String =
                    cursor.getString(cursor.getColumnIndex(BasketContract.COLUMN_NAME))
                val price: DataPrice =
                    DataPrice(cursor.getInt(cursor.getColumnIndex(BasketContract.COLUMN_PRICE)))
                val imageUrl: String =
                    cursor.getString(cursor.getColumnIndex(BasketContract.COLUMN_IMAGE_URL))
                products.add(DataProduct(id, name, price, imageUrl))
            }
            cursor.close()
        }
        return products
    }

    override fun add(product: DataProduct) {
        val contentValues = ContentValues().apply {
            put(BasketContract.COLUMN_NAME, product.name)
            put(BasketContract.COLUMN_PRICE, product.price.value)
            put(BasketContract.COLUMN_IMAGE_URL, product.imageUrl)
            put(BasketContract.COLUMN_CREATED, System.currentTimeMillis())
        }

        database.writableDatabase.use { db ->
            db.insert(BasketContract.TABLE_NAME, null, contentValues)
        }
    }

    override fun remove(product: DataProduct) {
        database.writableDatabase.use { db ->
            db.delete(
                BasketContract.TABLE_NAME,
                "${BaseColumns._ID} = ?",
                arrayOf(product.id.toString())
            )
        }
    }

    companion object {
        private val GET_PARTIALLY_QUERY = """
            SELECT * FROM ${BasketContract.TABLE_NAME}
            WHERE ${BaseColumns._ID} > ?
            ORDER BY ${BasketContract.COLUMN_CREATED} LIMIT ?        
        """.trimIndent()
    }
}
