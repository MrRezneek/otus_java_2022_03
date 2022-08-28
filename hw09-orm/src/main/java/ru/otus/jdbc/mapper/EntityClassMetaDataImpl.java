package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{
    private final Field idField;
    private final List<Field> fields;
    private final String name;
    private final List<Field> fieldsWithoutId;
    private final Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.name = entityClass.getSimpleName();
        this.fields = new ArrayList<>(Arrays.asList(entityClass.getDeclaredFields()));

        Field idField = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                break;
            }
        }
        this.idField = idField;
        if (this.idField == null) {
            throw new EntityClassMetaDataException("Class without field annotated with @Id");
        }

        this.fieldsWithoutId = new ArrayList<>(this.fields);
        fieldsWithoutId.remove(idField);

        try {
            Class<?>[] parameterTypes = this.fields.stream().map(Field::getType).toArray(Class<?>[]::new);
            this.constructor = entityClass.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new EntityClassMetaDataException("getConstructor error", e);
        }
    }

    @Override
    public String getName() {
        return this.name.toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
