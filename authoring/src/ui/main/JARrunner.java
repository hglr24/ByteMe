package ui.main;


import data.external.DatabaseEngine;

public class JARrunner {

    public static void main(String[] args){
        System.out.println(DatabaseEngine.getInstance().open());
        MainTester.main(args);
    }


}
