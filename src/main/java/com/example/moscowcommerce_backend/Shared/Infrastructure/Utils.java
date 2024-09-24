package com.example.moscowcommerce_backend.Shared.Infrastructure;

import java.lang.reflect.Field;

public class Utils {
   public static void removeNullFields(Object obj) {
        try {
            // Obtener todos los campos del objeto
            Field[] fields = obj.getClass().getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);  // Permitir acceso a campos privados

                // Verificar si el campo es nulo
                if (field.get(obj) == null) {
                    field.set(obj, null);  // Aquí puedes simplemente saltar o realizar alguna acción si deseas
                }
            }
        } catch (Exception e) {
            System.out.println("Fallo en utils");
            e.printStackTrace();
            // Manejo de excepciones si es necesario
        }
    } 

     public static <T> T fillNullFields(T target, T source) {
        try {
            // Obtener todos los campos del objeto
            Field[] fields = target.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);  // Permitir acceso a campos privados

                // Obtener el valor del campo del target y del source
                Object targetValue = field.get(target);
                Object sourceValue = field.get(source);

                // Si el valor en el target es null, reemplazarlo con el valor del source
                if (targetValue == null && sourceValue != null) {
                    field.set(target, sourceValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de excepciones si es necesario
        }
        return target;  // Devolver el objeto target actualizado
    }
}
