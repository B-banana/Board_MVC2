package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;


/*
 * XXXAction 클래스를 통해 각 요청에 대한 작업을 처리하기 위해서
 * 클라이언트로부터의 요청이 들어올 때 서로 다른 클래스들에 대한 요청이므로
 * 각 Action 클래스들이 구현해야하는 execute()메서드를
 * 공통된 형태로 정의하기 위해 다형성을 활용할 수 있도록 Action 인터페이스 설계
 * - 각 요청을 받아들일 execute() 메서드를 통해 요청(request)과 응답(response) 객체를
 * 전달받고, 포워딩 정보를 저장하는 ActionForward 객체를 리턴하도록 정의
 * -> 각 XXXAction 클래스에서는 Action 인터페이스를 상속받아 구현하도록 강제
 */

public interface Action {
	ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
