package com.github.simp7.commentdict;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DAO {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String JDBC_URL = "jdbc:h2:tcp://localhost/~/db/commentDict";

    public Connection open() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL,"admin","pass1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public List<Comment> getComments(int uid, String keyword) throws Exception {
        Connection conn = open();
        List<Comment> comments = new ArrayList<>();

        String sql = "select id, owner_uid, content, popularity as cdate from comment where keyword=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try(conn; pstmt) {
            pstmt.setString(1, keyword);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Comment c = new Comment();
                c.setContent(rs.getString("content"));
                c.setMine(rs.getInt("owner_uid") == uid);
                c.setPopularity(rs.getInt("popularity"));
                comments.add(c);
            }
            return comments;
        }
    }

    public void addComment(String keyword, Comment c) throws Exception {
        Connection conn = open();

        String sql = "insert into comments(keyword,popularity,content,date) values(?,?,?,CURRENT_TIMESTAMP())";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try(conn; pstmt) {
            pstmt.setString(1, c.getContent());
            pstmt.setInt(2, 0);
            pstmt.setString(3, c.getContent());
            pstmt.executeUpdate();
        }
    }

    public void delComment(int aid) throws SQLException {
        Connection conn = open();

        String sql = "delete from news where id=? and ";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try(conn; pstmt) {
            pstmt.setInt(1, aid);
            // 삭제된 뉴스 기사가 없을 경우
            if(pstmt.executeUpdate() == 0) {
                throw new SQLException("DB 에러");
            }
        }
    }
}
