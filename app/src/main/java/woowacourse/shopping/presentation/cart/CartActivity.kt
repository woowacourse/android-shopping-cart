package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.detail.DetailActivity

class CartActivity : AppCompatActivity(), CartClickListener {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private lateinit var viewModel: CartViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        
        binding.lifecycleOwner = this
        
        adapter = CartAdapter(this)
        binding.recyclerView.adapter = adapter
        
        val factory = CartViewModelFactory(repository = CartRepositoryImpl())
        viewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        viewModel.shoppingCart.observe(this, Observer { cartItems ->
            adapter.loadData(cartItems)
        })
        
        viewModel.isFirstPage.observe(this, Observer { isFirst ->
            binding.btnBefore.isEnabled = !isFirst
        })
        
        viewModel.isLastPage.observe(this, Observer { isLast ->
            binding.btnNext.isEnabled = !isLast
        })
        
        viewModel.isPageControlVisible.observe(this, Observer { isVisible ->
            binding.pageLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
        })
        
        viewModel.currentPage.observe(this, Observer { page ->
            binding.tvPage.text = (page + 1).toString()
        })
        
        binding.btnBefore.setOnClickListener {
            viewModel.loadPreviousPage()
        }
        
        binding.btnNext.setOnClickListener {
            viewModel.loadNextPage()
        }
        
        
    }
    
    private fun setUpToolbar() {
        val toolbar: MaterialToolbar = binding.toolbarCart
        setSupportActionBar(toolbar)
        
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    override fun onItemClick(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }
    
    override fun onDeleteItemClick(itemId: Long) {
        viewModel.deleteItem(itemId)
    }
    
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}

