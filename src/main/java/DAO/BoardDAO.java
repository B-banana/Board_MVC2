package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import vo.BoardBean;
import static db.JdbcUtil.*;

public class BoardDAO {

	// DataSource : ConnectionPool의 Connection을 관리하기 위한객체. JNDI에를 통해 이용됨.
	DataSource ds;
	Connection con;

	// 외부에서 생성자 접근 제한
	private static BoardDAO boardDAO;

	private BoardDAO() {
	}

	// 싱글톤 패턴으로 생성자를 리턴.
	public static BoardDAO getInstance() {

		if (boardDAO == null) {
			boardDAO = new BoardDAO();
		}
		return boardDAO;
	}

	public void setConnection(Connection con) {
		this.con = con;
	}

	// 글의 개수 구하기
	public int selectListCount() {

		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = con.prepareStatement("select count(*) from board");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				listCount = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.println("getListCount 에러 : " + e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}

	// 글 목록 보기
	public ArrayList<BoardBean> selectAritcleList(int page, int limit) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// sql문
		String Board_list_sql = "select * from board order by BOARD_RE_REF desc, BOARD_RE_SEQ asc limit ?, 10"; // limit
																												// 시작위치,
																												// 반환갯수

		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		BoardBean board = null;
		int startrow = (page - 1) * 10; // 읽기 시작할 row 번호 첫번째 시작일 경우 페이지는 1이며 0부터 시작함(0~9). 2페이지일시 10(10~19)

		try {
			pstmt = con.prepareStatement(Board_list_sql);
			pstmt.setInt(1, startrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				board = new BoardBean();
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_NAME(rs.getString("BOARD_NAME"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getInt("BOARD_DATE"));
				articleList.add(board);
			}

		} catch (Exception e) {
			System.out.println("getBoardList 에러 : " + e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return articleList;
	}

	// 글 내용 보기
	public BoardBean selectArticle(int board_num) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardBean boardBean = null;

		try {
			String sql = "select * from board where BOARD_NUM = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 상세글보기는 어차피 1줄이므로 while이 아닌 if 임
				boardBean = new BoardBean();
				boardBean.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				boardBean.setBOARD_NAME(rs.getString("BOARD_NAME"));
				boardBean.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				boardBean.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				boardBean.setBOARD_FILE(rs.getString("BOARD_FILE"));
				boardBean.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				boardBean.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				boardBean.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				boardBean.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				boardBean.setBOARD_DATE(rs.getInt("BOARD_DATE"));
			}

		} catch (Exception e) {
			System.out.println("getDetail 에러 : " + e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return boardBean;
	}

	// 글 등록
	public int insertArticle(BoardBean article) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num = 0;
		String sql = "";
		int insertCount = 0;

		try {
			pstmt = con.prepareStatement("select max(board_num) form board"); // 마지막 글 셀렉트
			rs = pstmt.executeQuery();

			if (rs.next()) {
				num = rs.getInt(1) + 1; // 새로운 글을 그 다음 번호
			} else {
				num = 1; // 첫번째 글일 경우
			}

			sql = "insert into board (BOARD_NUM, BOARD_NAME, BOARD_PASS, BOARD_SUBJECT, "
					+ "BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ, BOARD_READCOUNT, BOARD_DATE) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, article.getBOARD_NAME());
			pstmt.setString(3, article.getBOARD_PASS());
			pstmt.setString(4, article.getBOARD_SUBJECT());
			pstmt.setString(5, article.getBOARD_CONTENT());
			pstmt.setString(6, article.getBOARD_FILE());
			pstmt.setInt(7, num);
			pstmt.setInt(8, 0);
			pstmt.setInt(9, 0);
			pstmt.setInt(10, 0);

			insertCount = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("boardInsert 에러 : " + e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return insertCount;
	}
	
	//조회수 업데이트
	public int updateReadCount(int board_num) {
		
		PreparedStatement pstmt = null;
		int updateCount = 0;
		
		String sql = "update board set BOARD_READCOUNT = BOARD_READCOUNT + 1 where BOARD_NUM = " + board_num;
		
		try {
			pstmt = con.prepareStatement(sql);
			updateCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("setReadCountUpdate 에러 : " + e);
		} finally {
			close(pstmt);
		}
		return updateCount;
	}

}
