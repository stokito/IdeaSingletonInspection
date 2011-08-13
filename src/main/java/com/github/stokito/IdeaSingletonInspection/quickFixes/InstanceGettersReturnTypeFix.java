package com.github.stokito.IdeaSingletonInspection.quickFixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTypeElement;
import org.jetbrains.annotations.NotNull;

public class InstanceGettersReturnTypeFix implements LocalQuickFix {

  @NotNull
  @Override
  public String getName() {
    return "Set correct return type";
  }

  @Override
  public void applyFix(@NotNull final Project project, @NotNull final ProblemDescriptor descriptor) {
    @NotNull final PsiMethod instanceGetter = (PsiMethod)descriptor.getPsiElement();
    assert instanceGetter.getParent() instanceof PsiClass;
    @NotNull final PsiClass instanceGetterClass = (PsiClass)instanceGetter.getParent();
    assert instanceGetterClass.getNameIdentifier() != null;
    final PsiIdentifier nameIdentifier = instanceGetterClass.getNameIdentifier();
    assert nameIdentifier != null;
    final PsiTypeElement returnTypeElement = instanceGetter.getReturnTypeElement();
    assert returnTypeElement != null;
    returnTypeElement.replace(nameIdentifier.copy());
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return getName();
  }

}
