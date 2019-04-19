package conditions;

public enum ConditionType {

        Collision (CollisionCondition.class, new Class<?>[]{String.class,String.class}),
        EqualTo (EqualToCondition.class, new Class<?>[]{String.class,String.class}),
        GreaterThan (GreaterThanCondition.class, new Class<?>[]{String.class,String.class}),
        LessThan (LessThanCondition.class, new Class<?>[]{String.class,String.class}),
        StringEqualTo (StringEqualToCondition.class, new Class<?>[]{String.class,Double.class});


        private final Class<?> className;
        private final Class<?>[] classConstructorTypes;


        ConditionType(Class<?> className, Class<?>[] constructorTypes) {

            this.className = className;
            this.classConstructorTypes = constructorTypes;
        }


        public Class<?>[] getConstructorTypes(){
            return this.classConstructorTypes;
        }

        public Class<?> getClassName(){
            return this.className;
        }


}
