package com.todoroo.andlib.data.sql;

import static com.todoroo.andlib.data.sql.Constants.AS;
import static com.todoroo.andlib.data.sql.Constants.SPACE;

public abstract class DBObject<T extends DBObject<?>> {
    protected String alias;
    protected final String expression;

    protected DBObject(String expression){
        this.expression = expression;
    }

    public T as(String newAlias) {
        this.alias = newAlias;
        return (T) this;
    }

    public boolean hasAlias() {
        return alias != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBObject dbObject = (DBObject) o;

        if (alias != null ? !alias.equals(dbObject.alias) : dbObject.alias != null) return false;
        if (expression != null ? !expression.equals(dbObject.expression) : dbObject.expression != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = alias != null ? alias.hashCode() : 0;
        result = 31 * result + (expression != null ? expression.hashCode() : 0);
        return result;
    }

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder(expression);
        if (hasAlias()) {
            sb.append(SPACE).append(AS).append(SPACE).append(alias);
        }
        return sb.toString();
    }
}
