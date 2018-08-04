package com.github.stokito.IdeaSingletonInspection.quickFixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.PsiModifier.PRIVATE

class CreateConstructorFix : LocalQuickFix {
    override fun getName(): String {
        return "Create private constructor"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val aClass = descriptor.psiElement as PsiClass
        val factory = JavaPsiFacade.getInstance(project).elementFactory
        val constructor = factory.createConstructor()
        val nameIdentifier = aClass.nameIdentifier!!
        constructor.name = nameIdentifier.text
        constructor.modifierList.setModifierProperty(PRIVATE, true)
        aClass.add(constructor)
    }

    override fun getFamilyName(): String {
        return name
    }
}
