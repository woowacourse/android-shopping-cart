package woowacourse.shopping.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.view.products.ProductListFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val viewModel: MainViewModel by lazy {
        val viewModelFactory = MainViewModelFactory(ProductRepositoryImpl(context = this@MainActivity))
        viewModelFactory.create(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            ProductListFragment()
        ).commit()
    }
}
