package woowacourse.shopping.ui.screens.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.component.topbar.MainTapBar

@Composable
fun ProductScreen(
    products: List<Product>,
    onIconClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            MainTapBar(
                title = "Shopping",
                iconResources = R.drawable.ic_cart,
                onIconClick = onIconClick,
            )
        },
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = products,
                key = { it.id },
            ) {
                ProductCard(
                    imageUrl = it.imageUrl,
                    name = it.name,
                    price = it.price,
                    onClick = {},
                    modifier = Modifier,
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductScreenPreview() {
    ProductScreen(
        products = listOf(
            Product(
                name = "안녕",
                price = 10000,
                imageUrl = "TODO()",
            ),
            Product(
                name = "안녕",
                price = 10000,
                imageUrl = "TODO()",
            ),
            Product(
                name = "안녕",
                price = 10000,
                imageUrl = "TODO()",
            ),
            Product(
                name = "안녕",
                price = 10000,
                imageUrl = "TODO()",
            ),
            Product(
                name = "안녕",
                price = 10000,
                imageUrl = "TODO()",
            ),
            Product(
                name = "안녕",
                price = 10000,
                imageUrl = "TODO()",
            ),
        ),
        onIconClick = { },
    )
}
