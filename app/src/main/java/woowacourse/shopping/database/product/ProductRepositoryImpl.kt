package woowacourse.shopping.database.product

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.database.ProductContract
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.ProductRepository

class ProductRepositoryImpl(
    context: Context,
) : ProductRepository {
    private val db = ProductDbHelper(context).readableDatabase

    override fun findAll(): List<Product> {
        val products = mutableListOf<Product>()
        val cursor: Cursor = db.rawQuery(
            "Select * from ${ProductContract.ProductEntry.TABLE_NAME}",
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
            "Select * from ${ProductContract.ProductEntry.TABLE_NAME} limit $limit offset $offset",
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

    override fun findById(id: Long): Product? {
        val cursor: Cursor = db.rawQuery(
            "Select * from ${ProductContract.ProductEntry.TABLE_NAME} where ${BaseColumns._ID} = $id",
            null,
        )

        val product = if (cursor.moveToNext()) {
            Product(
                imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_IMAGE_URL)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_NAME)),
                price = cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRICE)),
            ).apply { this.id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID)) }
        } else {
            null
        }

        cursor.close()
        return product
    }
}
