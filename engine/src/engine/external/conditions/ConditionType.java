package engine.external.conditions;

import java.util.Arrays;

/**
 * @author Anna Darwish
 */
public enum ConditionType {

        Collision ("engine.external.conditions.CollisionCondition", new Class<?>[]{String.class,String.class}),
        EqualTo ("engine.external.conditions.EqualToCondition", new Class<?>[]{String.class,String.class}),
        GreaterThan ("engine.external.conditions.GreaterThanCondition", new Class<?>[]{String.class,String.class}),
        LessThan ("engine.external.conditions.LessThanCondition", new Class<?>[]{String.class,String.class}),
        Is ("engine.external.conditions.StringEqualToCondition", new Class<?>[]{String.class,Double.class});


        private final String className;
        private final Class<?>[] classConstructorTypes;


        ConditionType(String className, Class<?>[] constructorTypes) {

        this.className = className;
        this.classConstructorTypes = constructorTypes.clone();
    }


    public Class<?>[] getConstructorTypes() {
        return this.classConstructorTypes.clone();
    }

        public String getClassName(){
            return this.className;
        }


}
