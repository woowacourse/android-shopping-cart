package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.CartDBHelper
import woowacourse.shopping.CartRecyclerViewAdapter
import woowacourse.shopping.CartUIModel
import woowacourse.shopping.ProductDBRepository
import woowacourse.shopping.ProductUIModel
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        val dbHelper = CartDBHelper(this)
        val db = dbHelper.readableDatabase
        val repository = ProductDBRepository(db)
        val cartProducts = repository.getAll()
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
        repository.remove(it)

        adapter.remove(it)
    }

    companion object {
        fun intent(context: Context) = Intent(context, CartActivity::class.java)
    }
}
