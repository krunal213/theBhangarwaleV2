package com.app.thebhangarwale

import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import java.util.*
import kotlin.collections.ArrayList

class ConfirmationActivity : BhangarwaleConfigAndControllerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.title_bhangar_request)
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
                LinearLayoutManager(this@ConfirmationActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ItemAdapter()
            addItemDecoration(
                DividerItemDecoration(this@ConfirmationActivity,
                    DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(this@ConfirmationActivity, R.drawable.drawable_horizonatal_divider)?.let {
                    setDrawable(
                        it
                    )
                }
            })
        }

    }

}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_v2,parent,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5;
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        /*Glide.with(holder.itemView.context)
            .load(R.drawable.download)
            .transform(CenterCrop(), RoundedCorners(8))
            .into(holder.itemView.image)*/
    }

}

