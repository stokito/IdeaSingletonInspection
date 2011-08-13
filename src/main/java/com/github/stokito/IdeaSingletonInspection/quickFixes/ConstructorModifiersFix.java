package com.github.stokito.IdeaSingletonInspection.quickFixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

public class ConstructorModifiersFix implements LocalQuickFix {

  @NotNull
  @Override
  public String getName() {
    return "Set private modifier to constructor";
  }


  @Override
  public void applyFix(@NotNull final Project project, @NotNull final ProblemDescriptor descriptor) {
    @NotNull final PsiMethod constructor = (PsiMethod)descriptor.getPsiElement();
    constructor.getModifierList().setModifierProperty("private", true);
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return getName();
  }

}
