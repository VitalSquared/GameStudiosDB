package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.Contract;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractMapper implements RowMapper<Contract> {

    @Override
    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long contractID = rs.getLong("contract_id");
        Date date = rs.getDate("date");
        Long percent = rs.getLong("percent");
        Long testID = rs.getLong("test_id");
        return new Contract(contractID, date, percent, testID);
    }
}
