package com.app.thebhangarwale

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.appbar.MaterialToolbar

class HelpActivity : LocalizationActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.title = resources.getString(R.string.title_help)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val rv : RecyclerView = findViewById<RecyclerView>(R.id.recyclerviewItems)
        rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = HelpAdapter(context,this@HelpActivity)
            addItemDecoration(DividerItemDecoration(context))
        }


    }

    override fun onClick(view : View?) {
        when(view?.id){
            R.id.layoutMain->{
                val id = view.getTag(R.string.tag_id)
                when(id){
                    R.id.contact_us->{
                        startActivity(Intent(Intent.ACTION_DIAL).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            data = Uri.parse("tel:0123456789")
                        })
                    }
                    R.id.email_us->{
                        startActivity(Intent(Intent.ACTION_SENDTO).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            data = Uri.parse("mailto:help@bhangarwale.com")
                        })
                    }
                }
            }
        }
    }
}


class HelpAdapter(val context: Context, val clickListener: View.OnClickListener) : RecyclerView.Adapter<HelpViewHolder>() {

    val menus : ArrayList<Menu> = ArrayList()

    init {
        val titleContactUs = context.resources.getString(R.string.title_contact_us)
        val subTitleContactUs = context.resources.getString(R.string.sub_title_contact_us)
        menus.add(Menu(R.id.contact_us,titleContactUs,subTitleContactUs))

        val titleEmailUs = context.resources.getString(R.string.title_email_us)
        val subTitleEmailUs = context.resources.getString(R.string.sub_title_email_us)
        menus.add(Menu(R.id.email_us,titleEmailUs,subTitleEmailUs))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_help,parent,false)
        return HelpViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
        var menu : Menu = menus.get(position)
        holder.itemView.findViewById<TextView>(R.id.textViewTitle).text = menu.title
        holder.itemView.findViewById<TextView>(R.id.textViewSubTitle).text = menu.subTitle
        holder.itemView.setTag(R.string.tag_id,menu.id)
        holder.itemView.setOnClickListener(clickListener)
    }
}

class HelpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)