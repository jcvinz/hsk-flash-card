package com.hskflashcard.ui.screens.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.hskflashcard.R
import com.hskflashcard.ui.components.ProgressCard

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {

    ProfileScreenContent(
        imageUrl = viewModel.imageUrl.collectAsState().value,
        name = viewModel.name.collectAsState().value,
        email = viewModel.email.collectAsState().value,
        learnedWords = viewModel.progress.collectAsState().value.first,
        totalWords = viewModel.progress.collectAsState().value.second
    )

}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    email: String,
    learnedWords: Int,
    totalWords: Int
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(top = 32.dp)
                .size(120.dp)
                .clip(CircleShape),
            model = imageUrl, contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = email,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(32.dp))
        ProgressCard(
            learnedWords = learnedWords,
            totalWords = totalWords
        )
        Spacer(modifier = Modifier.height(32.dp))
        OptionButton(icon = R.drawable.ic_round_save, text = "Save your progress to cloud") { }
        Spacer(modifier = Modifier.height(16.dp))
        OptionButton(icon = R.drawable.ic_round_logout, text = "Log out") { }
    }
}

@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    text: String,
    onClicked: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClicked()
            }) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = ImageVector.vectorResource(icon), contentDescription = null)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text)
        }
    }
}