package mg.dot.feufaroo.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import mg.dot.feufaroo.R

class BlockScrollingFragment : androidx.fragment.app.Fragment() {

    private fun getRawTextFile( resourceId: Int): String {
        val inputStream = resources.openRawResource(resourceId)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        return String(buffer)
    }
    private val fileContent = getRawTextFile(R.raw.projecttemplaterefactor)
    val textLinesFromText = fileContent.split("/n")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setContent {
                BasicText(text = "Hello BlockScrollingFragment here !")
            }
        }
    }
}