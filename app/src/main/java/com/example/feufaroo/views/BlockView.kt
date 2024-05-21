package com.example.feufaroo.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feufaroo.ui.theme.FeufarooTheme
import java.io.File


// read the file here
var fileName = "C:\\Users\\26134\\AndroidStudioProjects\\Feufaroo\\app\\src\\main\\java\\com\\example\\feufaroo\\assets\\ProjectTemplateSample.txt"
fun readFileLine(fileName: String): List<String> = File(fileName).bufferedReader().readLines()

val fileInline = readFileLine(fileName)


@Composable
fun SampleDataInRows(){
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        items(fileInline) { line ->
            Text(line)
        }
    }
}

/*
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
*/

@Preview(showBackground = true)
@Composable
fun BlockCardPreview () {
    FeufarooTheme {
        SampleDataInRows()
    }
}