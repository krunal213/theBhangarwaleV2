package com.app.thebhangarwale

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.appbar.MaterialToolbar

class RequestActivity : BhangarwaleConfigAndControllerActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.title = resources.getString(R.string.title_requests)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val rv : RecyclerView = findViewById<RecyclerView>(R.id.recyclerviewItems)
        rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = RequestAdapter(context, this@RequestActivity)
            addItemDecoration(DividerItemDecoration(context))
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.item->{
                val intentAddAddress = Intent(this,RequestDetailActivity::class.java)
                startActivity(intentAddAddress)
            }
        }
    }

}

class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class RequestAdapter(val context: Context, val clickListener: View.OnClickListener) : RecyclerView.Adapter<RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_request_records,parent,false)
        return RequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.itemView.setOnClickListener(clickListener)
        /*  var menu : Menu = menus.get(position)
          holder.itemView.textViewTitle.text = menu.title
          holder.itemView.textViewSubTitle.text = menu.subTitle
          holder.itemView.setTag(R.string.tag_id,menu.id)
          holder.itemView.setOnClickListener(clickListener)*/
    }
}