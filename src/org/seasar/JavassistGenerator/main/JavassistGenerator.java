package org.seasar.JavassistGenerator.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtField.Initializer;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class JavassistGenerator {

    public static void main(String[] args) throws CannotCompileException, NotFoundException, InstantiationException,
            IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException {
        // �N���X���쐬
        ClassPool pool = ClassPool.getDefault();
        CtClass cls = pool.makeClass("org.seasar.JavassistGenerator.main.impl.TestClass");

        // ���L�����o�[�y��getter�Asetter���@��ǉ�����
        CtField param = new CtField(pool.get("java.lang.String"), "name", cls);
        param.setModifiers(Modifier.PRIVATE);
        cls.addMethod(CtNewMethod.setter("setName", param));
        cls.addMethod(CtNewMethod.getter("getName", param));
        cls.addField(param, Initializer.constant(""));

        // �����Ȃ�
        CtConstructor cons = new CtConstructor(new CtClass[] {}, cls);
        cons.setBody("{name = \"Brant\";}");
        cls.addConstructor(cons);

        // ��������
        cons = new CtConstructor(new CtClass[] { pool.get("java.lang.String") }, cls);
        cons.setBody("{$0.name = $1;}");
        cls.addConstructor(cons);

        // �N���G�C�^�[�N���X�����A�E�g�v�b�g
        System.out.println(cls.toClass());

        // �����Ȃ����@���N���G�C�^�[���AgetName���@���s��
        Object o = Class.forName("org.seasar.JavassistGenerator.main.impl.TestClass").newInstance();
        Method getter = o.getClass().getMethod("getName");
        System.out.println(getter.invoke(o));

        // setName���@���s��
        Method setter = o.getClass().getMethod("setName", new Class[] { String.class });
        setter.invoke(o, "Adam");
        System.out.println(getter.invoke(o));

        // ����������@���N���G�C�^�[���AgetName���@���s��
        o = Class.forName("org.seasar.JavassistGenerator.main.impl.TestClass").getConstructor(String.class)
                .newInstance("Dong Wenqiang");
        getter = o.getClass().getMethod("getName");
        System.out.println(getter.invoke(o));

    }

}
