package com.example.custom_plus_2_compose_views.banner.data

import com.example.custom_plus_2_compose_views.banner.callback.BannerClickCallback
import com.example.custom_plus_2_compose_views.banner.callback.BannerCloseCallback

public interface Banner {
    public val colorStart: String
    public val colorEnd: String
    public val title: String
    public val description: String
    public val icon: String
    public val tariffId: String
    public val bannerClickCallback: BannerClickCallback
    public val bannerCloseCallback: BannerCloseCallback
}