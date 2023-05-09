package woowacourse.shopping.database.cart

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.database.ProductContract
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartRepository

class CartRepositoryImpl(
    context: Context,
) : CartRepository {
    private val db = CartDbHelper(context).writableDatabase

    override fun findAll(): List<Product> {
        val products = mutableListOf<Product>()
        val cursor: Cursor = db.rawQuery(
            "Select * from ${ProductContract.CartEntry.TABLE_NAME} " +
                "JOIN ${ProductContract.ProductEntry.TABLE_NAME} " +
                "ON ${ProductContract.CartEntry.COLUMN_NAME_PRODUCT_ID} = ${BaseColumns._ID}",
            null,
        )

        while (cursor.moveToNext()) {
            Product(
                imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_IMAGE_URL)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_NAME)),
                price = cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRICE)),
            ).apply { id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID)) }
                .also { products.add(it) }
        }

        cursor.close()
        return products
    }

    override fun findAll(limit: Int, offset: Int): List<Product> {
        val products = mutableListOf<Product>()
        val cursor: Cursor = db.rawQuery(
            "Select * from ${ProductContract.CartEntry.TABLE_NAME} " +
                "JOIN ${ProductContract.ProductEntry.TABLE_NAME} " +
                "ON ${ProductContract.CartEntry.COLUMN_NAME_PRODUCT_ID} = ${BaseColumns._ID} " +
                "limit $limit offset $offset",
            null,
        )

        while (cursor.moveToNext()) {
            Product(
                imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_IMAGE_URL)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_NAME)),
                price = cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRICE)),
            ).apply { id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID)) }
                .also { products.add(it) }
        }

        cursor.close()
        return products
    }

    override fun save(product: Product) {
        val value = ContentValues().apply {
            put(ProductContract.CartEntry.COLUMN_NAME_PRODUCT_ID, product.id)
        }

        db.insert(ProductContract.CartEntry.TABLE_NAME, null, value)
    }

    override fun deleteById(productId: Long) {
        val selection = "${ProductContract.CartEntry.COLUMN_NAME_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf(productId.toString())
        db.delete(ProductContract.CartEntry.TABLE_NAME, selection, selectionArgs)
    }
}
