package com.ryan;

import com.google.auto.service.AutoService;
import com.ryan.annotations.click;
import com.ryan.annotations.findId;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(javax.annotation.processing.Processor.class)
public class MyProcessor extends AbstractProcessor {

    private static final String TAG = "MyProcessor";
    private static final String SUFFIX = "__ViewFinder";
    private Map<String, ProcessInfo> mClassCache;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mClassCache = new HashMap<>();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>(2);
        types.add(findId.class.getCanonicalName());
        types.add(click.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println(TAG);
        System.out.println(TAG + " ,start");

        mClassCache.clear();

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(findId.class);
        for (Element element : elements) {
            if (element.getAnnotation(findId.class) != null) {
                generateFindIdClass(element);
            }
        }

        elements = roundEnvironment.getElementsAnnotatedWith(click.class);
        for (Element element : elements) {
            if (element.getAnnotation(click.class) != null) {
                generateClickClass(element);
            }
        }

        try {
            for (ProcessInfo info : mClassCache.values()) {
                TypeSpec typeSpec = info.getTypeSpec().toBuilder()
                        .addMethod(info.getFindViewSpec())
                        .build();
                info.setTypeSpec(typeSpec);
                JavaFile javaFile = JavaFile.builder(info.getPackageName(), typeSpec)
                        .build();
                javaFile.writeTo(System.out);
                javaFile.writeTo(processingEnv.getFiler());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(TAG + " end");

        return true;
    }

    private void generateClickClass(Element element) {
        System.out.println("generateClickClass");
        String methodName = element.getSimpleName().toString();
        int[] values = element.getAnnotation(click.class).value();
        if (values == null || values.length <= 0) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "@click with no id !!");
            return;
        }

        for (int i = 0; i < values.length; i++) {
            CodeBlock codeBlock = CodeBlock.of(
                    "host.findViewById($1L)" +
                            ".setOnClickListener(new android.view.View.OnClickListener(){\n" +
                            "   @Override\n" +
                            "   public void onClick(android.view.View v) {\n" +
                            "       host.$2N();\n" +
                            "   }\n" +
                            "});",
                    values[i],
                    methodName
            );
            createOrAddCodeToProcessInfo(element, codeBlock);
        }



    }

    private void generateFindIdClass(Element element) {
        String fieldName = element.getSimpleName().toString();
        int id = element.getAnnotation(findId.class).value();
        TypeName elementTypeName = ClassName.get(element.asType());
        CodeBlock codeBlock = CodeBlock.of(
                "\nhost.$1N = ($2N)host.findViewById($3L);\n",
                fieldName,
                elementTypeName.toString(),
                id
        );
        createOrAddCodeToProcessInfo(element, codeBlock);
    }

    private void createOrAddCodeToProcessInfo(Element element, CodeBlock codeBlock) {
        Elements elementUtils = processingEnv.getElementUtils();
        Messager messager = processingEnv.getMessager();

        Element enclosingElement = element.getEnclosingElement();
        if (!(enclosingElement instanceof TypeElement)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "enclosingElement is not a class !!");
            return;
        }

        TypeElement typeElement = (TypeElement) enclosingElement;
        String className = typeElement.getQualifiedName().toString();

        PackageElement packageElement = elementUtils.getPackageOf(element);

        System.out.println("generateFindIdClass");

        ProcessInfo info = null;
        if (mClassCache.containsKey(className)) {
            info = mClassCache.get(className);

            info.setFindViewSpec(info.getFindViewSpec().toBuilder()
                    .addCode(codeBlock)
                    .build());

            System.out.println("add code to method " + info.getFindViewSpec().toString());
        } else {
            info = newProcessInfo(typeElement, element, className, typeElement.getSimpleName().toString(), packageElement.getQualifiedName().toString(), codeBlock);
            mClassCache.put(className, info);
            System.out.println("new code " + info.getFindViewSpec().toString());
        }

    }

    private ProcessInfo newProcessInfo(TypeElement classElement, Element element, String className, String simpleName, String packageName, CodeBlock codeBlock) {
        String generatedClassName = simpleName + SUFFIX;

        MethodSpec findView = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(TypeName.get(classElement.asType()), "host", Modifier.FINAL)
                .addCode(codeBlock)
                .build();
        TypeSpec typeSpec = TypeSpec.classBuilder(generatedClassName)
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .build();

        ProcessInfo info = new ProcessInfo();
        info.setTypeSpec(typeSpec);
        info.setPackageName(packageName);
        info.setKeyClassName(className);
        info.setFindViewSpec(findView);
        return info;
    }
}
