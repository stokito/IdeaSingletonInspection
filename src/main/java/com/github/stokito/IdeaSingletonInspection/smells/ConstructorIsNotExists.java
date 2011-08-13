package com.github.stokito.IdeaSingletonInspection.smells;

import com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

public class ConstructorIsNotExists extends Smell {

  @Override
  public void check(@NotNull final PsiClass aClass) {
    if (aClass.getConstructors().length == 0) {
      getHolder().registerProblem(aClass, "Class should have private constructor", QuickFixes.CREATE_CONSTRUCTOR);
    }
  }

}