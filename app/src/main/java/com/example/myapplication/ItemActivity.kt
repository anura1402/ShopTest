package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.razorpay.Checkout
import org.json.JSONObject

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        Checkout.preload(applicationContext)
        val co = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
        co.setKeyID("rzp_test_EgmQoGwAT33eJi")

        val title: TextView = findViewById(R.id.item_list_title_one)
        val text: TextView = findViewById(R.id.item_list_text)
        val image: ImageView = findViewById(R.id.item_list_image_one)
        val price: TextView = findViewById(R.id.item_list_price_one)
        val button:Button =findViewById(R.id.button_buy)

        title.text = intent.getStringExtra("itemTitle")
        text.text = intent.getStringExtra("itemText")
        price.text = intent.getStringExtra("itemPrice")+"₽"
        val imageId = intent.getIntExtra("itemImage",0)
        image.setImageResource(imageId)

        button.setOnClickListener {
            //получение order_id с ордера на сайте
            startPayment()
        }

    }
    private fun startPayment() {

        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Anura Test")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#f00");
            options.put("currency","RUB");
            options.put("order_id", "order_LoFddJZDnSKKnm");
            options.put("amount",10000)//pass amount in currency subunits

            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            Log.d("TAG123",e.toString())
            e.printStackTrace()
        }
    }
}