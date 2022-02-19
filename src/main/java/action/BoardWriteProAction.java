package action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.BoardWriteProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = null;
		BoardBean boardBean = null;
		String realFolder = "";
		String saveFolder = "/boardUpload";
		int fileSize = 5*1024*1024;
		
		//ServletContext : 서블릿 컨테이너와 통신하기 위해 사용되는 메소드
		ServletContext context = request.getServletContext();
		realFolder = context.getRealPath(saveFolder);
		//파일 업로드 라이브러리, COS등록 필요.
		//MultipartRequest사용시, 톰캣의 getParameter가 사용을 하지 못하게 되며, 값 전달을 위해서 MultipartRequest의 객체인 getParameter를 사용해야함
		//즉. 기존의 알고 있던 getParameter에서 multi.getParameter("name")가 된다.
		/**
		 * @Param realFolder 파일저장경로
		 * @Param fileSize 파일크기
		 * @Param "UTF-8" 파일 인코딩 방법
		 */
		//DefaultFileRenamePolicy : 파일명 규칙. COS내 클래스. 오버라이드 필요?
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		
		boardBean = new BoardBean();
		boardBean.setBOARD_NAME(multi.getParameter("BOARD_NAME"));
		boardBean.setBOARD_PASS(multi.getParameter("BOARD_PASS"));
		boardBean.setBOARD_SUBJECT(multi.getParameter("BOARD_SUBJECT"));
		boardBean.setBOARD_CONTENT(multi.getParameter("BOARD_CONTENT"));
		boardBean.setBOARD_FILE(multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
		
		BoardWriteProService boardWriteProService = new BoardWriteProService();	//글 등록 요청을 처리하는 비지니스 로직 구현 서비스 클래스
		boolean isWriteSuccess = boardWriteProService.registArticle(boardBean);
		
		if (!isWriteSuccess) {
			response.setContentType("text/html;charset=UTF-8");	//HTML이 브라우져에게 인코딩 형식을 알림
			PrintWriter out = response.getWriter();	//출력 메서드
			out.println("<script>");
			out.println("alert('등록실패')");
			out.println("history.back()");
			out.println("</script>");
		} else  {
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("boardList.bo");
		}
		return forward;
	}

}
