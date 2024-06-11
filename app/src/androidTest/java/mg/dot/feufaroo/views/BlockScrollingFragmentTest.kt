package mg.dot.feufaroo.views

import mg.dot.feufaroo.R
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class BlockScrollingFragmentTest {
    @Test
    fun testGetFile() {
        val fragment = BlockScrollingFragment()

//        val resourceId = R.raw.projecttemplaterefactor
//
//        val fileContentHere = fragment.resources.getRawTextFile(resourceId)
//
//        val method = fragment::class.java.getDeclaredField("myPrivateMethod")
//        method.isAccessible = true
//        val value = field.get(myClass) as String
//
//        // Add assertions to verify the expected behavior
//        // For example:
//        // assertEquals("Expected content", fileContent)

        val textLines = fragment.textLinesFromText
        assertEquals(textLines[0],":")


    }
    @Test
    fun testFileContent() {
        val error = 0
    }
}