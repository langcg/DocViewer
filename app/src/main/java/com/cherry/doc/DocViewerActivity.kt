package com.cherry.doc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.cherry.doc.util.Constant
import com.cherry.doc.util.MainHandler
import com.cherry.lib.doc.DocViewer
import com.cherry.lib.doc.bean.DocSourceType
import com.cherry.lib.doc.bean.FileType
import com.cherry.lib.doc.office.IOffice
import com.cherry.lib.doc.util.FileUtils
import com.cherry.lib.doc.util.ViewUtils.hide
import com.cherry.lib.doc.util.ViewUtils.show
import kotlinx.android.synthetic.main.activity_doc_viewer.*
import java.io.File

class DocViewerActivity : AppCompatActivity() {

    companion object {
        fun launchDocViewer (activity: AppCompatActivity,docSourceType: Int, path: String?) {
            var intent = Intent(activity, DocViewerActivity::class.java)
            intent.putExtra(Constant.INTENT_SOURCE_KEY, docSourceType)
            intent.putExtra(Constant.INTENT_DATA_KEY,path)
            activity.startActivity(intent)
        }
    }

    var docSourceType = 0
    var docUrl: String? = null//文件地址

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_viewer)

        initView()
        initData(intent)
    }

    fun initView() {
    }

    fun initData(intent: Intent?) {
        docUrl = intent?.getStringExtra(Constant.INTENT_DATA_KEY)
        docSourceType = intent?.getIntExtra(Constant.INTENT_SOURCE_KEY,0) ?: 0

        var fileType = FileUtils.getFileTypeForUrl(docUrl)
        when (fileType) {
            FileType.PDF -> {
                DocViewer.showPdf(docSourceType,mPdfView,docUrl)
            }
            FileType.IMAGE -> {
                if (docSourceType == DocSourceType.PATH) {
                    mIvImage.load(File(docUrl))
                } else {
                    mIvImage.load(docUrl)
                }
            }
            else -> {
                DocViewer.showDoc(this,mFlDocContainer,docUrl,docSourceType)
            }
        }
    }
}