package woowacourse.shopping.presentation.shopping

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.presentation.detail.DetailActivity

class ShoppingActivity : AppCompatActivity(), ProductClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ShoppingAdapter
    private lateinit var viewModel: ShoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                Log.d("crong", "$products")
            },
        )
        binding.vmProduct = viewModel
    }

    override fun onClick(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }
}
