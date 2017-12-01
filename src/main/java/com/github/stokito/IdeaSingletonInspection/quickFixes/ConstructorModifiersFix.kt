package com.github.stokito.IdeaSingletonInspection.quickFixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiMethod

class ConstructorModifiersFix : LocalQuickFix {
    override fun getName(): String {
        return "Set private modifier to constructor"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val constructor = descriptor.psiElement as PsiMethod
        constructor.modifierList.setModifierProperty("private", true)
    }

    override fun getFamilyName(): String {
        return name
    }
}
