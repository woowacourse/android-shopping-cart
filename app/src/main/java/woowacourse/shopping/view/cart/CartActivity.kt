package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.db.CartDBHelper
import woowacourse.shopping.data.db.ProductDBRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.uimodel.CartUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        val dbHelper = CartDBHelper(this)
        val db = dbHelper.readableDatabase
        val repository = ProductDBRepository(db)
        val cartProducts = repository.getAll(CartDBHelper.TABLE_NAME)
        adapter = CartRecyclerViewAdapter(
            CartUIModel(cartProducts),
            setOnClickRemove()
        )

        binding.rvCartList.adapter = adapter
    }

    fun setOnClickRemove(): (ProductUIModel) -> Unit = {
        val dbHelper = CartDBHelper(this)
        val db = dbHelper.writableDatabase
        val repository = ProductDBRepository(db)
        repository.remove(CartDBHelper.TABLE_NAME, it)
        adapter.remove(it)
    }

    companion object {
        fun intent(context: Context) = Intent(context, CartActivity::class.java)
    }
}
