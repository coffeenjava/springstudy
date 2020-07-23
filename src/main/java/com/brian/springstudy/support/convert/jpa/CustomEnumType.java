package com.brian.springstudy.support.convert.jpa;

import com.brian.springstudy.entity.User;
import com.brian.springstudy.support.enums.BaseEnum;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Enum <-> String 컨버팅
 *
 * @see User
 */
public class CustomEnumType extends UserTypeAdapter {

    // 타입 명
    public static final String NAME = "com.brian.springstudy.support.convert.jpa.CustomEnumType";

    private Class<? extends BaseEnum> enumClass;
    private String entityName;
    private String propertyName;

    @Override
    public void setParameterValues(Properties parameters) {
        ParameterType params = (ParameterType) parameters.get(PARAMETER_TYPE);

        enumClass = params.getReturnedClass();
        entityName = (String) parameters.get(ENTITY);
        propertyName = (String) parameters.get(PROPERTY);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        String label = rs.getString(names[0]);

        if (label == null) return null;

        // DB -> Enum
        return BaseEnum.getEnum(enumClass, label);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        // Enum -> DB
        st.setString(index, ((BaseEnum) value).getCode());
    }

    @Override
    public Class returnedClass() {
        return enumClass;
    }
}
