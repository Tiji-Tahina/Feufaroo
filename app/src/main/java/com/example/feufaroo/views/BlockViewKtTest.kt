package com.example.feufaroo.views

import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TemporaryFolder

class BlockViewKtTest{
    @Test
    fun testFileReading(){
        val file = readFileLine(fileName)
        assertEquals("|", file[0])
    }

    fun main(args: Array<String>) {
        for (i in 1..5) {
            println(i)
        }
    }

    fun main(){
        for (i in 1..56){
            print("ok")
        }
    }
}