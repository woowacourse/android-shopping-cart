package woowacourse.shopping.ui.productlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.ProductDummy
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        applyWindowInsets()

        setSupportActionBar(binding.toolbarProductList)

        val adapter = productListAdapter()
        binding.productsRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(CartActivity.newIntent(this))
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

    private fun productListAdapter(): ProductListAdapter {
        return ProductListAdapter(
            items = ProductDummy.products,
            object : ProductClickListener {
                override fun onClick(product: Product) {
                    val intent =
                        ProductDetailActivity.newIntent(this@ProductListActivity, product)
                    startActivity(intent)
                }
            },
        )
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ProductListActivity::class.java)
        }
    }
}
