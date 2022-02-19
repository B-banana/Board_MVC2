package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardWriteProService {
	
	public boolean registArticle(BoardBean boardBean) throws Exception {
		
		boolean isWriteSuccess = false;
		Connection con = getConnection();
		
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		int insertCount = boardDAO.insertArticle(boardBean);
		
		if (insertCount > 0) {
			commit(con);
			isWriteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		return isWriteSuccess;

	}

}
