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
    final String JDBC_URL = "jdbc:h2:tcp://localhost/~/db/commentDict;mode=mysql";
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

        String sql = "INSERT INTO users(id, passwd) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try (conn; pstmt) {
            pstmt.setString(1, id);
            pstmt.setString(2, passwd);
            pstmt.executeUpdate();
        }
    }

    public User getUser(String id, String passwd) throws Exception {
        Connection conn = open();

        String sql = "SELECT uid FROM users WHERE id=? AND passwd=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try(conn; pstmt) {
            pstmt.setString(1, id);
            pstmt.setString(2, passwd);
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

        String sql = "SELECT view.id, view.owner_uid, view.keyword, view.content, view.popularity, IFNULL(l.weight, 0) AS myvote FROM (\n" +
                "SELECT c.id, c.owner_uid, c.keyword, c.content, IFNULL(sum(l.weight), 0) AS popularity FROM comments AS c\n" +
                "LEFT JOIN likes AS l\n" +
                "ON l.comment_id = c.id\n" +
                "GROUP BY c.id\n" +
                ") view\n" +
                "LEFT JOIN likes AS l\n" +
                "ON l.comment_id = view.id AND l.uid=?\n" +
                "WHERE keyword=?\n" +
                "ORDER BY popularity DESC";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try(conn; pstmt) {
            pstmt.setInt(1, uid != null ? uid : 0);
            pstmt.setString(2, keyword);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                final boolean  isMine = uid != null && rs.getInt("owner_uid") == uid;
                c.setContent(rs.getString("content"));
                c.setMine(isMine);
                c.setPopularity(rs.getInt("popularity"));
                if (isMine) {
                    comments.add(0, c);
                    continue;
                }
                c.setMyVote(rs.getInt("myVote"));
                comments.add(c);
            }
            return comments;
        }
    }

    public void addComment(String keyword, Comment c, int uid) throws Exception {
        Connection conn = open();

        String sql = "INSERT INTO comments(keyword,content,owner_uid) VALUES (?,?,?)";
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

    public List<Integer> getLikesByUser(Integer uid) throws Exception {
        Connection conn = open();
        List<Integer> ids = new ArrayList<>();

        if (uid == null) {
            return ids;
        }

        String sql = "SELECT id FROM likes WHERE uid=? ";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try (conn; pstmt) {
            pstmt.setInt(1, uid);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                ids.add(rs.getInt("id"));
            }
        }

        return ids;
    }

    public void voteComment(int id, int uid, int weight) throws Exception {
        Connection conn = open();

        String sql = "INSERT INTO likes(comment_id, uid, weight) VALUES (?, ?, ?)\n" +
                "ON DUPLICATE KEY UPDATE weight=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try (conn; pstmt) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, uid);
            pstmt.setInt(3, weight);
            pstmt.setInt(4, weight);
            pstmt.executeUpdate();
            logger.info(id + ": 성공적으로 추천했습니다.");
        }
    }
}
