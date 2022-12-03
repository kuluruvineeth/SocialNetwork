package com.kuluruvineeth.socialnetwork.feature_profile.presentation.edit_profile

import android.net.Uri
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.Skill

sealed class EditProfileEvent{
    data class EnteredUsername(val value: String): EditProfileEvent()
    data class EnteredGithubUrl(val value: String): EditProfileEvent()
    data class EnteredInstagramUrl(val value: String): EditProfileEvent()
    data class EnteredLinkedInUrl(val value: String): EditProfileEvent()
    data class EnteredBio(val value: String): EditProfileEvent()

    data class CropProfilePicture(val uri: Uri?): EditProfileEvent()
    data class CropBannerImage(val uri: Uri?): EditProfileEvent()

    data class SetSkillsSelected(val skill: Skill): EditProfileEvent()

    object UpdateProfile: EditProfileEvent()
}
