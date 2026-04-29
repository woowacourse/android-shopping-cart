package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.constants.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProductListScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ProductListAppBar()
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            ProductList(
                products = MockData.MOCK_PRODUCTS,
            )
        }
    }
}
