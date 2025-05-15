package woowacourse.shopping.view.shoppingcart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding

class ShoppingCartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShoppingCartBinding.inflate(layoutInflater) }
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel =
            ViewModelProvider(
                this,
                ShoppingCartViewModel.provideFactory(applicationContext),
            )[ShoppingCartViewModel::class.java]

        val adapter =
            SelectedProductAdapter { product ->
                viewModel.deleteProduct(product)
            }
        binding.rvProducts.adapter = adapter
        binding.eventListener =
            object : OnClickArrowListener {
                override fun onClickLeftPage() {
                    // TODO
                }

                override fun onClickRightPage() {
                    viewModel.loadMoreShoppingProducts()
                }
            }

        viewModel.removedProduct.observe(this) { value ->
            adapter.removeItem(value)
        }

        viewModel.shoppingProduct.observe(this) { value ->
            adapter.updateItems(value.items, value.hasNext)
            if (!value.hasNext) binding.btnRight.isEnabled = false
        }
        binding.rvProducts.adapter = adapter
    }
}
