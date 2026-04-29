package woowacourse.shopping.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.ui.productdetail.ProductAppBar

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProductAppBar()
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            CartItemList()
        }
    }
}

@Composable
private fun CartItemList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(
            key = { it.id },
            items = MockData.MOCK_PRODUCTS,
        ) {
            SingleCartItem(
                imageUrl = it.imageUrl,
                title = it.name,
                price = it.price,
            )
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen()
}
