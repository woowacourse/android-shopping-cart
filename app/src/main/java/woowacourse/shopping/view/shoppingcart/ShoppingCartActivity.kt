package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.db.CartDBHelper
import woowacourse.shopping.data.db.ProductDBRepository
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.uimodel.CartProductsUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var adapter: ShoppingCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)

        val dbHelper = CartDBHelper(this)
        val db = dbHelper.readableDatabase
        val repository = ProductDBRepository(db)
        val cartProducts = repository.getAll(CartDBHelper.TABLE_NAME)
        adapter = ShoppingCartAdapter(
            CartProductsUIModel(cartProducts),
            setOnClickRemove()
        )

        binding.rvCartList.adapter = adapter
    }

    fun setOnClickRemove(): (ProductUIModel) -> Unit = {
        adapter.remove(it)
        val dbHelper = CartDBHelper(this)
        val db = dbHelper.writableDatabase
        val repository = ProductDBRepository(db)
        repository.remove(CartDBHelper.TABLE_NAME, it)
    }

    companion object {
        fun intent(context: Context) = Intent(context, ShoppingCartActivity::class.java)
    }
}
