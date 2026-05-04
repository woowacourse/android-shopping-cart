package woowacourse.shopping.ui.screens.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import woowacourse.shopping.R
import woowacourse.shopping.ui.component.topbar.MainTapBar

@Composable
fun ProductScreen(
    onIconClick: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    val productStateHolder = rememberSaveable(saver = ProductStateHolder.Saver) {
        ProductStateHolder()
    }
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        productStateHolder.initProducts()
    }

    val products = productStateHolder.products
    val scope = rememberCoroutineScope()

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
            state = listState,
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

            if (productStateHolder.hasNext) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    productStateHolder.getProducts()
                                }
                            },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_down),
                            contentDescription = "상품 더보기",
                            modifier = Modifier
                                .padding(12.dp)
                                .size(24.dp)
                                .align(Alignment.Center),
                            tint = Color(0xFF555555),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductScreenPreview() {
    ProductScreen(
        onIconClick = { },
        onItemClick = { },
    )
}
