package woowacourse.shopping.presentation.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.presentation.common.ShoppingAppBar
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.detail.model.DetailUiState
import woowacourse.shopping.presentation.detail.ui.components.DetailContent
import woowacourse.shopping.presentation.detail.ui.components.LastSeenProductCard
import woowacourse.shopping.ui.theme.Green40

@Composable
fun DetailScreen(
    uiState: DetailUiState.Success,
    onClickLastProductCard: (Long) -> Unit,
    onBack: () -> Unit,
    onAddToCart: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            ShoppingAppBar(
                contents = {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(16.dp)
                                .clickable { onBack() },
                    )
                },
            )
        },
        bottomBar = {
            Box(
                modifier =
                    Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Green40)
                        .clickable { onAddToCart() },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.add_product_to_cart),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        },
        modifier =
            modifier
                .statusBarsPadding(),
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            DetailContent(
                imageUrl = uiState.product.imageUrl,
                productName = uiState.product.name,
                price = uiState.price,
                quantity = uiState.quantity,
                onIncrease = { onIncrease() },
                onDecrease = { onDecrease() },
            )
            if (uiState.showLastSeenProductCard) {
                uiState.lastSeenProduct?.let { lastProduct ->
                    LastSeenProductCard(
                        name = lastProduct.name,
                        onClick = { onClickLastProductCard(lastProduct.id) },
                        modifier =
                            Modifier
                                .padding(horizontal = 18.dp),
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        uiState =
            DetailUiState.Success(
                product =
                    ProductUiModel(
                        id = 1L,
                        name = "제품",
                        price = 134L,
                        imageUrl = "",
                    ),
                quantity = 1,
            ),
        onClickLastProductCard = {},
        onBack = {},
        onAddToCart = {},
        onIncrease = {},
        onDecrease = {},
    )
}
