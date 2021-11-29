package com.app.thebhangarwale

import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.view.*
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import java.util.*
import kotlin.collections.ArrayList

class RequestDetailActivity : LocalizationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setSubtitleTextColor(resources.getColor(R.color.color_for_pending))
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Req Id : Bhangar10001"
        supportActionBar?.subtitle = "PENDING"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val myaddress = ArrayList<Address>()
        val address = Address(Locale.ENGLISH)
        address.setAddressLine(0,"4D, Harish Arjun Palay Marg, New Patra Chawl, Dhaku Prabhuchi Wadi, Byculla East, Byculla, Mumbai, Maharashtra 400033, India")
        address.latitude = 18.98307494631155
        address.longitude = 72.83906176686287
        myaddress.add(address)

        findViewById<TextView>(R.id.textViewAddress).apply {
            text = "4D, Harish Arjun Palay Marg, New Patra Chawl, Dhaku Prabhuchi Wadi, Byculla East, Byculla, Mumbai, Maharashtra 400033, India"
        }

        findViewById<RecyclerView>(R.id.recyclerviewBhangarItems).apply {
            layoutManager =
                LinearLayoutManager(this@RequestDetailActivity, LinearLayoutManager.VERTICAL, false)
            adapter = RequestItemAdapter()
            addItemDecoration(
                DividerItemDecoration(this@RequestDetailActivity,
                    DividerItemDecoration.VERTICAL).apply {
                    ContextCompat.getDrawable(this@RequestDetailActivity, R.drawable.drawable_horizonatal_divider)?.let {
                        setDrawable(
                            it
                        )
                    }
                })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_request_cancel, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_cancel_request->{
                val intentAddAddress = Intent(this,CancelRequestActivity::class.java)
                startActivity(intentAddAddress)
            }
        }
        return true
    }

}

class RequestItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class RequestItemAdapter : RecyclerView.Adapter<RequestItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_v3,parent,false)
        return RequestItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: RequestItemViewHolder, position: Int) {
        /*Glide.with(holder.itemView.context)
            .load(R.drawable.download)
            .transform(CenterCrop(), RoundedCorners(8))
            .into(holder.itemView.image)*/
    }

}

