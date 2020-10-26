package com.ros.hiringapkforengineer.home.engineer

import com.ros.hiringapkforengineer.home.skill.SkillModel

data class EngineerModel (val id: String,
                          val nameEngineer: String,
                          val nameFreelance: String,
                          val image: String,
                          val skill: List<SkillModel>
)