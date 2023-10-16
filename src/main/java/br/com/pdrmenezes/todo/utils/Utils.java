package br.com.pdrmenezes.todo.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

  public static void copyNonNullProperties(Object source, Object target) {
    // method to copy the properties that will be updated from the source, to the
    // target, ignoring the null properties. Those should remain as they were
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }

  // method to get the names of properties that will not be updated == are null
  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();
    Set<String> emptyFields = new HashSet<>(null);

    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
      Object srcValue = src.getPropertyValue(propertyDescriptor.getName());
      if (srcValue == null) {
        emptyFields.add(propertyDescriptor.getName());
      }
    }
    String[] result = new String[emptyFields.size()];
    return emptyFields.toArray(result);

  }

}
