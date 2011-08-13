package com.github.stokito.IdeaSingletonInspection.quickFixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

public class SetClassFinalFix implements LocalQuickFix {

  @NotNull
  @Override
  public String getName() {
    return "Set class final";
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return getName();
  }

  @Override
  public void applyFix(@NotNull final Project project, @NotNull final ProblemDescriptor descriptor) {
    @NotNull final PsiClass aClass = (PsiClass)descriptor.getPsiElement();
    final PsiModifierList modifierList = aClass.getModifierList();
    assert modifierList != null;
    modifierList.setModifierProperty("final", true);
  }

}
