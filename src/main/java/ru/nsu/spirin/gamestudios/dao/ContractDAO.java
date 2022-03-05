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
                                    SET date = ?, percent = ?, test_id = ?
                                    WHERE contract_id = ?
                                """;
        this.getJdbcTemplate().update(sql, contract.getDate(), contract.getPercent(), contract.getTestID(), id);
    }
}
