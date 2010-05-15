package com.todoroo.andlib.data.sql;

import static com.todoroo.andlib.data.sql.Constants.AND;
import static com.todoroo.andlib.data.sql.Constants.EXISTS;
import static com.todoroo.andlib.data.sql.Constants.LEFT_PARENTHESIS;
import static com.todoroo.andlib.data.sql.Constants.NOT;
import static com.todoroo.andlib.data.sql.Constants.OR;
import static com.todoroo.andlib.data.sql.Constants.RIGHT_PARENTHESIS;
import static com.todoroo.andlib.data.sql.Constants.SPACE;

public abstract class Criterion {
    protected final Operator operator;

    Criterion(Operator operator) {
        this.operator = operator;
    }

    public static Criterion and(final Criterion criterion, final Criterion... criterions) {
        return new Criterion(Operator.and) {

            @Override
            protected void populate(StringBuilder sb) {
                sb.append(criterion);
                for (Criterion c : criterions) {
                    sb.append(SPACE).append(AND).append(SPACE).append(c);
                }
            }
        };
    }

    public static Criterion or(final Criterion criterion, final Criterion... criterions) {
        return new Criterion(Operator.or) {

            @Override
            protected void populate(StringBuilder sb) {
                sb.append(criterion);
                for (Criterion c : criterions) {
                    sb.append(SPACE).append(OR).append(SPACE).append(c.toString());
                }
            }
        };
    }

    public static Criterion exists(final Query query) {
        return new Criterion(Operator.exists) {

            @Override
            protected void populate(StringBuilder sb) {
                sb.append(EXISTS).append(SPACE).append(LEFT_PARENTHESIS).append(query).append(RIGHT_PARENTHESIS);
            }
        };
    }

    public static Criterion not(final Criterion criterion) {
        return new Criterion(Operator.not) {

            @Override
            protected void populate(StringBuilder sb) {
                sb.append(NOT).append(SPACE);
                criterion.populate(sb);
            }
        };
    }

    protected abstract void populate(StringBuilder sb);

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(LEFT_PARENTHESIS);
        populate(builder);
        builder.append(RIGHT_PARENTHESIS);
        return builder.toString();
    }

}
