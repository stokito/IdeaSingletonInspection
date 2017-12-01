package com.github.stokito.IdeaSingletonInspection.quickFixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiMethod

class InstanceGettersModifiersFix : LocalQuickFix {
    override fun getName(): String {
        return "Set public and static modifiers"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val instanceGetter = descriptor.psiElement as PsiMethod
        instanceGetter.modifierList.setModifierProperty("public", true)
        instanceGetter.modifierList.setModifierProperty("static", true)
    }

    override fun getFamilyName(): String {
        return name
    }
}
