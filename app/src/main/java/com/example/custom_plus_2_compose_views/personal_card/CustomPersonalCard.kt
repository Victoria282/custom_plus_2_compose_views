package com.example.custom_plus_2_compose_views.personal_card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.intersvyaz.extensions.setGone
import com.intersvyaz.extensions.setVisibility
import com.intersvyaz.extensions.setVisible
import com.example.custom_plus_2_compose_views.personal_card.callbacks.ChangeContractCallback
import com.example.custom_plus_2_compose_views.personal_card.callbacks.ClickStatusCallback
import com.example.custom_plus_2_compose_views.personal_card.callbacks.CopyAccountNumberCallback
import com.example.custom_plus_2_compose_views.personal_card.data.PersonalCardViewData
import net.intersvyaz.library.core_common_ui.R
import net.intersvyaz.library.core_common_ui.databinding.CustomPersonalCardBinding

public class CustomPersonalCard @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null
) : ConstraintLayout(ctx, attrs) {

    private val binding: CustomPersonalCardBinding = CustomPersonalCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private var changeContractCallback: ChangeContractCallback? = null

    private var clickStatusCallback: ClickStatusCallback? = null

    private var copyAccountNumberCallback: CopyAccountNumberCallback? = null

    private var hasPersonalization: Boolean = false

    public fun setPersonalCard(data: PersonalCardViewData) {
        hasPersonalization = data.hasPersonalization
        changeContractCallback = data.changeContractCallback
        clickStatusCallback = data.clickStatusCallback
        copyAccountNumberCallback = data.copyAccountNumberCallback
        binding.addressLabel.text = data.address
        binding.personalAccountLabel.text =
            context.resources.getString(R.string.personal_account_label, data.personalAccount)
        setStatusText(data.text, data.color)
        setChangeContractCallback()
        setClickStatusCallback()
        setCopyAccountCallback()
        setStatusIcon(data.icon)
    }

    public fun startShimmer(): Unit = with(binding) {
        shimmerHolder.startShimmer()
        shimmerHolder.setVisible()
        contractGroup.setGone()
        cardGroup.setGone()
    }

    public fun stopShimmer(): Unit = with(binding) {
        shimmerHolder.stopShimmer()
        shimmerHolder.setGone()
        cardGroup.setVisible()
        contractGroup.setVisibility(hasPersonalization)
    }

    private fun setChangeContractCallback() = with(binding) {
        contractArea.setOnClickListener { changeContractCallback?.onChange() }
    }

    private fun setClickStatusCallback() = with(binding) {
        statusArea.setOnClickListener { clickStatusCallback?.onClick() }
        personalCardStatusIcon.setOnClickListener { clickStatusCallback?.onClick() }
    }

    private fun setCopyAccountCallback() = with(binding) {
        val accountNumber = binding.personalAccountLabel.text.filter(Char::isDigit)
        personalAccountLabel.setOnClickListener { copyAccountNumberCallback?.copy(accountNumber.toString()) }
    }

    private fun setStatusIcon(iconId: Int) =
        Glide.with(context).load(iconId).into(binding.personalCardStatusIcon)

    private fun setStatusText(text: String, colorId: Int) = with(binding) {
        val color = ContextCompat.getColor(context, colorId)
        networkStatusText.text = text
        networkStatusText.setTextColor(color)
    }
}