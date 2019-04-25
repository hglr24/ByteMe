package engine.external.conditions;

/**
 * @author Anna Darwish
 */
public enum ConditionType {

        Collision ("engine.external.conditions.CollisionCondition", new Class<?>[]{String.class,String.class}),
        EqualTo ("engine.external.conditions.EqualTo", new Class<?>[]{String.class,String.class}),
        GreaterThan ("engine.external.conditions.GreaterThanCondition", new Class<?>[]{String.class,String.class}),
        LessThan ("engine.external.conditions.LessThanCondition", new Class<?>[]{String.class,String.class}),
        Is ("engine.external.conditions.StringEqualToCondition", new Class<?>[]{String.class,Double.class});


        private final String className;
        private final Class<?>[] classConstructorTypes;


        ConditionType(String className, Class<?>[] constructorTypes) {

        this.className = className;
        this.classConstructorTypes = constructorTypes;
    }


    public Class<?>[] getConstructorTypes() {
        return this.classConstructorTypes;
    }

        public String getClassName(){
            return this.className;
        }


}
