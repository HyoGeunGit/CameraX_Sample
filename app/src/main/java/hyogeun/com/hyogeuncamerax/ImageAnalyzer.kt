package hyogeun.com.hyogeuncamerax

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit

class ImageAnalyzer : ImageAnalysis.Analyzer {

    private var lastAnalyzedTimestamp = 0L


    override fun analyze(image: ImageProxy?, rotationDegrees: Int) {
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimestamp >= TimeUnit.SECONDS.toMillis(1)) {
            val buffer = image?.planes?.get(0)?.buffer
            val data = buffer?.toByteArray()
            val pixels = data?.map { it.toInt() and 0xFF }
            val luma = pixels?.average()
            lastAnalyzedTimestamp = currentTimestamp
        }
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }

}
