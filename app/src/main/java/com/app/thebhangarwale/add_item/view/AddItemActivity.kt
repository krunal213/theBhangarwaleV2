package com.app.thebhangarwale.add_item.view

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.*
import com.app.thebhangarwale.RequestCode.REQUEST_IMAGE_GET_FROM_GALLERY
import com.app.thebhangarwale.add_item.entity.Media
import com.app.thebhangarwale.add_item.viewmodel.MultimediaViewModel
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.app.thebhangarwale.custom.activity_result.BhangarwaleTakePicture
import com.app.thebhangarwale.custom.activity_result.BhangarwaleTakeVideo
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.view.ProgressBarDialog
import com.app.thebhangarwale.home.create_request.viewmodel.RequestViewModel
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddItemActivity : BhangarwaleConfigAndControllerActivity(), View.OnClickListener {

    private val multimediaViewModel by lazy {
        MultimediaViewModel(application)
    }

    private val requestViewModel by lazy {
         RequestViewModel(application)
    }

    private val progressBarDialog by lazy {
        ProgressBarDialog(this).show()
    }

    private val imageLauncher =
        registerForActivityResult(BhangarwaleTakePicture()) { imageResponse ->
            when (imageResponse.result) {
                RESULT_OK -> {
                    findViewById<RecyclerView>(R.id.recyclerviewImages).apply {
                        val addItemAdapter: AddItemAdapter? = adapter as AddItemAdapter?
                        addItemAdapter?.notifyDataSetChanged(Media.Image<Uri>(data = imageResponse.uri as Uri))
                    }
                }
            }
        }

    private val videoLauncher = registerForActivityResult(BhangarwaleTakeVideo()) { videoResponse ->
        when (videoResponse.result) {
            RESULT_OK -> {
                findViewById<RecyclerView>(R.id.recyclerviewImages).apply {
                    val addItemAdapter: AddItemAdapter? = adapter as AddItemAdapter?
                    addItemAdapter?.notifyDataSetChanged(Media.Video<Uri>(data = videoResponse.uri as Uri))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.title = resources.getString(R.string.title_add_item)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        findViewById<RecyclerView>(R.id.recyclerviewImages).apply {
            layoutManager =
                GridLayoutManager(this@AddItemActivity, 3, GridLayoutManager.VERTICAL, false)
            addItemDecoration(AddItemDecoration())
            adapter = AddItemAdapter(this@AddItemActivity)
        }

        findViewById<MaterialButton>(R.id.button_submit)
            .setOnClickListener(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_take_photo_or_video_from_gallery -> {
                //selectPhotoFromGallery()
                return true
            }
            R.id.action_take_photo_from_camera -> {
                selectPhotoFromCamera()
                return true
            }
            R.id.action_take_video_from_camera -> {
                selectVideoFromCamera()
                return true
            }
        }
        return false
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        upIntent?.putExtra(REDIRECT_SCREEN, R.id.navigation_create_request)
        startActivity(upIntent)
        finish()
        return true
    }

    fun selectPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = MIME_TYPE_FOR_IMAGE + ";" + MIME_TYPE_FOR_VIDEO
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET_FROM_GALLERY)
        }
    }

    fun selectPhotoFromCamera() {
        imageLauncher.launch(multimediaViewModel.photoUri)
    }

    fun selectVideoFromCamera() {
        videoLauncher.launch(multimediaViewModel.videoUri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQUEST_IMAGE_GET_FROM_GALLERY -> {
                        /*&val fullPhotoUri: Uri? = data?.data
                        findViewById<RecyclerView>(R.id.recyclerviewImages).apply {
                            val addItemAdapter: AddItemAdapter? = adapter as AddItemAdapter?
                            addItemAdapter?.notifyDataSetChanged(fullPhotoUri)
                        }*/
                    }
                }

            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewForItems -> {
                when (v.getTag(R.string.tag_image_uri)) {
                    is Media.Image<Uri> -> {
                        val intent: Intent = Intent(this, ImageDetailScreenActivity::class.java)
                        val imageObject = v.getTag(R.string.tag_image_uri) as Media.Image<Uri>;
                        intent.data = imageObject.data
                        val options = ViewCompat.getTransitionName(v)?.let {
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                this,
                                v as View,
                                it
                            )
                        }
                        startActivity(intent, options?.toBundle())
                    }
                    is Media.Video<Uri> -> {
                        val intent: Intent = Intent(this, VideoDetailScreenActivity::class.java)
                        val imageObject = v.getTag(R.string.tag_image_uri) as Media.Video<Uri>;
                        intent.data = imageObject.data
                        val options = ViewCompat.getTransitionName(v)?.let {
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                this,
                                v as View,
                                it
                            )
                        }
                        startActivity(intent, options?.toBundle())
                    }
                }
            }
            R.id.button_submit->{
                requestViewModel.addItem().observe(this,Observer{
                    when(it){
                        is BhangarwaleResult.Loading->{
                            progressBarDialog.show()
                        }
                        is BhangarwaleResult.Success->{
                            progressBarDialog.dismiss()
                            Toast
                                .makeText(this,it.data,Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                })
            }
        }
    }

}

class AddItemDecoration : RecyclerView.ItemDecoration() {

    var spanCount = 3
    var spacing = 16

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
        outRect.right =
            spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
    }
}

class AddItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class AddItemAdapter(val onClickListener: View.OnClickListener?) :
    RecyclerView.Adapter<AddItemViewHolder>() {

    val imageList: ArrayList<Media<Uri>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_add_item, parent, false)
        return AddItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        val size = imageList.size
        if (size < 3) {
            return size
        } else {
            return 3
        }
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        val content: Media<Uri>? = imageList.get(position)
        when (content) {
            is Media.Video -> {
                holder.itemView.findViewById<AppCompatImageView>(R.id.imageViewForItems).apply {
                    Glide.with(getContext())
                        .load(content.data)
                        .thumbnail(Glide.with(this.context).load(content.data))
                        .into(this)
                    setTag(R.string.tag_image_uri, content)
                    ViewCompat.setTransitionName(holder.itemView, "imageViewForItems")
                    setOnClickListener(onClickListener)
                }
            }
            is Media.Image -> {
                holder.itemView.findViewById<AppCompatImageView>(R.id.imageViewForItems).apply {
                    Glide.with(getContext())
                        .load(content.data)
                        .thumbnail(Glide.with(this.context).load(content.data))
                        .into(this)
                    setTag(R.string.tag_image_uri, content)
                    ViewCompat.setTransitionName(holder.itemView, "videoViewForItems")
                    setOnClickListener(onClickListener)
                }
            }
        }

    }

    fun notifyDataSetChanged(content: Media<Uri>) {
        if (imageList.size <= 3) {
            imageList.add(content)
            notifyDataSetChanged()
        }
    }
}

