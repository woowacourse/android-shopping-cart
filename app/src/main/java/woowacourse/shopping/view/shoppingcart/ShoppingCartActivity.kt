package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.db.CartProductDBHelper
import woowacourse.shopping.data.db.CartProductDBRepository
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var adapter: ShoppingCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)

        val dbHelper = CartProductDBHelper(this)
        val db = dbHelper.readableDatabase
        val repository = CartProductDBRepository(db)
        val cartProducts = repository.getAll()
        adapter = ShoppingCartAdapter(
            cartProducts,
            setOnClickRemove()
        )

        binding.rvCartList.adapter = adapter
    }

    fun setOnClickRemove(): (ProductUIModel) -> Unit = {
        adapter.remove(it)
        val dbHelper = CartProductDBHelper(this)
        val db = dbHelper.writableDatabase
        val repository = CartProductDBRepository(db)
        repository.remove(CartProductUIModel(it))
    }

    companion object {
        fun intent(context: Context) = Intent(context, ShoppingCartActivity::class.java)
    }
}
