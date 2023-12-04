package com.github.simp7.commentdict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DAO {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String JDBC_URL = "jdbc:h2:tcp://localhost/~/db/commentDict";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Connection open() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL,"admin","pass1234");
            logger.info(conn.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public void addUser(String id, String passwd) throws Exception {
        Connection conn = open();

        String sql = "INSERT INTO comments(id, passwd) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try (conn; pstmt) {
            pstmt.setString(1, id);
            pstmt.setString(2, passwd);
        }
    }

    public User getUser(String id, String passwd) throws Exception {
        Connection conn = open();

        String sql = "SELECT uid FROM users WHERE id=? AND passwd=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try(conn; pstmt) {
            pstmt.setString(1, id);
            pstmt.setString(2, passwd);
            logger.info("로그인 시도 - id: " + id +", passwd:" + passwd);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt("uid"));
                user.setId(id);
                user.setPasswd(passwd);
                logger.info("로그인 성공! - " + user.getId());
                return user;
            }
            return null;
        }
    }

    public List<Comment> getComments(String keyword, Integer uid) throws Exception {
        Connection conn = open();
        List<Comment> comments = new ArrayList<>();

        String sql = "SELECT id, owner_uid, content, popularity FROM comments WHERE keyword=? ORDER BY popularity ASC";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try(conn; pstmt) {
            pstmt.setString(1, keyword);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Comment c = new Comment(rs.getInt("id"));
                final boolean  isMine = uid != null && rs.getInt("owner_uid") == uid;
                c.setContent(rs.getString("content"));
                c.setMine(isMine);
                c.setPopularity(rs.getInt("popularity"));
                if (isMine) {
                    comments.add(0, c);
                    continue;
                }
                comments.add(c);
            }
            return comments;
        }
    }

    public void addComment(String keyword, Comment c, int uid) throws Exception {
        Connection conn = open();

        String sql = "INSERT INTO comments(keyword,popularity,content,owner_uid) VALUES (?,0,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try(conn; pstmt) {
            pstmt.setString(1, keyword);
            pstmt.setString(2, c.getContent());
            pstmt.setInt(3, uid);
            pstmt.executeUpdate();
        }
    }

    public void deleteComment(int id) throws SQLException {
        Connection conn = open();

        String sql = "DELETE FROM comments WHERE id=? ";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try(conn; pstmt) {
            pstmt.setInt(1, id);
            if(pstmt.executeUpdate() == 0) {
                throw new SQLException("DB 에러");
            }
        }
    }

    public void likeComment(int id, Integer uid) throws Exception {
        Connection conn = open();

        String sql = "BEGIN; " +
                "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; " +
                "UPDATE comments SET popularity = popularity + 1 WHERE id=?; " +
                "COMMIT;";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try (conn; pstmt) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info(id + ": 성공적으로 추천했습니다.");
        }
    }

    public void dislikeComment(int id, Integer uid) throws Exception {
        Connection conn = open();

        String sql = "BEGIN;" +
                "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;" +
                "UPDATE comments SET popularity = popularity - 1 WHERE id=?;" +
                "COMMIT;";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try (conn; pstmt) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info(id + ": 성공적으로 비추천했습니다.");
        }
    }

}
