package woowacourse.shopping.ui.component.frame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Body(
    modifier: Modifier = Modifier,
    bodyContent: @Composable () -> Unit,
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        bodyContent()
    }
}

@Preview
@Composable
private fun BodyPreview() {
    Body() {
        Text("Body")
    }
}
