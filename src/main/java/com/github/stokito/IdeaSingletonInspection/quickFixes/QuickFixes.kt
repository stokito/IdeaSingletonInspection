package com.github.stokito.IdeaSingletonInspection.quickFixes

object QuickFixes {
    val INSTANCE_GETTERS_MODIFIERS = InstanceGettersModifiersFix()
    val INSTANCE_GETTERS_RETURN_TYPE = InstanceGettersReturnTypeFix()
    val CONSTRUCTOR_MODIFIERS = ConstructorModifiersFix()
    val CREATE_CONSTRUCTOR = CreateConstructorFix()
    val SET_CLASS_FINAL = SetClassFinalFix()
}
