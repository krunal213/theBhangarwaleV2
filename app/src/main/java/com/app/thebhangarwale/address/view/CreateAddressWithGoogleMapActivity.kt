package com.app.thebhangarwale.address.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.BounceInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import com.app.thebhangarwale.R
import com.app.thebhangarwale.RequestCode.REQUEST_MY_LOCATION
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.app.thebhangarwale.custom.adapter.MotionLayoutTransitionAdapter
import com.app.thebhangarwale.custom.view.ProgressBarDialog
import com.app.thebhangarwale.setVisibilityForMotionLayout
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.rodolfonavalon.shaperipplelibrary.ShapeRipple
import com.rodolfonavalon.shaperipplelibrary.model.Circle
import permissions.dispatcher.*

@RuntimePermissions
class CreateAddressWithGoogleMapActivity : BhangarwaleConfigAndControllerActivity() {

    private val locationRequest: LocationRequest by lazy {
        LocationRequest.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR )

        setContentView(R.layout.activity_create_address_with_google_map)

        findViewById<ShapeRipple>(R.id.ripple).apply {
            rippleDuration = 5757
            rippleCount = 1
            rippleMaximumRadius = 350f
            isEnableSingleRipple = true
            isEnableColorTransition = true
            setRippleColor(resources.getColor(R.color.color_map_ripple))
            rippleShape = Circle()
        }

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar_view))
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        initLocationWithPermissionCheck()
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationDenied() {
        findViewById<MaterialToolbar>(R.id.toolbar_view)
            .apply {
                inflateMenu(R.menu.menu_after_location_permission_denied)
            }

        TapTargetView.showFor(this,
            TapTarget.forToolbarOverflow(findViewById<MaterialToolbar>(R.id.toolbar_view),
                resources.getString(R.string.string_manage_address),
                resources.getString(R.string.string_manage_address_description))
                .outerCircleColor(R.color.color_background_for_hint_view)
                .outerCircleAlpha(0.96f)
                .descriptionTextSize(14)
                .textColor(android.R.color.white)
                .textTypeface(ResourcesCompat.getFont(this, R.font.jet_brains_mono_regular))
                .drawShadow(true)
                .cancelable(true)
                .transparentTarget(true)
                .targetRadius(30),
        object : TapTargetView.Listener() {
            override fun onTargetClick(view: TapTargetView?) {
                super.onTargetClick(view)
                findViewById<MaterialToolbar>(R.id.toolbar_view)
                    .showOverflowMenu()
            }
        })

        Snackbar
            .make(findViewById<ShapeRipple>(R.id.ripple), resources.getString(R.string.string_location_denied), Snackbar.LENGTH_LONG)
            .setDuration(LENGTH_INDEFINITE)
            .show()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_create_address ->{
                startActivity(Intent(this, CreateAddressActivity::class.java))
                finish()
            }
            R.id.action_system_settings ->{
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                })
                finish()
            }
        }
        return true
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun initLocation() {
        locationRequest.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            LocationSettingsRequest.Builder()
                .addLocationRequest(this)
                .setAlwaysShow(true)
                .build()
                .apply {
                    LocationServices
                        .getSettingsClient(this@CreateAddressWithGoogleMapActivity)
                        .checkLocationSettings(this)
                        .addOnSuccessListener {
                            setLocationOnMap()
                        }
                        .addOnFailureListener {
                            if (it is ResolvableApiException) {
                                try {
                                    it.startResolutionForResult(
                                        this@CreateAddressWithGoogleMapActivity,
                                        REQUEST_MY_LOCATION
                                    )
                                } catch (sendEx: IntentSender.SendIntentException) {
                                }
                            }
                        }
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_MY_LOCATION -> {
                when (resultCode) {
                    RESULT_OK -> {
                        setLocationOnMap()
                    }
                    RESULT_CANCELED -> {
                        onLocationDenied()
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setLocationOnMap() {
        LocationServices
            .getFusedLocationProviderClient(this@CreateAddressWithGoogleMapActivity)
            .also { fusedLocationProviderClient ->
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            also { locationCallback ->
                                locationResult.locations[0]?.apply {
                                    with(supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment) {
                                        getMapAsync { mMap ->
                                            mMap?.apply {
                                                isMyLocationEnabled = true
                                                uiSettings?.isMyLocationButtonEnabled = false
                                                uiSettings?.isCompassEnabled = false
                                            }
                                            mMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(this@CreateAddressWithGoogleMapActivity,
                                                R.raw.style_json
                                            ))
                                            val cameraPosition: CameraPosition =
                                                CameraPosition.builder()
                                                    .target(LatLng(latitude, longitude))
                                                    .zoom(17f)
                                                    .build()
                                            mMap?.animateCamera(
                                                CameraUpdateFactory.newCameraPosition(
                                                    cameraPosition
                                                )
                                            )
                                            findViewById<MotionLayout>(R.id.motionLayout).apply {
                                                transitionToEnd()
                                                addTransitionListener(object :
                                                    MotionLayoutTransitionAdapter() {
                                                    override fun onTransitionCompleted(
                                                        motionLayout: MotionLayout?,
                                                        currentId: Int
                                                    ) {
                                                        findViewById<AppCompatImageView>(R.id.imageViewPin).apply {
                                                            setVisibilityForMotionLayout(View.VISIBLE)
                                                            animate().setInterpolator(
                                                                BounceInterpolator()
                                                            ).setDuration(1000).translationYBy(
                                                                    resources.getDimensionPixelSize(
                                                                        R.dimen.margin_for_map_marker
                                                                    ).toFloat()
                                                                ).start()
                                                        }
                                                        findViewById<ShapeRipple>(R.id.ripple).apply {
                                                            setVisibilityForMotionLayout(View.VISIBLE)
                                                        }
                                                    }
                                                })
                                            }
                                        }
                                    }
                                    fusedLocationProviderClient.removeLocationUpdates(
                                        locationCallback
                                    )
                                }
                            }
                        }
                    },
                    null
                )
            }
    }
}