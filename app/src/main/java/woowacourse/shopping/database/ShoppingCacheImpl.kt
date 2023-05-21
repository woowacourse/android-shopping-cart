package woowacourse.shopping.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import model.CartProduct
import model.Count
import model.Name
import model.Price
import model.Product
import model.RecentViewedProduct
import woowacourse.shopping.database.product.MockProduct
import woowacourse.shopping.database.product.ProductDBContract
import woowacourse.shopping.database.product.RecentViewedDBContract
import woowacourse.shopping.database.product.ShoppingCartDBContract
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.model.ProductUiModel

class ShoppingCacheImpl(
    shoppingDao: ShoppingDao,
) : ShoppingCache {

    private val shoppingDB: SQLiteDatabase = shoppingDao.writableDatabase

    private fun addProduct(product: ProductUiModel) {
        val values = ContentValues().apply {
            put(ProductDBContract.PRODUCT_ID, product.id)
            put(ProductDBContract.PRODUCT_IMG, product.imageUrl)
            put(ProductDBContract.PRODUCT_NAME, product.name)
            put(ProductDBContract.PRODUCT_PRICE, product.price)
        }

        shoppingDB.insert(ProductDBContract.TABLE_NAME, null, values)
    }

    override fun selectProducts(from: Int, count: Int): List<Product> {
        val products = mutableListOf<Product>()
        val query = "SELECT * FROM ${ProductDBContract.TABLE_NAME} LIMIT %s OFFSET %s".format(
            count, from
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

    override fun selectShoppingCartProducts(): List<CartProduct> {
        val cartProducts = mutableListOf<CartProduct>()
        val query = "SELECT * FROM ${ShoppingCartDBContract.TABLE_NAME}"
        val cursor = shoppingDB.rawQuery(query, null)

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    cartProducts.add(cursor.getShoppingCartProduct())
                } while (moveToNext())
            }
        }
        cursor?.close()

        return cartProducts.toList()
    }

    override fun selectShoppingCartProductById(id: Int): CartProduct {
        val cursor = shoppingDB.rawQuery(
            "select * from ${ShoppingCartDBContract.TABLE_NAME} where ${ShoppingCartDBContract.CART_PRODUCT_ID} = ?",
            arrayOf(id.toString())
        ).apply {
            moveToNext()
        }
        val shoppingCartProduct = cursor.getShoppingCartProduct()

        cursor.close()

        return shoppingCartProduct
    }

    private fun Cursor.getShoppingCartProduct(): CartProduct {
        val id = getInt(getColumnIndexOrThrow(ShoppingCartDBContract.CART_PRODUCT_ID))
        val count = getInt(getColumnIndexOrThrow(ShoppingCartDBContract.COUNT))
        return CartProduct(
            product = selectProductById(id),
            count = Count(count)
        )
    }

    private fun Cursor.getProduct(): Product {
        val id = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_ID))
        val img = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_IMG))
        val name = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_NAME))
        val price = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_PRICE))
        return Product(id, Name(name), img, Price(price))
    }

    private fun Cursor.getRecentViewedProduct(): RecentViewedProduct {
        val id = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_ID))
        val img = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_IMG))
        val name = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_NAME))

        return RecentViewedProduct(id, Name(name), img)
    }

    override fun selectProductById(id: Int): Product {
        val cursor = shoppingDB.rawQuery(
            "select * from ${ProductDBContract.TABLE_NAME} where ${ProductDBContract.PRODUCT_ID} = ?",
            arrayOf(id.toString())
        ).apply {
            moveToNext()
        }

        val product = cursor.getProduct()
        cursor.close()

        return product
    }

    override fun insertToShoppingCart(id: Int, count: Int) {
        val values = ContentValues().apply {
            put(ShoppingCartDBContract.CART_PRODUCT_ID, id)
            put(ShoppingCartDBContract.COUNT, count)
        }
        // todo : 함수 분리하기
        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = shoppingDB.query(
            ShoppingCartDBContract.TABLE_NAME, arrayOf("id"),
            selection, selectionArgs,
            null, null, null
        )

        if (cursor.moveToFirst()) {
            shoppingDB.update(
                ShoppingCartDBContract.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        } else {
            shoppingDB.insert(ShoppingCartDBContract.TABLE_NAME, null, values)
        }
        cursor.close()
    }

    override fun getCountOfShoppingCartProducts(): Int {
        var count = 0
        val cursor =
            shoppingDB.rawQuery("SELECT COUNT(*) FROM ${ShoppingCartDBContract.TABLE_NAME}", null)
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()

        return count
    }

    override fun deleteFromShoppingCart(id: Int) {
        shoppingDB.delete(
            ShoppingCartDBContract.TABLE_NAME,
            "${ShoppingCartDBContract.CART_PRODUCT_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun insertToRecentViewedProducts(id: Int) {
        val values = ContentValues().apply {
            put(RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID, id)
        }
        shoppingDB.delete(
            RecentViewedDBContract.TABLE_NAME,
            "${RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID} = ?",
            arrayOf(id.toString())
        )
        shoppingDB.insert(RecentViewedDBContract.TABLE_NAME, null, values)
    }

    override fun selectRecentViewedProducts(): List<RecentViewedProduct> {
        val recentViewedCursor = shoppingDB.query(
            RecentViewedDBContract.TABLE_NAME,
            arrayOf(
                RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID
            ),
            null, null, null, null, null
        )
        val recentViewedProducts = mutableListOf<RecentViewedProduct>()

        with(recentViewedCursor) {
            while (moveToNext()) {
                val id =
                    getInt(getColumnIndexOrThrow(RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID))
                val product = selectRecentViewedProductById(id)

                recentViewedProducts.add(product)
            }
        }
        recentViewedCursor.close()

        return recentViewedProducts.toList().reversed()
    }

    override fun selectRecentViewedProductById(id: Int): RecentViewedProduct {
        val cursor = shoppingDB.rawQuery(
            "select * from ${ProductDBContract.TABLE_NAME} where ${ProductDBContract.PRODUCT_ID} = ?",
            arrayOf(id.toString())
        ).apply {
            moveToNext()
        }

        val product = cursor.getRecentViewedProduct()
        cursor.close()

        return product
    }

    override fun deleteFromRecentViewedProducts() {
        shoppingDB.delete(
            RecentViewedDBContract.TABLE_NAME,
            "ROWID = (SELECT MIN(ROWID) FROM ${RecentViewedDBContract.TABLE_NAME})",
            null
        )
    }

    // TODO: 이상한 부분
    override fun selectLatestViewedProduct(): Product? {
        val products = selectRecentViewedProducts()

        if (products.isEmpty()) {
            return null
        }
        return selectProductById(
            id = products.first().id
        )
    }

    override fun setUpDB() {
        MockProduct.products.forEach {
            addProduct(it)
        }
    }
}
