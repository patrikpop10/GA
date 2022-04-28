package patrik.ga.util;

import patrik.ga.Parameters;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ParametersController {

    public static String string() {
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields = Parameters.class.getDeclaredFields();

        for (Field f: fields) {
            try {
               stringBuilder.append("Field name: " +f.getName() +" field value " +f.get(null) +"\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return stringBuilder.toString();
    }

    public static void print() throws IllegalAccessException {
        Field[] fields = Parameters.class.getDeclaredFields();

        for (Field f: fields) {

            System.out.println("Field name: " +f.getName() +" field value " +f.get(null));

        }

    }

    /*
      USED TO CHANGE THE PARAMETERS OF THE PROBLEM TO THE ONES PASSED IN THE HASHMAP

     */
    public static void changeParameters(HashMap<String, Double> values){


        for (String s: values.keySet()) {
            try {
                /*
                GET THE FILED IN THE CLASS PARAMETERS THAT CORRESPONDS
                TO THE KEY INSIDE THE HASH MAP AND MAKES IT EDITABLE
                 */

                Field field = Parameters.class.getDeclaredField(s);
                field.setAccessible(true);
                if(field.getName() == s){

                    //CHECKS IF THE FIELD IS OF TYPE int OR TYPE double
                    if(field.getType().toString().equals("int")){


                        field.set(null, (int) Double.parseDouble(values.get(s).toString()));

                    }else if(field.getType().toString().equals("double")){
                        /*
                        Double cannot be cast to int. Only a primitive double can
                         */
                        field.set(null, Double.parseDouble(values.get(s).toString()));

                    }

                }
            }
            catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public static void incrementParameters(HashMap<String, Double> increments){

        try{
            for (String key: increments.keySet()
            ) {

                Field field = Parameters.class.getDeclaredField(key);
                field.setAccessible(true);

                if(field.getType().toString().equals("int")){

                        /*
                        Double cannot be cast to int. Only a primitive double can
                         */
                    field.set(null, field.getInt(null) +(int) Double.parseDouble(increments.get(key).toString()));

                }else if(field.getType().toString().equals("double")){

                    field.set(null, field.getDouble(null) + Double.parseDouble(increments.get(key).toString()));

                }

        }

        }catch(NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
