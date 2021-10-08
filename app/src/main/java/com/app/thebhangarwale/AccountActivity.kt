package com.app.thebhangarwale

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.appbar.MaterialToolbar

class AccountActivity : BhangarwaleConfigAndControllerActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.title = resources.getString(R.string.title_account)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val rv : RecyclerView = findViewById<RecyclerView>(R.id.recyclerviewItems)
        rv.apply {
            layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
            adapter = AccountAdapter(context,this@AccountActivity)
            addItemDecoration(DividerItemDecoration(context))
        }
    }

    override fun onClick(view : View?) {
        when(view?.id){
            R.id.layoutMain->{
                val id = view.getTag(R.string.tag_id)
                when(id){
                    R.id.address->{
                        startActivity(Intent(this,AddressActivity::class.java))
                    }
                    R.id.requests->{
                        startActivity(Intent(this,RequestActivity::class.java))
                    }
                }
            }
        }
    }
}

class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class AccountAdapter(val context: Context, val clickListener: View.OnClickListener) : RecyclerView.Adapter<AccountViewHolder>() {

    val menus : ArrayList<Menu> = ArrayList()

    init {
        val titleAddress = context.resources.getString(R.string.title_address)
        val subTitleAddress = context.resources.getString(R.string.sub_title_address)
        menus.add(Menu(R.id.address,titleAddress,subTitleAddress))

        val titleRequests = context.resources.getString(R.string.title_requests)
        val subTitleRequests = context.resources.getString(R.string.sub_title_requests)
        menus.add(Menu(R.id.requests,titleRequests,subTitleRequests))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_account,parent,false)
        return AccountViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        var menu : Menu = menus.get(position)
        holder.itemView.findViewById<TextView>(R.id.textViewTitle).text = menu.title
        holder.itemView.findViewById<TextView>(R.id.textViewSubTitle).text = menu.subTitle
        holder.itemView.setTag(R.string.tag_id,menu.id)
        holder.itemView.setOnClickListener(clickListener)
    }
}
