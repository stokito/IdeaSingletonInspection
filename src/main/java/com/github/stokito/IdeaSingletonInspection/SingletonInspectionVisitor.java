package com.github.stokito.IdeaSingletonInspection;

import com.github.stokito.IdeaSingletonInspection.smells.*;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class SingletonInspectionVisitor extends JavaElementVisitor {

  private static final List<Smell> smells = new ArrayList<Smell>();
  private final boolean checkFinal;

  static {
    smells.add(new ClassFinalModifier());
    smells.add(new InstanceGetters());
    smells.add(new ConstructorIsNotExists());
    smells.add(new ConstructorsArePrivate());
  }

  public SingletonInspectionVisitor(@NotNull final ProblemsHolder holder, final boolean checkFinal) {
    super();
    this.checkFinal = checkFinal;
    for (final Smell smell : smells) {
      smell.setHolder(holder);
    }
  }

  @Override
  public void visitClass(final PsiClass aClass) {
    super.visitClass(aClass);
    if (PsiTreeUtils.isNotUsualClass(aClass) || !PsiTreeUtils.classIsSingleton(aClass)) {
      return;
    }
    for (final Smell smell : smells) {
      if (!checkFinal && smell instanceof ClassFinalModifier) {
         continue;
      }
      smell.check(aClass);
    }
  }

}
