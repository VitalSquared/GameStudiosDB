package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.CategoryMapper;
import ru.nsu.spirin.gamestudios.model.entity.Category;
import ru.nsu.spirin.gamestudios.repository.query.CategoryQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class CategoryRepository extends JdbcDaoSupport {

    @Autowired
    public CategoryRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Category> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(CategoryQueries.QUERY_FIND_ALL, new CategoryMapper());
    }

    public Category findByID(Long categoryID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(CategoryQueries.QUERY_FIND_BY_ID, new CategoryMapper(), categoryID);
    }

    public void save(Category category) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(CategoryQueries.QUERY_SAVE, category.getName());
    }

    public void update(Long categoryID, Category category) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(CategoryQueries.QUERY_UPDATE, category.getName(), categoryID);
    }

    public void delete(Long categoryID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(CategoryQueries.QUERY_DELETE, categoryID);
    }
}
