package com.example.feufaroo.views

import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TemporaryFolder

class BlockViewKtTest{

    @Rule
    @JvmField
    val Folder = TemporaryFolder()

    @Test
    fun testFileReading(){
        val file = readFileLine(fileName)
        assertEquals("|", file[0])
    }
}