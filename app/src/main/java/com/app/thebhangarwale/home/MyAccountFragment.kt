package com.app.thebhangarwale.home

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.*
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat


class MyAccountFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.findViewById<ImageView>(R.id.img).apply {
            Glide.with(context)
                .load(R.drawable.ic_ashish)
                .circleCrop()
                .into(this)
        }
        view?.findViewById<RecyclerView>(R.id.rv).apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            adapter = MyAccountAdapter(requireContext(),this@MyAccountFragment)
            addItemDecoration(MyAccountItemDecoration(requireContext()))
        }
        view?.findViewById<ConstraintLayout>(R.id.item_profile)?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.layoutMain->{
                val rv : RecyclerView? = view?.findViewById<RecyclerView>(R.id.rv)
                var position = v?.let { rv?.getChildAdapterPosition(it) }
                var myAccountAdapter : MyAccountAdapter? = rv?.adapter as MyAccountAdapter?
                if (position != null) {
                    var menu : Menu? = myAccountAdapter?.menus?.get(position)
                    when(menu?.id){
                        R.id.account->{
                            startActivity(Intent(requireActivity(), AccountActivity::class.java))
                        }
                        R.id.settings->{
                            startActivity(Intent(requireActivity(), SettingsActivity::class.java))
                        }
                        R.id.help->{
                            startActivity(Intent(requireActivity(), HelpActivity::class.java))
                        }
                    }

                }
            }
            R.id.item_profile->{
                val options = ViewCompat.getTransitionName(requireActivity().findViewById<ImageView>(R.id.img))?.let {
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        requireActivity(),
                        requireActivity().findViewById<ImageView>(R.id.img),
                        it
                    )
                }
                startActivity(Intent(requireActivity(), ProfileActivity::class.java),options?.toBundle())
            }

        }
    }

}

class MyAccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

data class Menu(val id : Int,val icon : Int,val title : String,val subTitle : String)

class MyAccountAdapter(val context: Context, val clickListener: View.OnClickListener) : RecyclerView.Adapter<MyAccountViewHolder>() {

    val menus : ArrayList<Menu> = ArrayList()

    init {
        var icon = R.drawable.ic_account
        var title = context.resources.getString(R.string.title_account)
        var subTitle = context.resources.getString(R.string.sub_title_account)
        menus.add(Menu(R.id.account,icon,title, subTitle))

        icon = R.drawable.ic_settings
        title = context.resources.getString(R.string.title_settings)
        subTitle = context.resources.getString(R.string.sub_title_settings)
        menus.add(Menu(R.id.settings,icon,title, subTitle))

        icon = R.drawable.ic_help_v2
        title = context.resources.getString(R.string.title_help)
        subTitle = context.resources.getString(R.string.sub_title_help)
        menus.add(Menu(R.id.help,icon,title, subTitle))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_my_account,parent,false)
        return MyAccountViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    override fun onBindViewHolder(holder: MyAccountViewHolder, position: Int) {
        var menu : Menu = menus[position]
        holder.itemView.findViewById<AppCompatImageView>(R.id.icon).setImageResource(menu.icon)
        holder.itemView.findViewById<TextView>(R.id.title).text = menu.title
        holder.itemView.findViewById<TextView>(R.id.subtitle).text = menu.subTitle
        holder.itemView.setOnClickListener(clickListener)
    }
}

class MyAccountItemDecoration(mContext: Context) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable? by lazy {
        ContextCompat.getDrawable(mContext, R.drawable.drawable_horizonatal_divider)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        mDivider?.apply {
            val childMainView = parent.getChildAt(0).findViewById<View>(R.id.layoutMain)
            val linearLayoutText  = childMainView.findViewById<View>(R.id.title)
            val dividerLeft = linearLayoutText.left
            val dividerRight = childMainView.right
            val childCount = parent.childCount
            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val dividerTop = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + intrinsicHeight
                setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                draw(c)
            }
        }
    }

}
