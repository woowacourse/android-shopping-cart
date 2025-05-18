package woowacourse.shopping.view.cart

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.lifecycleOwner = this
        adapter =
            CartAdapter(onProductRemoveClickListener = { product -> viewModel.removeToCart(product) })
        binding.viewModel = viewModel

        viewModel.backArrowButton.observe(this) {
            finish()
        }
        viewModel.loadedItems.observe(this) {
            Log.d("TAG", "loadedItems: $it")
            adapter.updateProductsView(it)
        }
        viewModel.productsInCart.observe(this) {
            Log.d("TAG", "productsInCart: $it")

            adapter.updateProductsView(it)
        }

        binding.btnPreviousPage.setOnClickListener {
            viewModel.loadPreviousPage()
        }

        binding.btnNextPage.setOnClickListener {
            viewModel.loadNextPage()
        }

        binding.rvProductsInCart.adapter = adapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
