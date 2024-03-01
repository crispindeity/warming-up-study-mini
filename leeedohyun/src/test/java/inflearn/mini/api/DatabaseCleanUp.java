package inflearn.mini.api;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.common.base.CaseFormat;

@Component
public class DatabaseCleanUp implements InitializingBean {

    private static final String TRUNCATE_SQL_MESSAGE = "TRUNCATE TABLE %s";
    private static final String SET_REFERENTIAL_INTEGRITY_SQL_MESSAGE = "SET FOREIGN_KEY_CHECKS = %s";
    private static final String DISABLE_REFERENTIAL_QUERY = String.format(SET_REFERENTIAL_INTEGRITY_SQL_MESSAGE, false);
    private static final String ENABLE_REFERENTIAL_QUERY = String.format(SET_REFERENTIAL_INTEGRITY_SQL_MESSAGE, true);

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() throws Exception {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                .map(this::getTableName)
                .toList();
    }

    @Transactional
    public void execute() {
        disableReferentialIntegrity();
        executeTruncate();
        enableReferentialIntegrity();
    }

    private void disableReferentialIntegrity() {
        entityManager.createNativeQuery(DISABLE_REFERENTIAL_QUERY);
    }

    private void executeTruncate() {
        for (final String tableName : tableNames) {
            final String TRUNCATE_QUERY = String.format(TRUNCATE_SQL_MESSAGE, tableName);

            entityManager.createNativeQuery(TRUNCATE_QUERY);
        }
    }

    private void enableReferentialIntegrity() {
        entityManager.createNativeQuery(ENABLE_REFERENTIAL_QUERY);
    }

    private String getTableName(final EntityType<?> e) {
        final Table tableAnnotation = e.getJavaType().getAnnotation(Table.class);
        if (tableAnnotation != null && StringUtils.isNotEmpty(tableAnnotation.name())) {
            return tableAnnotation.name();
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName());
    }
}
