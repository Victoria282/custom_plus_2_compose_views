package com.example.custom_plus_2_compose_views.personal_card.data

import com.example.custom_plus_2_compose_views.personal_card.callbacks.ChangeContractCallback
import com.example.custom_plus_2_compose_views.personal_card.callbacks.ClickStatusCallback
import com.example.custom_plus_2_compose_views.personal_card.callbacks.CopyAccountNumberCallback

public interface PersonalCardViewData {
    public var changeContractCallback: ChangeContractCallback?
    public var clickStatusCallback: ClickStatusCallback?
    public var copyAccountNumberCallback: CopyAccountNumberCallback?
    public val hasPersonalization: Boolean
    public val personalAccount: String
    public val address: String
    public val color: Int
    public val icon: Int
    public val text: String
}