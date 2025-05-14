package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.product.CartDatabase
import woowacourse.shopping.data.product.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.ui.productlist.ProductListActivity
import woowacourse.shopping.ui.viewmodel.ProductViewModel
import woowacourse.shopping.utils.ViewModelFactory

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    val cartRepository: CartRepository by lazy { CartRepositoryImpl(CartDatabase.getInstance(this)) }

    private val viewModel: ProductViewModel by viewModels {
        ViewModelFactory.createProductViewModelFactory(
            CartRepositoryImpl(CartDatabase.getInstance(this)),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        applyWindowInsets()
        setSupportActionBar(binding.toolbarCart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        viewModel.products.observe(this) {
            binding.rvCart.adapter = CartAdapter(it, object : CartClickListener {
                override fun onClick(product: Product) {
                    cartRepository.remove(product)
                    viewModel.update()
                    binding.rvCart.adapter!!.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(ProductListActivity.newIntent(this))
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
