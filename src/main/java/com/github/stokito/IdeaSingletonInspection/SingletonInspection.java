package com.github.stokito.IdeaSingletonInspection;

import com.intellij.codeInsight.daemon.GroupNames;
import com.intellij.codeInspection.BaseJavaLocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

/**
 * User: stokito
 * Date: 3/21/11
 * Time: 21:30 PM
 */
public class SingletonInspection extends BaseJavaLocalInspectionTool {
    // Quick fixes
    private final InstanceGettersModifiersFix instanceGettersModifiersFix = new InstanceGettersModifiersFix();
    private final ConstructorModifiersFix constructorModifiersFix = new ConstructorModifiersFix();
    private final InstanceGettersReturnTypeFix instanceGettersReturnTypeFix = new InstanceGettersReturnTypeFix();
    private final CreateConstructorFix createConstructorFix = new CreateConstructorFix();
    private final SetClassFinalFix setClassFinalFix = new SetClassFinalFix();

    @NotNull
    @Override
    public String getDisplayName() {
        return "Singleton inspection";
    }

    @NotNull
    @Override
    public String getGroupDisplayName() {
        return GroupNames.BUGS_GROUP_NAME;
    }

    @NotNull
    @Override
    public String getShortName() {
        return "Singleton";
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
            }

            @Override
            public void visitClass(PsiClass aClass) {
                super.visitClass(aClass);
                if (isNotUsualClass(aClass) || !classIsSingleton(aClass)) {
                    return;
                }
                checkInstanceGetters(aClass, holder);
                checkClassFinalModifier(aClass, holder);
                checkConstructorExistence(aClass, holder);
                checkConstructorsIsPrivate(aClass, holder);
            }
        };
    }

    private void checkConstructorsIsPrivate(@NotNull final PsiClass aClass, @NotNull final ProblemsHolder holder) {
        @NotNull final PsiMethod[] constructors = aClass.getConstructors();
        for (PsiMethod constructor : constructors) {
            @NotNull final PsiModifierList modifiers = constructor.getModifierList();
            if (!modifiers.hasModifierProperty("private")) {
                holder.registerProblem(constructor, "Constructor must be private", constructorModifiersFix);
            }
        }
    }

    private void checkConstructorExistence(@NotNull final PsiClass aClass, @NotNull final ProblemsHolder holder) {
        if (aClass.getConstructors().length == 0) {
            holder.registerProblem(aClass, "Class should have private constructor", createConstructorFix);
        }
    }

    private void checkClassFinalModifier(@NotNull final PsiClass aClass, @NotNull final ProblemsHolder holder) {
        // Check class final modifier
        PsiModifierList modifierList = aClass.getModifierList();
        assert modifierList != null;
        if (!modifierList.hasModifierProperty("final")) {
            holder.registerProblem(aClass, "Class should be final", setClassFinalFix);
        }
    }

    private void checkInstanceGetters(@NotNull final PsiClass aClass, @NotNull final ProblemsHolder holder) {
        @NotNull final PsiMethod[] instanceGetters = getInstanceGetters(aClass);
        for (PsiMethod instanceGetter : instanceGetters) {
            checkInstanceGetterModifiers(holder, instanceGetter);
            checkInstanceGetterReturnType(aClass, holder, instanceGetter);
        }
    }

    /**
     * Check that instance getter has public and static modifiers
     */
    private void checkInstanceGetterModifiers(@NotNull final ProblemsHolder holder, @NotNull final PsiMethod instanceGetter) {
        @NotNull final PsiModifierList modifiers = instanceGetter.getModifierList();
        if (!(modifiers.hasModifierProperty("public") && modifiers.hasModifierProperty("static"))) {
            holder.registerProblem(instanceGetter, "getInstance must be public and static", instanceGettersModifiersFix);
        }
    }

    /**
     * Check that instance getter returns itself class
     */
    private void checkInstanceGetterReturnType(@NotNull final PsiClass aClass, @NotNull final ProblemsHolder holder, @NotNull final PsiMethod instanceGetter) {
        if (instanceGetter.getReturnType() instanceof PsiClassType) {
            PsiClassType returnClassType = (PsiClassType) instanceGetter.getReturnType();
            assert returnClassType != null;
            final PsiClass instanceGetterReturnClass = returnClassType.resolve();
            if (!aClass.equals(instanceGetterReturnClass)) {
                holder.registerProblem(instanceGetter, "getInstance return class isn't equals to singleton itself class", instanceGettersReturnTypeFix);
            }
        } else {
            holder.registerProblem(instanceGetter, "getInstance must return itself class", instanceGettersReturnTypeFix);
        }
    }

    @NotNull
    private PsiMethod[] getInstanceGetters(@NotNull final PsiClass aClass) {
        return aClass.findMethodsByName("getInstance", false);
    }

    /**
     * @return true, if class has getInstance method(s)
     */
    private boolean classIsSingleton(@NotNull final PsiClass aClass) {
        @NotNull final PsiMethod[] instanceGetters = getInstanceGetters(aClass);
        return instanceGetters.length != 0;
    }

    /**
     * We are check only normal classes.
     * @return true if class is anonymous, or annotation, or enum, or interface, or is not exists in source
     */
    private boolean isNotUsualClass(@NotNull final PsiClass aClass) {
        return aClass.getNameIdentifier() == null || aClass.isAnnotationType() || aClass.isEnum() || aClass.isInterface() || !aClass.isPhysical();
    }

    private static class InstanceGettersModifiersFix implements LocalQuickFix {

        @NotNull
        @Override
        public String getName() {
            return "Set public and static modifiers";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            @NotNull final PsiMethod instanceGetter = (PsiMethod) descriptor.getPsiElement();
            instanceGetter.getModifierList().setModifierProperty("public", true);
            instanceGetter.getModifierList().setModifierProperty("static", true);
        }

        @NotNull
        @Override
        public String getFamilyName() {
            return getName();
        }

    }

    private static class ConstructorModifiersFix implements LocalQuickFix {

        @NotNull
        @Override
        public String getName() {
            return "Set private modifier to constructor";
        }


        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            @NotNull final PsiMethod constructor = (PsiMethod) descriptor.getPsiElement();
            constructor.getModifierList().setModifierProperty("private", true);
        }

        @NotNull
        @Override
        public String getFamilyName() {
            return getName();
        }

    }

    private static class InstanceGettersReturnTypeFix implements LocalQuickFix {

        @NotNull
        @Override
        public String getName() {
            return "Set correct return type";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            @NotNull final PsiMethod instanceGetter = (PsiMethod) descriptor.getPsiElement();
            assert instanceGetter.getParent() instanceof PsiClass;
            @NotNull final PsiClass instanceGetterClass = (PsiClass) instanceGetter.getParent();
            assert instanceGetterClass.getNameIdentifier() != null;
            final PsiIdentifier nameIdentifier = instanceGetterClass.getNameIdentifier();
            assert nameIdentifier != null;
            PsiTypeElement returnTypeElement = instanceGetter.getReturnTypeElement();
            assert returnTypeElement != null;
            returnTypeElement.replace(nameIdentifier.copy());
        }

        @NotNull
        @Override
        public String getFamilyName() {
            return getName();
        }

    }

    private class CreateConstructorFix implements LocalQuickFix {

        @NotNull
        @Override
        public String getName() {
            return "Create private constructor";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            @NotNull final PsiClass aClass = (PsiClass) descriptor.getPsiElement();
            @NotNull final PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
            @NotNull final PsiMethod constructor = factory.createConstructor();
            PsiIdentifier nameIdentifier = aClass.getNameIdentifier();
            assert nameIdentifier != null;
            constructor.setName(nameIdentifier.getText());
            constructor.getModifierList().setModifierProperty("private", true);
            aClass.add(constructor);
        }

        @NotNull
        @Override
        public String getFamilyName() {
            return getName();
        }

    }

    private class SetClassFinalFix implements LocalQuickFix {

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
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            @NotNull final PsiClass aClass = (PsiClass) descriptor.getPsiElement();
            PsiModifierList modifierList = aClass.getModifierList();
            assert modifierList != null;
            modifierList.setModifierProperty("final", true);
        }

    }

}
