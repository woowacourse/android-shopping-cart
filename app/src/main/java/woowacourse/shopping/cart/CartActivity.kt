package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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
        setToolBarBackButton()
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

    private fun setToolBarBackButton() {
        setSupportActionBar(binding.tbCart)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setOnClickRemove(): (ProductUIModel, Int) -> Unit = { productUIModle: ProductUIModel, position: Int ->
        val dbHelper = CartDBHelper(this)
        val db = dbHelper.writableDatabase
        val repository = ProductDBRepository(db)
        repository.remove(productUIModle)

        adapter.remove(productUIModle)
        adapter.notifyItemChanged(position)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun intent(context: Context) = Intent(context, CartActivity::class.java)
    }
}
