package com.kuluruvineeth.socialnetwork.feature_profile.domain.use_case

import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.UiText
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.Skill
import com.kuluruvineeth.socialnetwork.feature_profile.domain.util.ProfileConstants

class SetSkillSelectedUseCase {

    operator fun invoke(
        selectedSkills: List<Skill>,
        skillToToggle: Skill
    ): Resource<List<Skill>>{
        val skillInList = selectedSkills.find { it.name == skillToToggle.name  }
        if(skillInList != null){
            return Resource.Success(selectedSkills - skillInList)
        }
        return if(selectedSkills.size >= ProfileConstants.MAX_SELECTED_SKILL_COUNT){
            Resource.Error(uiText = UiText.StringResource(R.string.error_max_skills_selected))
        }else{
            Resource.Success(selectedSkills + skillToToggle)
        }
    }
}