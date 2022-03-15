package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.Contract;
import ru.nsu.spirin.gamestudios.model.mapper.ContractMapper;
import ru.nsu.spirin.gamestudios.repository.query.ContractQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class ContractRepository extends JdbcDaoSupport {

    @Autowired
    public ContractRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Contract> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(ContractQueries.QUERY_FIND_ALL, new ContractMapper());
    }

    public Contract findByID(Long contractID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(ContractQueries.QUERY_FIND_BY_ID, new ContractMapper(), contractID);
    }

    public void save(Contract contract) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(ContractQueries.QUERY_SAVE, contract.getDate(), contract.getPercent(), contract.getTestID());
    }

    public void update(Long contractID, Contract contract) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(ContractQueries.QUERY_UPDATE, contract.getDate(), contract.getPercent(), contractID);
    }

    public void delete(Long contractID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(ContractQueries.QUERY_DELETE, contractID);
    }

    public List<Contract> findAllByGameID(Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(ContractQueries.QUERY_FIND_ALL_BY_GAME_ID, new ContractMapper(), gameID);
    }

    public void saveGameContract(Long contractID, Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(ContractQueries.QUERY_SAVE_GAME_CONTRACT, gameID, contractID);
    }

    public void deleteGameContract(Long contractID, Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(ContractQueries.QUERY_DELETE_GAME_CONTRACT, gameID, contractID);
    }

    public void deleteAllGameContract(Long contractID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(ContractQueries.QUERY_DELETE_ALL_GAME_CONTRACT, contractID);
    }

    public void deleteAllGameContractByGameID(Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(ContractQueries.QUERY_DELETE_ALL_GAME_CONTRACT_BY_GAME_ID, gameID);
    }
}
