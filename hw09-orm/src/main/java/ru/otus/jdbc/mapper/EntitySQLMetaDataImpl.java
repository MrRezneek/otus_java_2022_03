package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        String fields;
        String fieldsWithoutId;
        String fieldsWithoutIdForUpdate;
        String idField = entityClassMetaData.getIdField().getName();
        int fieldsWithoutIdCount;

        fields = entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        fieldsWithoutId = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        fieldsWithoutIdForUpdate = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(", "));

        fieldsWithoutIdCount = entityClassMetaData.getFieldsWithoutId().size();

        selectAllSql = "select " + fields + " from " + entityClassMetaData.getName();
        selectByIdSql = "select " + fields + " from " + entityClassMetaData.getName() + " where " + idField + " = ?";
        insertSql = "insert into " + entityClassMetaData.getName() + "(" + fieldsWithoutId + ") values (" + ",?".repeat(fieldsWithoutIdCount).substring(1) + ")";
        updateSql = "update " + entityClassMetaData.getName() + " set " + fieldsWithoutIdForUpdate + " where " + idField + " = ?";
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }
}

