package com.github.stokito.IdeaSingletonInspection.quickFixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifier.PUBLIC
import com.intellij.psi.PsiModifier.STATIC

class InstanceGettersModifiersFix : LocalQuickFix {
    override fun getName(): String {
        return "Set public and static modifiers"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val instanceGetter = descriptor.psiElement as PsiMethod
        instanceGetter.modifierList.setModifierProperty(PUBLIC, true)
        instanceGetter.modifierList.setModifierProperty(STATIC, true)
    }

    override fun getFamilyName(): String {
        return name
    }
}
