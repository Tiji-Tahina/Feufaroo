package com.example.feufaroo.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feufaroo.ui.theme.FeufarooTheme

@Composable
fun BlockCard (block: Block) {
    Column (
        modifier = Modifier
            .padding(15.dp)
            .background(Color.Gray),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = block.marking, modifier = Modifier
            .padding(4.dp)
            .background(Color.DarkGray))
        Text(text = block.choir[0], modifier = Modifier
            .padding(4.dp)
            .background(Color.DarkGray))

        Text(text = block.choir[1], modifier = Modifier  // Make it a user input
            .padding(4.dp)
            .background(Color.DarkGray))

        Text(text = block.choir[2], modifier = Modifier
            .padding(4.dp)
            .background(Color.DarkGray))
        Text(text = block.choir[3], modifier = Modifier
            .padding(4.dp)
            .background(Color.DarkGray))
        Text(text = block.lyrics, modifier = Modifier
            .padding(4.dp)
            .background(Color.DarkGray))
    }
}

// trying a user entry
@Composable
fun SimpleTextFieldSample() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,         onValueChange = { newText ->
            // Handle the text change here
            text = newText
        },         label = { Text("Enter something") }
        // Add other parameters to customize the TextField
    )
}

@Preview(showBackground = true)
@Composable
fun BlockCardPreview () {
    FeufarooTheme {
        Row {
            BlockCard(Block("Fortissimo", listOf("do","do","re","do"), lyrics = "\"Tia\"" ))
            BlockCard(Block("Fortissimo", listOf("do","do","re","do"), lyrics = "\"Tia\"" ))
        }
    }
}