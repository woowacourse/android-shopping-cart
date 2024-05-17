package woowacourse.shopping.presentation.ui.shopping

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.DetailActivity

class ShoppingActivity : AppCompatActivity(), ShoppingClickListener {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var adapter: ShoppingAdapter
    private val viewModel: ShoppingViewModel by viewModels { ShoppingViewModelFactory(ShoppingItemsRepositoryImpl()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        setUpRecyclerView()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
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

    private fun setUpRecyclerView() {
        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)
        setUpRecyclerViewAdapter()
        checkLoadMoreBtnVisibility()
    }

    private fun setUpRecyclerViewAdapter() {
        adapter = ShoppingAdapter(this)
        binding.rvProductList.adapter = adapter

        viewModel.products.observe(
            this,
            Observer { products ->
                adapter.loadData(products)
            },
        )
    }

    private fun checkLoadMoreBtnVisibility() {
        binding.rvProductList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.showLoadMoreBtn()
                    } else {
                        if (dy < 0) viewModel.hideLoadMoreBtn()
                    }
                }
            },
        )
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
        viewModel.hideLoadMoreBtn()
    }
}
