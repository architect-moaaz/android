package com.intelliflow.apps.view

import android.content.Context
import android.os.*
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.github.barteksc.pdfviewer.PDFView
import com.intelliflow.apps.R
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Help_center.newInstance] factory method to
 * create an instance of this fragment.
 */
class TermsOfUse : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
  var pdfView:PDFView ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return
      var view=  inflater.inflate(R.layout.fragment_termsofuse, container, false)
//      var  webView = view.findViewById<WebView>(R.id.terms_webView)
//        webView.webViewClient = WebViewClient()
//        webView.settings.setSupportZoom(true)
//        webView.settings.javaScriptEnabled = true
//        val url = getPdfUrl()
//        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$url")

        pdfView=view.findViewById(R.id.pdfView)
        val fileName = "myFile.pdf"
        context?.let { getRootDirPath(it) }?.let {
            downloadPdfFromInternet(
                getPdfUrl(),
                it,
                fileName
            )
        }

        return view
    }

    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {

                override fun onDownloadComplete() {
//                    Toast.makeText(this@TermsOfUse, "downloadComplete", Toast.LENGTH_LONG)
//                        .show()
                    val downloadedFile = File(dirPath, fileName)
                  //  progressBar.visibility = View.GONE
                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: com.downloader.Error?) {
                    TODO("Not yet implemented")
                }

//                override fun onError(error: Error?) {
////                    Toast.makeText(
////                        this@TermsOfUse,
////                        "Error in downloading file : $error",
////                        Toast.LENGTH_LONG
////                    )
////                        .show()
//                }

            })
    }

    private fun showPdfFromFile(file: File) {
     //   val pdfView =PDFView(context,null)
        pdfView?.fromFile(file)
            ?.password(null)
            ?.defaultPage(0)
            ?.enableSwipe(true)
            ?.swipeHorizontal(false)
            ?.enableDoubletap(true)
            ?.onPageError { page, _ ->
//                Toast.makeText(
//                    this@PdfViewActivity,
//                    "Error at page: $page", Toast.LENGTH_LONG
//                ).show()
            }
            ?.load()
    }

    private fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }

    fun getPdfUrl(): String {
        return "http://intelliflow.co.in/wp-content/uploads/2022/06/Terms-of-Use.pdf"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Help_center.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TermsOfUse().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}