package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.mapper.MessageReceivedMapper;
import ru.nsu.spirin.gamestudios.mapper.MessageSentMapper;
import ru.nsu.spirin.gamestudios.model.Message;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class EmployeeDAO extends JdbcDaoSupport {
    @Autowired
    public EmployeeDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Message> getEmployeeByID(Long employeeID) {
        Object[] params = new Object[]{ employeeID };

        String sqlSent = "SELECT * from message msg WHERE msg.sender = ? ";
        MessageSentMapper sentMapper = new MessageSentMapper();
        List<Message> sentMessages = this.getJdbcTemplate().query(sqlSent, sentMapper, params);

        String sqlReceived = "SELECT * FROM (message NATURAL JOIN received_message) msg WHERE msg.receiver = ? ";
        MessageReceivedMapper receivedMapper = new MessageReceivedMapper();
        List<Message> receivedMessages = this.getJdbcTemplate().query(sqlReceived, receivedMapper, params);

        sentMessages.addAll(receivedMessages);
        return sentMessages;
    }
}
