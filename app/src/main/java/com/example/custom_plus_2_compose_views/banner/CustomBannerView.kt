package com.example.custom_plus_2_compose_views.banner

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.custom_plus_2_compose_views.banner.callback.BannerClickCallback
import com.example.custom_plus_2_compose_views.banner.callback.BannerCloseCallback
import com.example.custom_plus_2_compose_views.banner.data.Banner
import net.intersvyaz.library.core_common_ui.databinding.BannerLayoutBinding

public class CustomBannerView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null
) : ConstraintLayout(ctx, attrs) {

    private val binding: BannerLayoutBinding = BannerLayoutBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private var bannerClickCallback: BannerClickCallback? = null

    private var bannerCloseCallback: BannerCloseCallback? = null

    public fun setBanner(banner: Banner): Unit = with(binding) {
        bannerClickCallback = banner.bannerClickCallback
        bannerCloseCallback = banner.bannerCloseCallback
        setGradient(banner.colorStart, banner.colorEnd)
        bannerDescription.text = banner.description
        bannerMoreText.setOnClickListener {
            bannerClickCallback?.onClick(banner.tariffId)
        }
        closeBannerButton.setOnClickListener {
            bannerCloseCallback?.onClose()
        }
        bannerTittle.text = banner.title
        setImage(banner.icon)
    }

    private fun setImage(icon: String) =
        Glide.with(context).load(icon).into(binding.bannerImageView)

    private fun setGradient(
        startColor: String, endColor: String
    ) {
        val gradient = generateGradientDrawable(startColor, endColor)
        binding.bannerCard.background = gradient
    }

    private fun generateGradientDrawable(
        startColor: String, endColor: String
    ): GradientDrawable {
        return GradientDrawable().apply {
            colors = intArrayOf(
                Color.parseColor(startColor),
                Color.parseColor(endColor),
            )
            orientation = GradientDrawable.Orientation.TOP_BOTTOM
            gradientType = GradientDrawable.LINEAR_GRADIENT
            cornerRadius = CUSTOM_BANNER_VIEW_CORNER_RADIUS
            shape = GradientDrawable.RECTANGLE
        }
    }

    private companion object {
        private const val CUSTOM_BANNER_VIEW_CORNER_RADIUS = 36F
    }
}