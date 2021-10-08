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
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.appbar.MaterialToolbar
import java.util.*
import kotlin.collections.ArrayList

class SupportActivity: LocalizationActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.title = resources.getString(R.string.title_support)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val rv : RecyclerView = findViewById<RecyclerView>(R.id.recyclerviewItems)
        rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = SupportAdapter(context,this@SupportActivity)
            addItemDecoration(DividerItemDecoration(context))
        }


    }

    override fun onClick(view : View?) {
        when(view?.id){
            R.id.layoutMain->{
                val id = view.getTag(R.string.tag_id)
                when(id){
                    R.id.support_settings->{
                        startActivity(Intent(this, SupportSettingsActivity::class.java))
                    }
                    R.id.help->{
                        startActivity(Intent(this, HelpActivity::class.java))
                    }
                }
            }
        }
    }
}


class SupportAdapter(val context: Context, val clickListener: View.OnClickListener) : RecyclerView.Adapter<SupportViewHolder>() {

    val menus : ArrayList<Menu> = ArrayList()

    init {
        val titleSettings = context.resources.getString(R.string.title_settings)
        val subTitleSettings = context.resources.getString(R.string.sub_title_settings_display)
        menus.add(Menu(R.id.support_settings,titleSettings,subTitleSettings))

        val titleHelp = context.resources.getString(R.string.title_help)
        val subTitleHelp = context.resources.getString(R.string.sub_title_help)
        menus.add(Menu(R.id.help, titleHelp, subTitleHelp))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_support,parent,false)
        return SupportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    override fun onBindViewHolder(holder: SupportViewHolder, position: Int) {
        var menu : Menu = menus.get(position)
        holder.itemView.findViewById<TextView>(R.id.textViewTitle).text = menu.title
        holder.itemView.findViewById<TextView>(R.id.textViewSubTitle).text = menu.subTitle
        holder.itemView.setTag(R.string.tag_id,menu.id)
        holder.itemView.setOnClickListener(clickListener)
    }
}

class SupportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)