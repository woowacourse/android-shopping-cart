package woowacourse.shopping.ui.screens.productdetail

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.ui.component.AmountController
import woowacourse.shopping.ui.component.topbar.DismissTopBar

@Composable
fun ProductDetailScreen(
    productId: String,
    onDismiss: () -> Unit,
    onNavigateToProduct: (String) -> Unit,
    viewModel: ProductDetailViewModel = viewModel(factory = ProductDetailViewModel.Factory),
) {
    val uiState: ProductDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    LaunchedEffect(uiState.isError) {
        if (uiState.isError) onDismiss()
    }

    Scaffold(
        topBar = {
            DismissTopBar(
                onDismiss = onDismiss,
            )
        },
        modifier = Modifier
            .systemBarsPadding(),
    ) { innerPadding ->
        ProductDetailScreenContent(
            uiState = uiState,
            innerPadding = innerPadding,
            onMinusClick = viewModel::minusAmount,
            onPlusClick = viewModel::plusAmount,
            onAddToCart = viewModel::addToCart,
            onClickRecentProduct = onNavigateToProduct,
        )
    }
}

@Composable
fun ProductDetailScreenContent(
    uiState: ProductDetailUiState,
    innerPadding: PaddingValues,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
    onAddToCart: () -> Unit,
    onClickRecentProduct: (String) -> Unit,
) {
    Column {
        uiState.product?.let {
            ProductDetail(
                product = it,
                amount = uiState.amount,
                onClickMinus = { onMinusClick() },
                onClickPlus = { onPlusClick() },
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        uiState.latestProduct?.let {
            LatestProductCard(
                name = it.name,
                onClickItem = { onClickRecentProduct(it.productId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        AddCartButton(
            onClick = { onAddToCart() },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ProductDetail(
    product: Product,
    amount: Int,
    onClickMinus: () -> Unit,
    onClickPlus: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "${product.name} 이미지",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProductInfoText(
            name = product.name,
            price = product.price,
            amount = amount,
            onClickMinus = { onClickMinus() },
            onClickAdd = { onClickPlus() },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun LatestProductCard(
    name: String,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClickItem)
            .border(1.dp, Color(0xffAAAAAA), shape = RoundedCornerShape(5.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = "마지막으로 본 상품", color = Color(0xff04C09E))
        Text(text = name)
    }
}

@Composable
private fun ProductInfoText(
    name: String,
    price: Int,
    amount: Int,
    onClickMinus: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(17.dp),
    ) {
        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(horizontal = 18.dp),
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFFAAAAAA),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "${"%,d".format(price)}원",
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
            )

            AmountController(
                amount = amount.toString(),
                onClickMinus = onClickMinus,
                onClickAdd = onClickAdd,
                modifier = Modifier.width(126.dp),
            )
        }
    }
}

@Composable
private fun AddCartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF04C09E),
            contentColor = Color.White,
        ),
        modifier = modifier,
    ) {
        Text(
            text = "장바구니 담기",
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier
                .padding(vertical = 12.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreenContent(
        uiState = ProductDetailUiState(
            product = Product(
                id = "1",
                name = "고양이",
                price = 10000,
                imageUrl = "",
            ),
            latestProduct = RecentProduct(
                productId = "1",
                name = "고양이",
                imageUrl = "",
                viewedAt = 1,
            ),
        ),
        innerPadding = PaddingValues(),
        onMinusClick = { },
        onPlusClick = { },
        onAddToCart = { },
        onClickRecentProduct = { },
    )
}
