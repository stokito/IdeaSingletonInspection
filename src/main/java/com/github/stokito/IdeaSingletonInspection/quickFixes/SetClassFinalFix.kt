package com.github.stokito.IdeaSingletonInspection.quickFixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiModifierList

class SetClassFinalFix : LocalQuickFix {
    override fun getName(): String {
        return "Set class final"
    }

    override fun getFamilyName(): String {
        return name
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val aClass = descriptor.psiElement as PsiClass
        val modifierList = aClass.modifierList!!
        modifierList.setModifierProperty("final", true)
    }
}
