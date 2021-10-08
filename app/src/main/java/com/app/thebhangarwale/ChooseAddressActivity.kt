package com.app.thebhangarwale

import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.view.*
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.collections.ArrayList

class ChooseAddressActivity : BhangarwaleConfigAndControllerActivity(), View.OnLongClickListener, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_address)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.title_select_address)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        //18.98307494631155,72.83906176686287
        val myaddress = ArrayList<Address>()
        val address = Address(Locale.ENGLISH)
        address.setAddressLine(0,"4D, Harish Arjun Palay Marg, New Patra Chawl, Dhaku Prabhuchi Wadi, Byculla East, Byculla, Mumbai, Maharashtra 400033, India")
        address.latitude = 18.98307494631155
        address.longitude = 72.83906176686287

        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)
        myaddress.add(address)

        val recyclerviewAddress : RecyclerView = findViewById<RecyclerView>(R.id.recyclerviewAddress)
        recyclerviewAddress.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = ChooseAddressAdapter(myaddress,this@ChooseAddressActivity,this@ChooseAddressActivity)
            addItemDecoration(DividerItemDecoration(context))
        }

        findViewById<MaterialButton>(R.id.buttonSaveAddress).setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_address,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_address->{
                val intentAddAddress = Intent(this,AddAddressActivity::class.java)
                startActivity(intentAddAddress)
            }
        }
        return true
    }

    /*private val contextualMenuCallBack = object : ActionMode.Callback {
        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.context_menu_address, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            /*return when (item.itemId) {
                R.id.menu_share -> {
                    shareCurrentItem()
                    mode.finish() // Action picked, so close the CAB
                    true
                }
                else -> false
            }*/

            return false
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            //mode = null
        }
    }*/

    override fun onLongClick(v: View?): Boolean {
        when(v?.id){
            /*R.id.constraintLayoutAddress->{
                startActionMode(contextualMenuCallBack)
            }*/
        }
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imageViewMenu->{
                /*val popup = PopupMenu(this, v)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.pop_up_menu_my_address, popup.menu)
                popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when(item?.itemId){
                            R.id.action_google_map->{
                                val address : Address? = v.getTag(R.string.tag_address) as Address?
                                val intentAddress = Intent(this@MyAddressActivity,AddAddressActivity::class.java)
                                intentAddress.putExtra(EDIT_AVAILABLE_ADDRESS,address)
                                this@MyAddressActivity.startActivity(intentAddress)
                            }

                            R.id.action_edit->{
                                val address : Address? = v.getTag(R.string.tag_address) as Address?
                                val intentUpdateAddress = Intent(this@MyAddressActivity,UpdateAddressActivity::class.java)
                                intentUpdateAddress.putExtra(EDIT_AVAILABLE_ADDRESS,address)
                                this@MyAddressActivity.startActivity(intentUpdateAddress)
                            }
                        }
                        return true
                    }

                })
                popup.show()*/
            }
            R.id.buttonSaveAddress->{
                val intentAddAddress = Intent(this,ConfirmationActivity::class.java)
                startActivity(intentAddAddress)
            }
        }

    }

    class ChooseAddressAdapter(
        val myaddress: ArrayList<Address>,
        val longClickListener: View.OnLongClickListener?,
        val onClickListener: View.OnClickListener?) : RecyclerView.Adapter<ChooseAddressViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseAddressViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_choose_address,parent,false)
            return ChooseAddressViewHolder(view)
        }

        override fun getItemCount(): Int {
            return myaddress.size
        }

        override fun onBindViewHolder(holder: ChooseAddressViewHolder, position: Int) {
            val address =  myaddress.get(position)
            holder.itemView.findViewById<TextView>(R.id.textViewAddress).text = address.getAddressLine(0)
            holder.itemView.setOnLongClickListener(longClickListener)
            //holder.itemView.imageViewMenu.setOnClickListener(onClickListener)
            //holder.itemView.imageViewMenu.setTag(R.string.tag_address,address)
            /*TooltipCompat.setTooltipText(holder.itemView.textViewAddress,
                myaddress.get(position))*/
        }
    }

    class ChooseAddressViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)


}