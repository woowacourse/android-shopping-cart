package woowacourse.shopping.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import model.CartProduct
import model.Name
import model.Price
import model.Product
import woowacourse.shopping.database.product.MockProduct
import woowacourse.shopping.database.product.ProductDBContract
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.database.recentviewed.RecentViewedDBContract
import woowacourse.shopping.database.shoppingcart.ShoppingCartDBContract

class ShoppingDBRepository(
    shoppingDao: ShoppingDao,
) : ShoppingRepository {

    private val shoppingDB: SQLiteDatabase = shoppingDao.writableDatabase

    override fun selectProducts(from: Int, count: Int): List<Product> {
        val products = mutableListOf<Product>()
        val query = "SELECT * FROM ${ProductDBContract.TABLE_NAME} LIMIT %s OFFSET %s".format(
            count,
            from,
        )
        val cursor = shoppingDB.rawQuery(query, null)

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    products.add(cursor.getProduct())
                } while (moveToNext())
            }
        }
        cursor?.close()

        return products
    }

    override fun selectAllShoppingCartProducts(): List<CartProduct> {
        val shoppingCartProducts = mutableListOf<CartProduct>()
        val cursor = shoppingDB.rawQuery(
            "SELECT * FROM ${ShoppingCartDBContract.TABLE_NAME}",
            null,
        ).apply {
            if (moveToFirst()) {
                do {
                    shoppingCartProducts.add(getShoppingCartProductById())
                } while (moveToNext())
            }
        }
        cursor.close()

        return shoppingCartProducts.toList()
    }

    override fun selectShoppingCartProducts(from: Int, count: Int): List<CartProduct> {
        val shoppingCartProducts = mutableListOf<CartProduct>()
        val query = "SELECT * FROM ${ShoppingCartDBContract.TABLE_NAME} LIMIT %s OFFSET %s".format(
            count,
            from,
        )
        val cursor = shoppingDB.rawQuery(query, null)

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    shoppingCartProducts.add(cursor.getShoppingCartProductById())
                } while (moveToNext())
            }
        }
        cursor?.close()

        return shoppingCartProducts.toList()
    }

    override fun getShoppingCartProductsSize(): Int {
        val cursor = shoppingDB.rawQuery(
            "SELECT * FROM ${ShoppingCartDBContract.TABLE_NAME}",
            null,
        )
        val count = cursor.count
        cursor.close()
        return count
    }

    override fun getSelectedShoppingCartProducts(): List<CartProduct> {
        val shoppingCartProducts = mutableListOf<CartProduct>()
        val cursor = shoppingDB.rawQuery(
            "SELECT * FROM ${ShoppingCartDBContract.TABLE_NAME} where ${ShoppingCartDBContract.IS_SELECTED} = 1",
            null,
        ).apply {
            if (moveToFirst()) {
                do {
                    shoppingCartProducts.add(getShoppingCartProductById())
                } while (moveToNext())
            }
        }
        cursor.close()

        return shoppingCartProducts.toList()
    }

    private fun Cursor.getShoppingCartProductById(): CartProduct {
        val id = getInt(getColumnIndexOrThrow(ShoppingCartDBContract.CART_PRODUCT_ID))
        val product = selectProductById(id)
        val count = getInt(getColumnIndexOrThrow(ShoppingCartDBContract.CART_PRODUCT_COUNT))
        val isSelected = getInt(getColumnIndexOrThrow(ShoppingCartDBContract.IS_SELECTED)) != 0
        return CartProduct(product, count, isSelected)
    }

    private fun Cursor.getProduct(): Product {
        val id = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_ID))
        val img = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_IMG))
        val name = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_NAME))
        val price = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_PRICE))
        return Product(id, Name(name), img, Price(price))
    }

    override fun selectProductById(id: Int): Product {
        return MockProduct.products.find { it.id == id }
            ?: Product(-1, Name(""), "", Price(0))
    }

    override fun selectShoppingCartProductById(id: Int): CartProduct {
        val cursor = shoppingDB.rawQuery(
            "select * from ${ShoppingCartDBContract.TABLE_NAME} where ${ShoppingCartDBContract.CART_PRODUCT_ID} = ?",
            arrayOf(id.toString()),
        ).apply {
            moveToNext()
        }

        val product = cursor.getShoppingCartProductById()
        cursor.close()

        return product
    }

    override fun insertToShoppingCart(id: Int, count: Int, isSelected: Boolean) {
        val values = ContentValues().apply {
            put(ShoppingCartDBContract.CART_PRODUCT_ID, id)
            put(ShoppingCartDBContract.CART_PRODUCT_COUNT, count)
            put(ShoppingCartDBContract.IS_SELECTED, isSelected)
        }

        shoppingDB.insert(ShoppingCartDBContract.TABLE_NAME, null, values)
    }

    override fun deleteFromShoppingCart(id: Int) {
        shoppingDB.delete(
            ShoppingCartDBContract.TABLE_NAME,
            "${ShoppingCartDBContract.CART_PRODUCT_ID} = ?",
            arrayOf(id.toString()),
        )
    }

    override fun updateShoppingCartCount(id: Int, count: Int) {
        val values = ContentValues()
        values.put(ShoppingCartDBContract.CART_PRODUCT_COUNT, count)

        shoppingDB.update(
            ShoppingCartDBContract.TABLE_NAME,
            values,
            "${ShoppingCartDBContract.CART_PRODUCT_ID} = ?",
            arrayOf(id.toString()),
        )
    }

    override fun updateShoppingCartSelection(id: Int, isSelected: Boolean) {
        val values = ContentValues()
        values.put(ShoppingCartDBContract.IS_SELECTED, isSelected)

        shoppingDB.update(
            ShoppingCartDBContract.TABLE_NAME,
            values,
            "${ShoppingCartDBContract.CART_PRODUCT_ID} = ?",
            arrayOf(id.toString()),
        )
    }

    override fun insertToRecentViewedProducts(id: Int) {
        val recentViewedId = mutableListOf<Int>()
        val query = "SELECT * FROM ${RecentViewedDBContract.TABLE_NAME}"
        val cursor = shoppingDB.rawQuery(query, null)

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    recentViewedId.add(
                        getInt(
                            getColumnIndexOrThrow(RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID),
                        ),
                    )
                } while (moveToNext())
            }
        }
        cursor?.close()

        if (recentViewedId.contains(id)) {
            deleteFromRecentViewedProducts(id)
        }

        val values = ContentValues().apply {
            put(RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID, id)
        }
        shoppingDB.insert(RecentViewedDBContract.TABLE_NAME, null, values)
    }

    override fun selectRecentViewedProducts(): List<Product> {
        val recentViewedProducts = mutableListOf<Product>()

        val recentViewedCursor = shoppingDB.query(
            RecentViewedDBContract.TABLE_NAME,
            arrayOf(
                RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID,
            ),
            null,
            null,
            null,
            null,
            null,
        )

        with(recentViewedCursor) {
            while (moveToNext()) {
                val id =
                    getInt(getColumnIndexOrThrow(RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID))
                val product = selectProductById(id)

                recentViewedProducts.add(product)
            }
            close()
        }
        return recentViewedProducts.toList().reversed()
    }

    override fun deleteFromRecentViewedProducts(id: Int) {
        shoppingDB.delete(
            RecentViewedDBContract.TABLE_NAME,
            "${RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID} = ?",
            arrayOf(id.toString()),
        )
    }
}
