package woowacourse.shopping.ui.screens.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.ui.component.topbar.MainTapBar

@Composable
fun ProductScreen(
    productStateHolder: ProductStateHolder,
    onIconClick: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    val products = productStateHolder.products

    Scaffold(
        topBar = {
            MainTapBar(
                title = "Shopping",
                iconResources = R.drawable.ic_cart,
                onIconClick = onIconClick,
                modifier = Modifier.statusBarsPadding(),
            )
        },
        modifier = Modifier.statusBarsPadding(),
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(20.dp),
            modifier = Modifier
                .padding(innerPadding),
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
                    onClick = { onItemClick(it.id) },
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
        productStateHolder = ProductStateHolder(),
        onIconClick = { },
        onItemClick = { },
    )
}
