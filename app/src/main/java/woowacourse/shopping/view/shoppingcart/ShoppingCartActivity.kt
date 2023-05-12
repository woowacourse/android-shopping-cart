package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.db.CartProductDBHelper
import woowacourse.shopping.data.db.CartProductDBRepository
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {
    override lateinit var presenter: ShoppingCartContract.Presenter
    private lateinit var adapter: ShoppingCartAdapter

    private var _binding: ActivityShoppingCartBinding? = null
    private val binding
        get()= _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)

        setPresenter()
        setAdapter()
        setViewSettings()
    }

    private fun setPresenter() {
        presenter = ShoppingCartPresenter(this)
    }

    private fun setAdapter() {
        val db = CartProductDBHelper(this).readableDatabase
        presenter.setRecentProducts(db)
        adapter = ShoppingCartAdapter(
            presenter.cartProducts,
            setOnClickRemove()
        )
    }

    private fun setViewSettings() {
        binding.rvCartList.adapter = adapter
    }

    private fun setOnClickRemove(): (ProductUIModel) -> Unit = { product ->
        val db = CartProductDBHelper(this).writableDatabase
        presenter.removeCartProduct(db, product)
    }

    override fun removeCartProduct(cartProducts: List<CartProductUIModel>, index: Int) {
        Log.d("mendel", cartProducts.joinToString { it.productUIModel.name })
        Log.d("mendel", index.toString())
        adapter.update(cartProducts)
        adapter.notifyItemRemoved(index)
    }

    companion object {
        fun intent(context: Context) = Intent(context, ShoppingCartActivity::class.java)
    }
}
