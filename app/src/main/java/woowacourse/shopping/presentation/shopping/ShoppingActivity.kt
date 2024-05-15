package woowacourse.shopping.presentation.shopping

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.detail.DetailActivity

class ShoppingActivity : AppCompatActivity(), ShoppingClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ShoppingAdapter
    private lateinit var viewModel: ShoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()

        binding.lifecycleOwner = this

        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)

        adapter = ShoppingAdapter(this)
        binding.rvProductList.adapter = adapter

        val factory = ShoppingViewModelFactory(ShoppingItemsRepositoryImpl())
        viewModel = ViewModelProvider(this, factory)[ShoppingViewModel::class.java]
        viewModel.products.observe(
            this,
            Observer { products ->
                adapter.loadData(products)
            },
        )
        binding.vmProduct = viewModel
        binding.rvProductList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    if (lastVisibleItem == totalItemCount - 1) {
                        // loadMoreData()
                        if (dy > 0) {
                            binding.btnLoadMore.visibility = View.VISIBLE
                        } else {
                            binding.btnLoadMore.visibility = View.GONE
                        }
                    } else {
                        binding.btnLoadMore.visibility = View.GONE
                    }
                }
            },
        )
        binding.clickListener = this
    }

    private fun setUpToolbar() {
        val toolbar: MaterialToolbar = binding.toolbarMain
        setSupportActionBar(toolbar)

        toolbar.setOnMenuItemClickListener {
            navigateToShoppingCart()
            true
        }
    }

    private fun navigateToShoppingCart() {
        startActivity(CartActivity.createIntent(context = this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onProductClick(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }

    override fun onLoadButtonClick() {
        viewModel.loadProducts()
        binding.btnLoadMore.visibility = GONE
    }
}
