package com.github.stokito.IdeaSingletonInspection.smells;

import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

import static com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes.CREATE_CONSTRUCTOR;

public class ConstructorIsNotExists extends Smell {

  @Override
  public void check(@NotNull final PsiClass aClass) {
    if (aClass.getConstructors().length == 0) {
      getHolder().registerProblem(aClass, "Class should have private constructor", CREATE_CONSTRUCTOR);
    }
  }

}