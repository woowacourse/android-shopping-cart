package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.ProductModel

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"
        presenter = CartPresenter(this, CartDbRepository(this))
        presenter.fetchProducts()

        binding.nextPage.setOnClickListener {
            presenter.showMoreProducts()
        }
    }

    override fun showProducts(cartProducts: List<ProductModel>) {
        binding.recyclerCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerCart.adapter = CartAdapter(cartProducts) {
            onRemoveClick(it)
        }
    }

    override fun notifyAddProducts(position: Int, size: Int) {
        binding.recyclerCart.adapter?.notifyItemRangeChanged(0, size)
//        binding.recyclerCart.adapter?.notifyItemRangeInserted(position, size)
    }

    private fun onRemoveClick(id: Int) {
        presenter.removeProduct(id)
    }

    override fun notifyRemoveItem(position: Int) {
        binding.recyclerCart.adapter?.notifyItemRemoved(position)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
