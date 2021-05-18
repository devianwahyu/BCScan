package com.devianwahyu.bcscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode

class MainActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)

        codeScanner.apply {
            this.camera = CodeScanner.CAMERA_BACK
            this.formats = CodeScanner.ALL_FORMATS
            this.autoFocusMode = AutoFocusMode.SAFE
            this.scanMode = ScanMode.SINGLE
            this.isAutoFocusEnabled = true
            this.isFlashEnabled = true

            // Callback
            this.decodeCallback = DecodeCallback {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Scan result : ${it.text}", Toast.LENGTH_LONG).show()
                }
            }

            this.errorCallback = ErrorCallback {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Camera error : ${it.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}