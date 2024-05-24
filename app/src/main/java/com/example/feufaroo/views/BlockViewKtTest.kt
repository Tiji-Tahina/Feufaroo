package com.example.feufaroo.views

import android.util.Log
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class BlockViewKtTest{
    @Test
    fun testFileReading(){
        val file = readFileLine(fileName)
        assertEquals("|", file[0])
    }

    @Test
    fun testBlocks(){
        val blocks = createBlocks(textLines, separators, endBlock, lyrics)

        val block = Block(separator = ":", choir = mutableListOf("s₁","m₁","d","d₁"))
        assertEquals(block.separator, blocks[0].separator)
    }
}