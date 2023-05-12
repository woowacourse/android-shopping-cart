package woowacourse.shopping.view.shoppingcart

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.db.CartProductDBRepository
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View
) : ShoppingCartContract.Presenter {
    override lateinit var cartProducts: List<CartProductUIModel>

    override fun setRecentProducts(db: SQLiteDatabase) {
        val repository = CartProductDBRepository(db)
        cartProducts = repository.getAll()
        repository.close()
    }

    override fun removeCartProduct(db: SQLiteDatabase, productUIModel: ProductUIModel) {
        val repository = CartProductDBRepository(db)
        repository.remove(CartProductUIModel(productUIModel))

        val index = getIndex(productUIModel)
        cartProducts = cartProducts - cartProducts[index]
        view.removeCartProduct(cartProducts, index)
    }

    private fun getIndex(product: ProductUIModel): Int {
        return cartProducts.indices.find { index ->
            cartProducts[index].productUIModel.id == product.id
        } ?: throw IllegalStateException("해당 값을 찾을 수 없습니다.")
    }
}
