package woowacourse.shopping.presentation.cart.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CartScaffold(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CartTopAppBar(
                onClick = onBack,
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            content()
        }
    }
}

@Preview
@Composable
fun CartScaffoldPreview() {
    CartScaffold(
        onBack = {},
        content = {},
    )
}
