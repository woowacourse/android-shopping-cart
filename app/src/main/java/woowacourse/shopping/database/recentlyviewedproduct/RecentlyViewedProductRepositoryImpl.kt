package woowacourse.shopping.database.recentlyviewedproduct

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import woowacourse.shopping.database.ProductContract.RecentlyViewedProductEntry
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import java.time.LocalDateTime

class RecentlyViewedProductRepositoryImpl(
    private val db: SQLiteDatabase,
    private val productRepository: ProductRepository,
) : RecentlyViewedProductRepository {

    override fun findAll(): List<Product> {
        val products = mutableListOf<Product>()
        val cursor: Cursor = db.rawQuery(
            "Select * from ${RecentlyViewedProductEntry.TABLE_NAME}",
            null,
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            productRepository.findById(id)?.let { products.add(it) }
        }

        cursor.close()
        return products
    }

    override fun save(product: Product) {
        deleteProductIfExists(product)

        val value = ContentValues().apply {
            put(RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID, product.id)
        }
        db.insert(RecentlyViewedProductEntry.TABLE_NAME, null, value)
    }

    private fun deleteProductIfExists(product: Product) {
        val selection = "${RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf(product.id.toString())
        db.delete(RecentlyViewedProductEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun save(recentlyViewedProduct: RecentlyViewedProduct) {
        deleteRecentlyViewedProductIfExists(recentlyViewedProduct)

        val value = ContentValues().apply {
            put(
                RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID,
                recentlyViewedProduct.product.id
            )
            put(
                RecentlyViewedProductEntry.COLUMN_NAME_VIEWED_TIME,
                recentlyViewedProduct.viewedTime.toString()
            )
        }
        val id = db.insert(RecentlyViewedProductEntry.TABLE_NAME, null, value)
        recentlyViewedProduct.id = id
    }

    private fun deleteRecentlyViewedProductIfExists(recentlyViewedProduct: RecentlyViewedProduct) {
        val selection = "${RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf(recentlyViewedProduct.product.id.toString())
        db.delete(RecentlyViewedProductEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun findFirst10OrderByViewedTimeDesc(): List<RecentlyViewedProduct> {
        val recentlyViewedProducts = mutableListOf<RecentlyViewedProduct>()
        val limit = 10
        val cursor = db.query(
            RecentlyViewedProductEntry.TABLE_NAME,
            arrayOf("*"),
            null,
            null,
            null,
            null,
            RecentlyViewedProductEntry.COLUMN_NAME_VIEWED_TIME,
            limit.toString()
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val productId =
                cursor.getLong(cursor.getColumnIndexOrThrow(RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID))
            val viewedTime =
                cursor.getString(cursor.getColumnIndexOrThrow(RecentlyViewedProductEntry.COLUMN_NAME_VIEWED_TIME))
            val recentlyViewedProduct = RecentlyViewedProduct(
                productRepository.findById(productId)
                    ?: throw IllegalArgumentException("참조 무결성 제약조건 위반"),
                LocalDateTime.parse(viewedTime)
            ).apply { this.id = id }
            recentlyViewedProducts.add(recentlyViewedProduct)
        }

        cursor.close()
        return recentlyViewedProducts
    }
}
