package ru.otus.jdbc.mapper;

import ru.otus.jdbc.core.repository.DataTemplate;
import ru.otus.jdbc.core.repository.DataTemplateException;
import ru.otus.jdbc.core.repository.executor.DbExecutor;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), Collections.singletonList(id), rs -> {
            try {
                if (rs.next()) {
                    return instantiate(rs);
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var entityList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    entityList.add(instantiate(rs));
                }
                return entityList;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T entity) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getFieldsWithoutIdParams(entity));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T entity) {
        try {
            var params = getFieldsWithoutIdParams(entity);
            params.add(entityClassMetaData.getIdField().get(entity));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T instantiate(ResultSet rs) throws Exception {
        List<Object> columnsValues = new ArrayList<>();

        for(var field : entityClassMetaData.getAllFields()) {
            columnsValues.add(rs.getObject(field.getName()));
        }

        Constructor<T> constructor = entityClassMetaData.getConstructor();
        return constructor.newInstance(columnsValues.toArray());
    }

    private List<Object> getFieldsWithoutIdParams(T entity) throws IllegalAccessException {
        List<Object> params = new ArrayList<>();
        for(var field : entityClassMetaData.getFieldsWithoutId()) {
            field.setAccessible(true);
            params.add(field.get(entity));
        }
        return params;
    }
}
