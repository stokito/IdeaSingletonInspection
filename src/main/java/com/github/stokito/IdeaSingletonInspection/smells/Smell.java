package com.github.stokito.IdeaSingletonInspection.smells;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

public abstract class Smell {

  private ProblemsHolder holder;

  ProblemsHolder getHolder() {
    return this.holder;
  }

  public void setHolder(final ProblemsHolder holder) {
    this.holder = holder;
  }

  public abstract void check(@NotNull final PsiClass aClass);

}
