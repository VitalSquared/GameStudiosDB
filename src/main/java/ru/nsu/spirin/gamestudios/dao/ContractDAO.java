package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.Contract;
import ru.nsu.spirin.gamestudios.model.mapper.ContractMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class ContractDAO extends JdbcDaoSupport {

    @Autowired
    public ContractDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Contract> getAllContracts() {
        String sql = "SELECT * FROM contract;";
        return this.getJdbcTemplate().query(sql, new ContractMapper());
    }

    public Contract getContractByID(Long contractID) {
        String sql = "SELECT * FROM contract WHERE contract_id = ?";
        return this.getJdbcTemplate().queryForObject(sql, new ContractMapper(), contractID);
    }

    public void newContract(Contract contract) {
        String sql = """
                        INSERT INTO contract (contract_id, date, percent, test_id) VALUES
                             (default, ?, ?, ?)
                    """;
        this.getJdbcTemplate().update(sql, contract.getDate(), contract.getPercent(), contract.getTestID());
    }

    public void updateContract(Long id, Contract contract) {
        String sql = """
                                    UPDATE contract
                                    SET date = ?, percent = ?
                                    WHERE contract_id = ?
                                """;
        this.getJdbcTemplate().update(sql, contract.getDate(), contract.getPercent(), id);
    }

    public List<Contract> getContractsByGameID(Long gameID) {
        String sql = """
                        SELECT c.contract_id, c.percent, c.date, c.test_id
                        FROM (contract NATURAL JOIN contract__game) c
                        WHERE c.game_id = ?;
                    """;
        return this.getJdbcTemplate().query(sql, new ContractMapper(), gameID);
    }

    public void addGameToContract(Long contractID, Long gameID) {
        String sql = """
                        INSERT INTO contract__game (game_id, contract_id) VALUES
                                                (?, ?)
                    """;
        this.getJdbcTemplate().update(sql, gameID, contractID);
    }

    public void removeGameFromContract(Long contractID, Long gameID) {
        String sql = """
                        DELETE FROM contract__game
                        WHERE game_id = ? AND contract_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, gameID, contractID);
    }
}
