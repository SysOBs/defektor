package pt.uc.sob.defektor.server.api.mapper;

import org.modelmapper.ModelMapper;

public class Mapper {

    public static  <S, T> T convertToDTO(S source) {
        String sourceClassName = source.getClass().getSimpleName();
        String targetClassName = sourceClassName.substring(0, sourceClassName.length() - 4);
        String packageName = "pt.uc.sob.defektor.server.model";

        String canonicalTargetClassName = packageName + "." + targetClassName;

        try {
            Class<?> clazz = Class.forName(canonicalTargetClassName);
            return (T) mapList(source, clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <S, T> T convertToDAO(S source) {
        String sourceClassName = source.getClass().getSimpleName();
        String targetClassName = sourceClassName + "Data";
        String packageName = "pt.uc.sob.defektor.server.api.data";
        String canonicalTargetClassName = packageName + "." + targetClassName;

        try {
            Class<?> clazz = Class.forName(canonicalTargetClassName);
            return (T) mapList(source, clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <S, T> T mapList(S source,Class<T> targetClass) {
        return new ModelMapper().map(source, targetClass);
    }
}
