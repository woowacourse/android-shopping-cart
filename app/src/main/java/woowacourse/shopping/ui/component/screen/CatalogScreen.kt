package woowacourse.shopping.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.ui.component.frame.CommonFrame
import woowacourse.shopping.ui.component.item.CartCountLabel
import woowacourse.shopping.ui.component.item.RecentlyViewedProducts
import woowacourse.shopping.ui.component.item.ShoppingItem

@Composable
fun CatalogScreen(
    catalog: Products,
    recentlyViewedProducts: Products,
    onRecentlyViewedClick: (Product) -> Unit,
    totalCount: () -> Int,
    specificProductCount: (String) -> Int,
    onItemClick: (Product) -> Unit,
    onCartClick: () -> Unit,
    onLoadClick: () -> Unit,
    onAdd: (String, Int) -> Unit,
    onMinus: (String, Int) -> Unit,
    onDelete: (String) -> Unit,
    onAddInCart: (PurchaseProduct) -> Unit,
    isLoading: Boolean,
    isContainedInCart: (String) -> Boolean,
    modifier: Modifier = Modifier,
) {
    CommonFrame(
        headerContent = { CatalogHeader(totalCount, onCartClick) },
        bodyContent = {
            CatalogBody(
                catalog = catalog,
                recentlyViewedProducts = recentlyViewedProducts,
                onRecentlyViewedClick = onRecentlyViewedClick,
                onItemClick = { onItemClick(it) },
                onLoadClick = onLoadClick,
                onAdd = { id, updateAmount ->
                    onAdd(id, updateAmount)
                },
                onMinus = { id, updateAmount ->
                    onMinus(id, updateAmount)
                },
                onDelete = { onDelete(it) },
                onAddInCart = { onAddInCart(it) },
                specificProductCount = {
                    specificProductCount(it)
                },
                isContainedInCart = isContainedInCart,
                isLoading = isLoading,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun CatalogHeader(
    totalCount: () -> Int,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = "Shopping",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )
        Row {
            Icon(
                painter = painterResource(R.drawable.ic_cart),
                contentDescription = "장바구니 아이콘",
                tint = Color.White,
                modifier =
                    Modifier
                        .size(24.dp)
                        .clickable(onClick = onCartClick),
            )
            CartCountLabel(totalCount())
        }
    }
}

@Composable
private fun CatalogBody(
    catalog: Products,
    recentlyViewedProducts: Products,
    onRecentlyViewedClick: (Product) -> Unit,
    specificProductCount: (String) -> Int,
    onItemClick: (Product) -> Unit,
    onAddInCart: (PurchaseProduct) -> Unit,
    onAdd: (String, Int) -> Unit,
    onMinus: (String, Int) -> Unit,
    onDelete: (String) -> Unit,
    onLoadClick: () -> Unit,
    isLoading: Boolean,
    isContainedInCart: (String) -> Boolean,
    modifier: Modifier = Modifier,
) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier,
            contentPadding = PaddingValues(12.dp),
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) },
            ) {
                if (!recentlyViewedProducts.isEmpty()) {
                    RecentlyViewedProducts(
                        recentlyViewedProducts,
                        onClick = onRecentlyViewedClick,
                    )
                }
            }

            items(catalog.size()) { item ->
                ShoppingItem(
                    product = catalog.getSingleItem(item),
                    onClick = {
                        onItemClick(catalog.getSingleItem(item))
                    },
                    count = {
                        specificProductCount(catalog.getSingleItem(item).id)
                    },
                    isContainedInCart = {
                        isContainedInCart(catalog.getSingleItem(item).id)
                    },
                    onAdd = {
                        onAdd(catalog.getSingleItem(item).id, 1)
                    },
                    onMinus = {
                        onMinus(catalog.getSingleItem(item).id, -1)
                    },
                    onDelete = {
                        onDelete(catalog.getSingleItem(item).id)
                    },
                    onAddInCart = { onAddInCart(it) },
                )
            }

            item(
                span = { GridItemSpan(maxLineSpan) },
            ) {
                LoadBtn(
                    isLoading = isLoading,
                    onLoad = onLoadClick,
                )
            }
        }
    }
}

@Composable
private fun LoadBtn(
    isLoading: Boolean,
    onLoad: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isLoading) {
        Row(
            modifier =
                Modifier
                    .padding(25.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.LightGray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "더보기 버튼",
            tint = Color.White,
            modifier =
                modifier
                    .padding(25.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.LightGray)
                    .clickable(onClick = onLoad),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CatalogScreenPreview() {
    val catalog =
        Products(
            listOf(
                Product(
                    imageUri = "hello",
                    name = "너무너무너무긴아이템이름",
                    price = 100000,
                ),
                Product(
                    imageUri = "디디",
                    name = "당근주스",
                    price = 1000,
                ),
                Product(
                    imageUri = "hello",
                    name = "우유",
                    price = 100,
                ),
                Product(
                    imageUri = "hello",
                    name = "투핸더",
                    price = 100000000,
                ),
                Product(
                    imageUri = "hello",
                    name = "너무너무너무긴아이템이름",
                    price = 100000,
                ),
                Product(
                    imageUri = "디디",
                    name = "당근주스",
                    price = 1000,
                ),
                Product(
                    imageUri = "hello",
                    name = "우유",
                    price = 100,
                ),
                Product(
                    imageUri = "hello",
                    name = "투핸더",
                    price = 100000000,
                ),
                Product(
                    imageUri = "hello",
                    name = "너무너무너무긴아이템이름",
                    price = 100000,
                ),
                Product(
                    imageUri = "디디",
                    name = "당근주스",
                    price = 1000,
                ),
                Product(
                    imageUri = "hello",
                    name = "우유",
                    price = 100,
                ),
                Product(
                    imageUri = "hello",
                    name = "투핸더",
                    price = 100000000,
                ),
            ),
        )

    CatalogScreen(
        catalog,
        catalog,
        onRecentlyViewedClick = {},
        totalCount = { 10 },
        specificProductCount = { it -> 0 },
        onItemClick = { },
        onCartClick = { },
        onLoadClick = { },
        onAdd = { id, type -> },
        onMinus = { id, type -> },
        onDelete = { },
        onAddInCart = { },
        isContainedInCart = { it -> true },
        isLoading = false,
    )
}

@Preview(showBackground = true)
@Composable
private fun CatalogScreenPreview2() {
    val catalog =
        Products(
            listOf(
                Product(
                    imageUri = "hello",
                    name = "너무너무너무긴아이템이름",
                    price = 100000,
                ),
                Product(
                    imageUri = "디디",
                    name = "당근주스",
                    price = 1000,
                ),
                Product(
                    imageUri = "hello",
                    name = "우유",
                    price = 100,
                ),
                Product(
                    imageUri = "hello",
                    name = "투핸더",
                    price = 100000000,
                ),
                Product(
                    imageUri = "hello",
                    name = "너무너무너무긴아이템이름",
                    price = 100000,
                ),
                Product(
                    imageUri = "디디",
                    name = "당근주스",
                    price = 1000,
                ),
                Product(
                    imageUri = "hello",
                    name = "우유",
                    price = 100,
                ),
                Product(
                    imageUri = "hello",
                    name = "투핸더",
                    price = 100000000,
                ),
                Product(
                    imageUri = "hello",
                    name = "너무너무너무긴아이템이름",
                    price = 100000,
                ),
                Product(
                    imageUri = "디디",
                    name = "당근주스",
                    price = 1000,
                ),
                Product(
                    imageUri = "hello",
                    name = "우유",
                    price = 100,
                ),
                Product(
                    imageUri = "hello",
                    name = "투핸더",
                    price = 100000000,
                ),
            ),
        )

    CatalogScreen(
        catalog,
        Products(),
        onRecentlyViewedClick = {},
        totalCount = { 10 },
        specificProductCount = { it -> 0 },
        onItemClick = { },
        onCartClick = { },
        onLoadClick = { },
        onAdd = { id, type -> },
        onMinus = { id, type -> },
        onDelete = { },
        onAddInCart = { },
        isContainedInCart = { it -> true },
        isLoading = true,
    )
}
