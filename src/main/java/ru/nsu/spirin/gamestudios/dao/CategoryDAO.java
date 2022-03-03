package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.mapper.CategoryMapper;
import ru.nsu.spirin.gamestudios.model.Category;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class CategoryDAO extends JdbcDaoSupport {

    @Autowired
    public CategoryDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Category getCategoryByID(Long categoryID) {
        String sql = """
                        SELECT *
                        FROM category cat
                        WHERE cat.category_id = ?
                    """;
        return this.getJdbcTemplate().queryForObject(sql, new CategoryMapper(), categoryID);
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM category ORDER BY category_id";
        return this.getJdbcTemplate().query(sql, new CategoryMapper());
    }

    public void newCategory(Category category) throws SQLException {
        if (category.getName() == null) {
            return;
        }
        String sql = """
                        INSERT INTO category (category_id, name) VALUES
                             (default, ?);
                    """;
        this.getJdbcTemplate().update(sql, category.getName());
    }

    public void updateCategory(Long id, Category category) throws SQLException {
        if (category.getName() == null) {
            return;
        }
        String sql = """
                        UPDATE category
                        SET name = ?
                        WHERE category_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, category.getName(), id);
    }
}
